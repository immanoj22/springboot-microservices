package microservice.pratice.Security.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import microservice.pratice.Security.Exception.JWTExpiredException;
import microservice.pratice.Security.Service.JwtService;
import microservice.pratice.User.service.UserDetailsSevice;
import microservice.pratice.Utils.SendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JWTFilterChain extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsSevice userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilterChain(JwtService jwtService, UserDetailsSevice userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtService.extractEmail(token);
            } catch (ExpiredJwtException ex) {
                writeErrorResponse(response, HttpStatus.FORBIDDEN, "Session expired");
                return; // stop the filter chain here — do not call filterChain.doFilter()
            } catch (JwtException ex) {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.validate(token, userDetails)) {
                request.setAttribute("email", userDetails.getUsername());
                request.setAttribute("role", userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        SendResponse<Void> sendResponse = new SendResponse<>();
        sendResponse.setStatus(false);
        sendResponse.setMessage(message);
        sendResponse.setStatusCode(status);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(sendResponse));
    }
}

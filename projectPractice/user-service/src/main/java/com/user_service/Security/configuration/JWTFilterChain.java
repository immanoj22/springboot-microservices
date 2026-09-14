package com.user_service.Security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.Security.Service.JwtService;
import com.user_service.Utils.SendResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTFilterChain extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilterChain(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtService.extractEmail(token);

                if (jwtService.isTokenExpired(token)) {   // <-- add explicit check here
                    writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Session expired");
                    return;
                }

            } catch (ExpiredJwtException ex) {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Session expired");
                return;
            } catch (JwtException ex) {
                writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.emptyList()
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
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

package microservice.pratice.User.service;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.servlet.http.HttpServletResponse;
import microservice.pratice.Security.Service.JwtService;
import microservice.pratice.User.Exception.InvalidRefreshToken;
import microservice.pratice.User.Exception.UserAlreadyExists;
import microservice.pratice.User.Exception.UsernameNotExist;
import microservice.pratice.User.Models.DTO.RefreshDTO;
import microservice.pratice.User.Models.DTO.UserDTO;
import microservice.pratice.User.Models.Mapper.UserMapper;
import microservice.pratice.User.Models.RefreshToken;
import microservice.pratice.User.Models.User;
import microservice.pratice.User.Repository.RefreshTokenRepository;
import microservice.pratice.User.Repository.UserRepository;
import microservice.pratice.Utils.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);

    public UserDTO newuser(User user){
        boolean userExist=userRepository.existsByEmail(user.getEmail());

        if(userExist){
            throw new UserAlreadyExists("email already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRole(
                user.getRole() != null
                        ? user.getRole()
                        : "USER"
        );

        User saveduser=userRepository.save(user);
        return new UserMapper().EntityToResponse(saveduser);
    }

    public UserDTO login(UserDTO userRequestDto, HttpServletResponse response) {
        UserMapper userMapper = new UserMapper();
        User loginAttempt = userMapper.reuestToEntity(userRequestDto);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAttempt.getEmail(), loginAttempt.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new UsernameNotExist("Invalid email or password");
        }

        User existingUser = userRepository.findByEmail(loginAttempt.getEmail());
        if (existingUser == null) {
            throw new UsernameNotExist("Invalid email or password");
        }

        String accessToken = jwtService.genrateAccessToken(existingUser); // fixed
        String refreshTokenValue = UUID.randomUUID().toString();
        Date date = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10);

        RefreshToken existingToken = refreshTokenRepository.findByUser(existingUser);

        if (existingToken == null) {                  // fixed — handle first login
            existingToken = new RefreshToken();
            existingToken.setUser(existingUser);
        }

        existingToken.setRefreshToken(refreshTokenValue);
        existingToken.setValidTill(date);
        refreshTokenRepository.save(existingToken);

        userRequestDto.setToken(accessToken);
        userRequestDto.setRole(existingUser.getRole());

        ResponseCookie responseCookie=ResponseCookie.from("refreshToken",refreshTokenValue)
                .httpOnly(true)
                .secure(true)
                .path("/api/v1/user/refresh")
                .maxAge(Duration.ofDays(10))
                .sameSite("strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());

        return userRequestDto;
    }

    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken=refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(()-> new InvalidRefreshToken("invalid refresh token"));

        if(refreshToken.getValidTill().before(new Date())){
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshToken("Session expires");
        }

        return refreshToken;
    }


    public void saveRefreshToken(RefreshToken storedRefreshToken,
                                 HttpServletResponse response) {
        refreshTokenRepository.save(storedRefreshToken);

        ResponseCookie responseCookie=ResponseCookie.from("refreshToken",
                        storedRefreshToken.getRefreshToken()
                )
                .httpOnly(true)
                .secure(true)
                .path("/api/v1/user/refresh")
                .maxAge(Duration.ofDays(10))
                .sameSite("strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

    }
}

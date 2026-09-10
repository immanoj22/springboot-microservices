package microservice.pratice.User.service;


import microservice.pratice.Security.Service.JwtService;
import microservice.pratice.User.Exception.UserAlreadyExists;
import microservice.pratice.User.Exception.UsernameNotExist;
import microservice.pratice.User.Models.DTO.UserDTO;
import microservice.pratice.User.Models.Mapper.UserMapper;
import microservice.pratice.User.Models.User;
import microservice.pratice.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

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

    public UserDTO login(UserDTO userRequestDto) {
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

        if(existingUser==null){
           throw new UsernameNotExist("Invalid email or password");
        }

        System.out.println("existingUser" +existingUser);
        String token = jwtService.genrateToken(existingUser);
        existingUser.setRefreshToken(token); // if you want refresh-token persistence
        userRepository.save(existingUser);

        userRequestDto.setToken(token);
        userRequestDto.setRole(existingUser.getRole());

        return userRequestDto;
    }


}

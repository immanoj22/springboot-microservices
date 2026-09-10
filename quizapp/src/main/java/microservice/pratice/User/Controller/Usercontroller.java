package microservice.pratice.User.Controller;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.servlet.http.HttpServletResponse;
import microservice.pratice.Security.Service.JwtService;
import microservice.pratice.User.Exception.InvalidRefreshToken;
import microservice.pratice.User.Models.DTO.UserDTO;
import microservice.pratice.User.Models.Mapper.UserMapper;
import microservice.pratice.User.Models.RefreshToken;
import microservice.pratice.User.Models.User;
import microservice.pratice.User.service.UserService;
import microservice.pratice.Utils.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class Usercontroller {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/hello")
    public String nothing(){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<SendResponse> saveuser(@RequestBody UserDTO user){

        UserDTO saveduser= userService.newuser(
                new UserMapper()
                        .reuestToEntity(user)
        );

        SendResponse sendResponse=new SendResponse<>();

        sendResponse.setData(saveduser);
        sendResponse.setMessage("registered success fully");
        sendResponse.setStatusCode(HttpStatus.CREATED);

        return new ResponseEntity<>(sendResponse,
                sendResponse.getStatusCode());
    }

    @PostMapping("/login")
    public ResponseEntity<SendResponse> loginUser(@RequestBody UserDTO userReuestdto, HttpServletResponse response){
        UserDTO logedinUser= userService.login(userReuestdto,response);

        SendResponse sendResponse=new SendResponse<>();

        sendResponse.setData(logedinUser);
        sendResponse.setMessage("login successfully");
        sendResponse.setStatusCode(HttpStatus.OK);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }

    @GetMapping("/refresh")
    public ResponseEntity<SendResponse> getAccessToken(
            @CookieValue(value = "refreshToken",required = false)
            String refreshTokenValue,
            HttpServletResponse response
    ){

        SendResponse sendResponse=new SendResponse();
        if (refreshTokenValue == null) {
            sendResponse.setMessage("No refresh token found, please login again");
            sendResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
        }

        RefreshToken storedRefreshToken;
        try{
            storedRefreshToken=userService.validateRefreshToken(refreshTokenValue);
        }catch (Exception ex){
            sendResponse.setMessage(ex.getMessage());
            sendResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
        }

        String newAccessToken=jwtService.genrateAccessToken(storedRefreshToken.getUser());

        String newRefreshToken= UUID.randomUUID().toString();
        storedRefreshToken.setRefreshToken(newRefreshToken);
        storedRefreshToken.setValidTill(
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10)
        );
        userService.saveRefreshToken(storedRefreshToken,response);

        sendResponse.setData(Map.of("accessToken", newAccessToken));
        sendResponse.setMessage("Token refreshed successfully");
        sendResponse.setStatusCode(HttpStatus.OK);

        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }
}

package com.authService.authService.User.Controller;

import com.authService.authService.Security.Service.JwtService;
import com.authService.authService.User.Models.DTO.UserDTO;
import com.authService.authService.User.Models.Mapper.UserMapper;
import com.authService.authService.User.Models.RefreshToken;
import com.authService.authService.User.service.UserService;
import com.authService.authService.Utils.FileSystem;
import com.authService.authService.Utils.SendResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class Usercontroller {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    FileSystem fileSystem;

    @PostMapping("/register")
    public ResponseEntity<SendResponse> saveuser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("userName") String username,
            @RequestParam(value = "profilePic",required = false)
            MultipartFile multipartFile
            ){

        UserDTO user=new UserDTO();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(username);

        try{
            String fileName=fileSystem.saveFile(multipartFile,"profileImage");
            if(!fileName.isEmpty()){
                user.setProfileImage(fileName);
            }
        }catch (Exception e){
            System.out.println("error happened saving the file"+e.getMessage());
        }

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
    public ResponseEntity<SendResponse> loginUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpServletResponse response){

        UserDTO userReuestdto=new UserDTO();
        userReuestdto.setEmail(email);
        userReuestdto.setPassword(password);

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
            HttpServletRequest request,
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

        String authHeader = request.getHeader("Authorization");

        if(authHeader ==null){
            sendResponse.setMessage("access token required");
            sendResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
        }

        authHeader=authHeader.substring(7);
        List<String> list=storedRefreshToken.getAccesstoken();
        for(String e:list){
            System.out.println("---->"+e);
        }
        if(!list.contains(authHeader)){
            sendResponse.setMessage("Invalid access token");
            sendResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
        }

        list.remove(authHeader);
        String newAccessToken=jwtService.genrateAccessToken(storedRefreshToken.getUser());
        list.add(newAccessToken);
        String newRefreshToken= UUID.randomUUID().toString();
        storedRefreshToken.setRefreshToken(newRefreshToken);
        storedRefreshToken.setValidTill(
                new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10)
        );
        storedRefreshToken.setAccesstoken(list);
        userService.saveRefreshToken(storedRefreshToken,response);

        sendResponse.setData(Map.of("accessToken", newAccessToken));
        sendResponse.setMessage("Token refreshed successfully");
        sendResponse.setStatusCode(HttpStatus.OK);

        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }
}

package microservice.pratice.User.Controller;

import microservice.pratice.User.Models.DTO.UserDTO;
import microservice.pratice.User.Models.Mapper.UserMapper;
import microservice.pratice.User.Models.User;
import microservice.pratice.User.service.UserService;
import microservice.pratice.Utils.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class Usercontroller {

    @Autowired
    UserService userService;
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
    public ResponseEntity<SendResponse> loginUser(@RequestBody UserDTO userReuestdto){
        UserDTO logedinUser= userService.login(userReuestdto);

        SendResponse sendResponse=new SendResponse<>();

        sendResponse.setData(logedinUser);
        sendResponse.setMessage("login successfully");
        sendResponse.setStatusCode(HttpStatus.OK);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }
}

package com.user_service.User.Controller;
;
import com.user_service.User.service.UserService;
import com.user_service.Utils.FileSystem;
import com.user_service.Utils.SendResponse;
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
   FileSystem fileSystem;

   @PostMapping("/updateUser")
    public ResponseEntity<SendResponse> updateUser(
           @RequestBody Map<String,String> value
   ){

       SendResponse sendResponse = new SendResponse();
       sendResponse.setData(userService.updateUser(value));
       sendResponse.setStatusCode(HttpStatus.OK);
       return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
   }

}

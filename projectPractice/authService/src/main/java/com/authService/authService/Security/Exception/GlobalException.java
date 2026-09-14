package com.authService.authService.Security.Exception;

import com.authService.authService.User.Exception.UserAlreadyExists;
import com.authService.authService.User.Exception.UserNotFoundException;
import com.authService.authService.User.Exception.UsernameNotExist;
import com.authService.authService.Utils.SendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<SendResponse> usernotExist(UserNotFoundException usernameNotExist){
        String message=usernameNotExist.getMessage();

        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setStatus(false);
        sendResponse.setMessage(message);
        sendResponse.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<SendResponse> userExists(UserAlreadyExists userAlreadyExists){
        String message=userAlreadyExists.getMessage();

        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setStatus(false);
        sendResponse.setMessage(message);
        sendResponse.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }

    @ExceptionHandler(UsernameNotExist.class)
    public ResponseEntity<SendResponse> usernamenotExits(UsernameNotExist usernameNotExist){
        String message=usernameNotExist.getMessage();

        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setStatus(false);
        sendResponse.setMessage(message);
        sendResponse.setStatusCode(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }

    @ExceptionHandler(JWTExpiredException.class)
    public ResponseEntity<SendResponse> expired(JWTExpiredException jwtExpiredException){
        String message=jwtExpiredException.getMessage();

        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setStatus(false);
        sendResponse.setMessage(message);
        sendResponse.setStatusCode(HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(sendResponse, sendResponse.getStatusCode());
    }

}

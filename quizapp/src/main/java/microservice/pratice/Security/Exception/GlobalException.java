package microservice.pratice.Security.Exception;

import microservice.pratice.User.Exception.UserAlreadyExists;
import microservice.pratice.User.Exception.UserNotFoundException;
import microservice.pratice.User.Exception.UsernameNotExist;
import microservice.pratice.Utils.SendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
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

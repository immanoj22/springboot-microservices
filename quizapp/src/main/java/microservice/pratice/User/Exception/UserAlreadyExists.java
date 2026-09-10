package microservice.pratice.User.Exception;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists(String message){
        super(message);
    }
}

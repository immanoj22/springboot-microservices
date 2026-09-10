package microservice.pratice.User.Exception;

public class InvalidRefreshToken extends RuntimeException {
    public InvalidRefreshToken(String invalidRefreshToken) {
        super(invalidRefreshToken);
    }
}

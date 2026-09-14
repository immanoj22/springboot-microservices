package com.authService.authService.User.Exception;

public class InvalidRefreshToken extends RuntimeException {
    public InvalidRefreshToken(String invalidRefreshToken) {
        super(invalidRefreshToken);
    }
}

package com.authService.authService.User.Exception;

public class UsernameNotExist extends RuntimeException {
    public UsernameNotExist(String userNotFound) {
        super(userNotFound);
    }
}

package com.authService.authService.Security.Exception;

public class JWTExpiredException extends RuntimeException{
    public JWTExpiredException(String mssage){
        super(mssage);
    }
}

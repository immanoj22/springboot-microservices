package com.user_service.Security.Exception;

public class JWTExpiredException extends RuntimeException{
    public JWTExpiredException(String mssage){
        super(mssage);
    }
}

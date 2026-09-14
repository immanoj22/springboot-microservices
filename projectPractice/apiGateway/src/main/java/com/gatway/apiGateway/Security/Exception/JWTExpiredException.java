package com.gatway.apiGateway.Security.Exception;

public class JWTExpiredException extends RuntimeException{
    public JWTExpiredException(String mssage){
        super(mssage);
    }
}

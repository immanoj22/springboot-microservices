package com.authService.authService.Utils;

import org.springframework.http.HttpStatus;

public class SendResponse<T> {
    private T data;
    private String message;
    private HttpStatus statusCode;

    private boolean status=true;

    public void setData(T data){
        this.data=data;
    }

    public T getData(){
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

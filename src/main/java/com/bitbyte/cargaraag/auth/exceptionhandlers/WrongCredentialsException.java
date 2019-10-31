package com.bitbyte.cargaraag.auth.exceptionhandlers;

public class WrongCredentialsException extends RuntimeException {

    private String message;

    public WrongCredentialsException(String exceptionMessage){
        this.message = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

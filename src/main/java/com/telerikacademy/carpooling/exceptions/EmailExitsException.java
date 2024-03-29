package com.telerikacademy.carpooling.exceptions;

public class EmailExitsException extends RuntimeException{

    public EmailExitsException(String email) {
        super(String.format("Email %s already exists.", email));
    }
}

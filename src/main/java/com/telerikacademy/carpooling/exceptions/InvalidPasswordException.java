package com.telerikacademy.carpooling.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("Password must be at least 8 characters long and should contain at least one uppercase letter, one lowercase letter, one digit, and one special symbol");
    }


}

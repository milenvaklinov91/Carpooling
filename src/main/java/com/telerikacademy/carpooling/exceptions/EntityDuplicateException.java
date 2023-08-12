package com.telerikacademy.carpooling.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String type, String typeName) {
        super(String.format("%s %s already exists.", type, typeName));
    }

}

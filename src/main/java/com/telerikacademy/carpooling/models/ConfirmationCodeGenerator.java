package com.telerikacademy.carpooling.models;
import java.util.UUID;

public class ConfirmationCodeGenerator {
    public static String generateCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
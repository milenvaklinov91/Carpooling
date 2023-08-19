package com.telerikacademy.carpooling.services.emailServices;
import java.util.UUID;

public class ConfirmationCodeGenerator {
    public static String generateCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
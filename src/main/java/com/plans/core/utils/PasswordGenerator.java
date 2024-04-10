package com.plans.core.utils;

import java.security.SecureRandom;

// Author: ChatGPT
public class PasswordGenerator {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final int LENGTH = 8;

    public static String generate() {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        // Add at least one uppercase letter
        password.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));

        // Add at least one digit
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        // Add at least one special character
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Fill the rest of the password with random characters
        for (int i = 3; i < LENGTH; i++) {
            String possibleCharacters = UPPERCASE_LETTERS + DIGITS + SPECIAL_CHARACTERS;
            password.append(possibleCharacters.charAt(random.nextInt(possibleCharacters.length())));
        }

        // Shuffle the password characters to make it more random
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }
}

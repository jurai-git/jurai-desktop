package com.jurai.util;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.math.BigInteger;

public class StringRandom {
    public static double getRandomFromString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInt = new BigInteger(1, hash);
            // Normalize to [0, 1): divide by 2^256
            return bigInt.doubleValue() / Math.pow(2, 256);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash string", e);
        }
    }
}

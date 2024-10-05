package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class UniqueIdentifier {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALPHABET.length();

    public static String toBase62(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder str = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(BigInteger.valueOf(BASE));
            str.insert(0, ALPHABET.charAt(divmod[1].intValue()));
            number = divmod[0];
        }
        return str.toString();
    }

    public static String generateUniqueId(String email) {
        long timestamp = System.currentTimeMillis();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((email + timestamp).getBytes(StandardCharsets.UTF_8));
            String base62 = toBase62(hash);
            return base62.substring(0, Math.min(11, base62.length()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void main(String[] args) {
        String email = "example@example.com";
        String uniqueId = generateUniqueId(email);
        System.out.println("Unique ID: " + uniqueId);
    }
}

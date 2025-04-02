package com.rrkim.core.common.util;

import java.text.Normalizer;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class StringUtility {

    public static String getRandomString(int length) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }

    public static String normalizeString(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFC);
    }

    public static String encodeToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeFromBase64(String base64Input) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Input);
        return new String(decodedBytes);
    }

}

package com.marvic.factsigner.util;

import org.springframework.util.StringUtils;

public class Utils {

    public static String cleanUpper(String string) {
        if (string == null) {
            return null;
        }
        String cleanedString = string
                .replaceAll("\\s", "_")
                .replaceAll("[^a-zA-Z0-9_]", "")
                .toUpperCase();
        return (StringUtils.hasText(cleanedString)) ? cleanedString : null;
    }

    public static <T> T coalesce(T one, T two) {
        return one != null ? one : two;
    }

}

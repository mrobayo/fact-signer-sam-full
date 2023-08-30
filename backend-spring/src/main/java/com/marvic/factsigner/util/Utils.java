package com.marvic.factsigner.util;

import com.marvic.factsigner.exception.InvalidKeyException;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class Utils {

    public static UUID toUUID(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return uuid;
        } catch (IllegalArgumentException iex) {
            throw new InvalidKeyException("[uuid] has wrong format", iex);
        } catch (Exception ex) {
            throw new InvalidKeyException(ex.getMessage(), ex);
        }
    }

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

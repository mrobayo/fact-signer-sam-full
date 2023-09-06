package com.marvic.factsigner.util;

import com.marvic.factsigner.exception.InvalidKeyException;
import ec.gob.sri.types.SriTipoDoc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class Utils {

    /**
     * Secuencia ID
     */
    public static String secuenciaId(String puntoVentaId, SriTipoDoc tipo) {
        return String.format("%s-%s", puntoVentaId, tipo.name());
    }

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

    public static String removeAccents(String textWithDiacritics) {
        if (textWithDiacritics == null) return "";

        //Quita espacios de sobrantes (dobles espacios)
        String text = textWithDiacritics.replaceAll("(\\s)+", " ");

        text = Normalizer.normalize(
                text.trim().toUpperCase(), Normalizer.Form.NFD);

        return text.replaceAll("[^\\p{ASCII}]", "");
    }


    private static final DateTimeFormatter SHORT_FMT = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    private static final SimpleDateFormat SHORT_DATE = new SimpleDateFormat("dd/MM/yyyy");

    public static String fmtDMY(LocalDate date) {
        return SHORT_FMT.format(date);
    }

    protected static BigDecimal to2Dec(BigDecimal dec) {
        if (dec != null) {
            return dec.setScale(2, RoundingMode.HALF_UP);
        }
        return null;
    }

    public static final String separatorChar = ",";

    /**
     * Split y quita items vacios
     */
    public static String[] split2(String text, String... separator) {
        if (text == null)
            return new String[0];
        else {
            String sep = separatorChar;
            if (separator.length > 0) sep = separator[0];

            int count = 0;
            String[] items = text.split(sep);
            for (String item : items) {
                count += (org.apache.commons.lang3.StringUtils.isEmpty(item)) ? 0: 1;
            }

            String[] result = new String[count];
            int i = 0;
            for (String item : items) {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(item)) result[i++] = item;
            }

            return result;
        }

    }

    public static String fmtDMY(Date fecha) {
        if (fecha == null) return null;
        return SHORT_DATE.format(fecha);
    }

    public static Integer[] splitToInt(String text, String... separator) {
        int len = 0;
        String[] textList = null;

        if (org.apache.commons.lang3.StringUtils.isEmpty(text)) {
            textList = split2(text, separator);
            len = textList.length;
        }
        Integer[] numberList = new Integer[len];

        for(int i=0; i<len; i++) {
            try {
                numberList[i] = Integer.parseInt(textList[i]);
            } catch (Exception e) {
                numberList[i] = null;
            }
        }

        return numberList;
    }

}

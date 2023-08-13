package com.mobilise.task.specifications;

import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ObjectParser {

    public static Integer parseInteger(Object value) {
        String valueAsString = String.valueOf(value);
        try {
            return Integer.parseInt(valueAsString);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static BigDecimal parseBigDecimal(Object value) {
        String valueAsString = String.valueOf(value);
        try {
            return BigDecimal.valueOf(Double.parseDouble(valueAsString));
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Long parseLong(Object value) {
        String valueAsString = String.valueOf(value);
        try {
            return Long.parseLong(valueAsString);
        } catch (Exception ignored) {
        }
        return null;
    }

    @SneakyThrows
    public static String parseValue(String value){
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}

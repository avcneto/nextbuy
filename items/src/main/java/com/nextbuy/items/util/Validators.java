package com.nextbuy.items.util;

public class Validators {
    public static <T> boolean isNullOrEmptyOrBlank(T object) {
        if (object == null) return true;
        if (object instanceof String str) return str.isEmpty() || str.isBlank();

        return false;
    }

}
package com.minecraft.ranks.utils;

public class StringUtils {
    public static String createSeparator(int length) {
        String sep = "";

        for (int i = 0; i<length; i++) sep += "=";

        return sep;
    }
}

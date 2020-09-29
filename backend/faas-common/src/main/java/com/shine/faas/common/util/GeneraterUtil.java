package com.shine.faas.common.util;

public class GeneraterUtil {
    public static String getClassName(String name) {
        return name.toUpperCase().substring(0, 1) + name.substring(1);
    }
    public static String getVarName(String name) {
        return name.toLowerCase().substring(0, 1) + name.substring(1);
    }
}

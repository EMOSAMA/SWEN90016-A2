package com.shine.faas.common.util;

import java.util.ArrayList;
import java.util.List;

public final class StringUtil {

    public static final Boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static final String[] split(String s, String splitTag, boolean removeEmpty) {
        String[] ss = s.split(splitTag);
        if (removeEmpty) {
            List<String> list = new ArrayList<>();
            for (String each : ss) {
                if (isEmpty(each)) {
                    continue;
                }

                list.add(each);
            }

            return list.toArray(new String[0]);
        }
        return ss;
    }

    public static String format(String fmt, String... args) {
        String result = fmt;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", args[i]);
        }
        return result;
    }

    public static String standardOWLString(String s) {
        String newS = s;
        if (newS.endsWith("'"))
            newS += " ";
        newS = newS.replace("\\", "\\\\");
        return newS;
    }
}

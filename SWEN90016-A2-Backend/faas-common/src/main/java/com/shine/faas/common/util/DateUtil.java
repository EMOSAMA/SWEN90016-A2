package com.shine.faas.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static void standardDate(DateTime dateTime) {

        dateTime.setField(DateField.HOUR_OF_DAY, 0);
        dateTime.setField(DateField.MINUTE, 0);
        dateTime.setField(DateField.SECOND, 0);
        dateTime.setField(DateField.MILLISECOND, 0);

    }

    public static String getStandardDate(Date date) {
        return dateFormat.format(date);
    }
}

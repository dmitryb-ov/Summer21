package com.rest.app.orionrestapplication.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Date calculateDeletedDate(Date date) {
        var calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DATE, 10);

        return calendar.getTime();
    }
}

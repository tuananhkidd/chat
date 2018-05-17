package com.kidd.chat.common.adapter.recycler_view_adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtils {
    public static final String FORMAT = "dd/MM/yyyy 'at' hh:mm";

    public static String getDateTimeString(Date date) {
        return new SimpleDateFormat(FORMAT, Locale.US).format(date);
    }
}

package com.example.practise.utils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataConversionFactory {

    public static String fromDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return date == null ? null : dateString;
    }
}

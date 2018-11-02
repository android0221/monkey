package com.run.common.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UTime {

    public static Long timeStrToNumber(String user_time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            System.out.println("timeStrToNumber() " + d);
            return d.getTime();
        } catch (ParseException e) {
            System.out.println(e);
        }
        return 0l;
    }

    public static String chutDownTiem(long d_time) {
        long time = d_time / 1000;
        long hh = time / 3600;
        long mm = (time - 3600 * hh) / 60;
        return addNumber(hh) + ":" + addNumber(mm);
    }

    private static String addNumber(long num) {
        if (num < 10) {
            return "0" + num;
        }
        return num + "";
    }
}
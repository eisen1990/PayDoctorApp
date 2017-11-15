package com.linepayroll.paydoctor.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eisen on 2017-11-16.
 */

public class TimeCompo {

    public static String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t = day.format(new Date(time));
        return t;
    }

    public static int DateStringToDay(String date) {
        int result = 0;
        if(date.length() >= 9) {
            result = Integer.parseInt(date.substring(8, 10));
        }

        return result;
    }
}

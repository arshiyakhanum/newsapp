package com.arshiya.newsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Arshiya on 2020-03-18.
 */
public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();

    public static long toMillis(String timeStamp) {
        long timeInMillis = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'" , Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = sdf.parse(timeStamp);
            timeInMillis = date.getTime();
        } catch (ParseException e) {
            Logger.e(TAG,"toMillis",e);
        }

        return timeInMillis;
    }

    public static String getDisplayDate(long timeInMillis) {
        long diff = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - timeInMillis);
        String displayTime;
        //TODO handle month and year
        if (diff == 0) {
            displayTime = "today";
        } else if (diff == 1) {
            displayTime = "yesterday";
        } else {
            displayTime = diff + " days ago";
        }

        Logger.d(TAG,"displayTime: " + displayTime);
        return displayTime;
    }
}

package com.linkage.study.attend.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cunguoyao on 2017/9/23.
 */

public class Utils {

    public static String formatDate(String date) {
        if(TextUtils.isDigitsOnly(date)) {
            long d = Long.valueOf(date);
            Date dd = new Date(d);
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dd);
            return dateStr;
        }
        return "";
    }

    public static int getScreenWidth(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    public static int getScreenHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }
}

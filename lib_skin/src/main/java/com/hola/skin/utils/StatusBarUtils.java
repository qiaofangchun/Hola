package com.hola.skin.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class StatusBarUtils {

    private StatusBarUtils() {

    }

    public static void forStatusBar(Activity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[]{
                android.R.attr.statusBarColor
        });
        int color = a.getColor(0, 0);
        a.recycle();
        forStatusBar(activity, color);
    }

    public static void forStatusBar(Activity activity, int skinColor) {
        activity.getWindow().setStatusBarColor(skinColor);
    }
}

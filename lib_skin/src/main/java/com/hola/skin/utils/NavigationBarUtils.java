package com.hola.skin.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NavigationBarUtils {
    private NavigationBarUtils() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void forNavigationBar(Activity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[]{
                android.R.attr.navigationBarColor
        });
        int color = a.getColor(0, 0);
        a.recycle();
        forNavigationBar(activity, color);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void forNavigationBar(Activity activity, int skinColor) {
        activity.getWindow().setNavigationBarColor(skinColor);
    }
}

package com.hola.skin.utils;

import android.app.Activity;
import android.content.res.TypedArray;

public class NavigationBarUtils {
    private NavigationBarUtils() {

    }

    public static void forNavigationBar(Activity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[]{
                android.R.attr.navigationBarColor
        });
        int color = a.getColor(0, 0);
        a.recycle();
        forNavigationBar(activity, color);
    }

    public static void forNavigationBar(Activity activity, int skinColor) {
        activity.getWindow().setNavigationBarColor(skinColor);
    }
}

package com.hola.skin.utils;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ActionBarUtils {
    private ActionBarUtils() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void forActionBar(AppCompatActivity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[]{
                android.R.attr.colorPrimary
        });
        int color = a.getColor(0, 0);
        a.recycle();
        forActionBar(activity, color);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void forActionBar(AppCompatActivity activity, int skinColor) {
        ActionBar actionBar = activity.getActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(skinColor));
    }
}

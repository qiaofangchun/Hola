package com.hola.skin.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatActivity;

public class ActionBarUtils {
    private ActionBarUtils() {

    }

    public static void forActionBar(Activity activity) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(0, new int[]{
                android.R.attr.colorPrimary
        });
        int color = a.getColor(0, 0);
        a.recycle();
        forActionBar(activity, color);
    }

    public static void forActionBar(Activity activity, int skinColor) {
        if (activity instanceof AppCompatActivity) {
            androidx.appcompat.app.ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(skinColor));
            }
        }
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(skinColor));
        }
    }
}

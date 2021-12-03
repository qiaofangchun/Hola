package com.hola.skin.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;

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
        if (activity.getActionBar() != null) {
            activity.getActionBar().setBackgroundDrawable(new ColorDrawable(skinColor));
        }
        try {
            if (activity instanceof androidx.appcompat.app.AppCompatActivity) {
                androidx.appcompat.app.ActionBar actionBar = ((androidx.appcompat.app.AppCompatActivity) activity).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setBackgroundDrawable(new ColorDrawable(skinColor));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

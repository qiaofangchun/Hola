package com.hola.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreferencesUtils {
    private static final String PREFERENCE_NAME = "com.hola.skin";

    private PreferencesUtils() {

    }

    public static void put(Context context, String key, Object obj) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof String) {
            editor.putString(key, (String) obj);
        }else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        }
        editor.apply();
    }
}

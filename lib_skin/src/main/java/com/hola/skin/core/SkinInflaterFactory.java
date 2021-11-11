package com.hola.skin.core;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class SkinInflaterFactory implements LayoutInflater.Factory2 {
    private static String TAG = "SkinInflaterFactory";
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
    };
    private LayoutInflater mLayoutInflater;
    private ViewCreatedListener mViewCreatedListener;

    public SkinInflaterFactory(ViewCreatedListener listener) {
        mViewCreatedListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(context);
        }
        View view = null;
        try {
            if (!name.contains(".")) {
                for (String prefix : sClassPrefixList) {
                    view = mLayoutInflater.createView(name, prefix, attrs);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    view = mLayoutInflater.createView(context, name, null, attrs);
                } else {
                    view = originCreateViewForLowSDK(context, name, attrs);
                }
            }
        } catch (Exception ignored) {
            Log.e(TAG, "View class:" + name + ",The view create failure!");
        }
        Log.e("qfc","onCreateView name---->"+name+" is null:"+(view==null));
        if (mViewCreatedListener != null) {
            mViewCreatedListener.onViewCreated(view, attrs);
        }
        return view;
    }

    private View originCreateViewForLowSDK(Context context, String name, AttributeSet attrs) throws Exception {
        Field field = LayoutInflater.class.getDeclaredField("mConstructorArgs");
        field.setAccessible(true);
        Object[] mConstructorArgs = (Object[]) field.get(mLayoutInflater);
        Object lastContext = mConstructorArgs[0];
        mConstructorArgs[0] = context;
        View view = mLayoutInflater.createView(name, null, attrs);
        mConstructorArgs[0] = lastContext;
        return view;
    }

    public interface ViewCreatedListener {
        void onViewCreated(View view, AttributeSet attrs);
    }
}

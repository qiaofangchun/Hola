package com.hola.skin.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatViewInflater;

import com.hola.skin.view.SkinButton;
import com.hola.skin.view.SkinEditText;
import com.hola.skin.view.SkinTextView;

public class SkinViewInflater extends AppCompatViewInflater {
    @Nullable
    @Override
    public View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "FrameLayout":
                view = new FrameLayout(context, attrs);
                verifyNotNull(view, name);
                break;
            case "RelativeLayout":
                view = new RelativeLayout(context, attrs);
                verifyNotNull(view, name);
                break;
            case "TextView":
                view = new SkinTextView(context, attrs);
                verifyNotNull(view, name);
                break;
            case "Button":
                view = new SkinButton(context, attrs);
                verifyNotNull(view, name);
                break;
            case "EditText":
                view = new SkinEditText(context, attrs);
                verifyNotNull(view, name);
                break;
            default:
                break;
        }
        return view;
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName()
                    + "asked to inflate view for<" + name + ">, but return parent inflate view.");
        }
    }
}

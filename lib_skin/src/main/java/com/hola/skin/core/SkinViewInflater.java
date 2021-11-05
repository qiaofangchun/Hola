package com.hola.skin.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatViewInflater;

public class SkinViewInflater extends AppCompatViewInflater {
    private String mName;
    private Context mContext;
    private AttributeSet mAttrs;

    public SkinViewInflater(@NonNull Context context) {
        mContext = context;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setAttrs(AttributeSet attrs) {
        mAttrs = attrs;
    }

    public View autoMatch() {
        View view = null;
        switch (mName) {
            case "FrameLayout":
                view = new FrameLayout(mContext);
                break;
            case "RelativeLayout":
                view = new RelativeLayout(mContext);
                break;
            case "TextView":
                view = new TextView(mContext);
                break;
            case "Button":
                view = new Button(mContext);
                break;
            case "EditText":
                view = new EditText(mContext);
                break;
            default:
                break;
        }
        verifyNotNull(view, mName);
        return view;
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName()
                    + "asked to inflate view for<" + name + ">, but return parent inflate view.");
        }
    }
}

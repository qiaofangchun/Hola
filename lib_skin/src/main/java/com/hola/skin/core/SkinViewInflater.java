package com.hola.skin.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatViewInflater;

import com.hola.skin.view.SkinButton;
import com.hola.skin.view.SkinEditText;
import com.hola.skin.view.SkinTextView;

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
                verifyNotNull(view, mName);
                break;
            case "RelativeLayout":
                view = new RelativeLayout(mContext);
                verifyNotNull(view, mName);
                break;
            case "TextView":
                view = new SkinTextView(mContext);
                verifyNotNull(view, mName);
                break;
            case "Button":
                view = new SkinButton(mContext);
                verifyNotNull(view, mName);
                break;
            case "EditText":
                view = new SkinEditText(mContext);
                verifyNotNull(view, mName);
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

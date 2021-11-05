package com.hola.skin.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.hola.skin.model.AttrsBean;

public class SkinTextView extends androidx.appcompat.widget.AppCompatTextView {
    private AttrsBean attrsBean;

    public SkinTextView(Context context) {
        this(context, null);
    }

    public SkinTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        androidx.appcompat.R.styleable.AppCompatTextView
        attrsBean = new AttrsBean();
        attrsBean.saveResource(attrs,d);
    }


}

package com.hola.skin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.hola.skin.R;
import com.hola.skin.core.ViewMatch;
import com.hola.skin.model.AttrsBean;

public class SkinButton extends AppCompatEditText implements ViewMatch {
    private final AttrsBean attrsBean;

    public SkinButton(@NonNull Context context) {
        this(context, null);
    }

    public SkinButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public SkinButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinTextView, defStyleAttr, 0);
        attrsBean = new AttrsBean();
        attrsBean.saveViewResource(typedArray, R.styleable.SkinTextView);
        typedArray.recycle();
    }

    @Override
    public void skinnableView() {
        int attrId = R.styleable.SkinTextView[R.styleable.SkinEditText_android_textColor];
        int resId = attrsBean.getViewResource(attrId);
        if (resId > 0) {
            ColorStateList color = ContextCompat.getColorStateList(getContext(), resId);
            setTextColor(color);
        }
        attrId = R.styleable.SkinTextView[R.styleable.SkinEditText_android_textColorHint];
        resId = attrsBean.getViewResource(attrId);
        if (resId > 0) {
            ColorStateList color = ContextCompat.getColorStateList(getContext(), resId);
            setHintTextColor(color);
        }
        attrId = R.styleable.SkinTextView[R.styleable.SkinEditText_android_background];
        resId = attrsBean.getViewResource(attrId);
        if (resId > 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
            setBackground(drawable);
        }
    }
}

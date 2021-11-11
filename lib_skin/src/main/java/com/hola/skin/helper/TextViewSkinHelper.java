package com.hola.skin.helper;

import android.view.View;
import android.widget.TextView;

import com.hola.skin.R;
import com.hola.skin.model.ViewAttrs;

public class TextViewSkinHelper extends BaseSkinHelper<TextView> {
    @Override
    public boolean viewMatch(View view) {
        return view instanceof TextView;
    }

    @Override
    public int[] getSkinAttrs() {
        return R.styleable.SkinTextView;
    }

    @Override
    public void applyView(TextView view, ViewAttrs attrs) {
        int resId = getResourceId(attrs, R.styleable.SkinTextView_android_textColor);
        if (resId > 0) {
            view.setTextColor(getColor(resId));
        }
        resId = getResourceId(attrs, R.styleable.SkinTextView_android_background);
        if (resId > 0) {
            view.setBackground(getDrawable(resId));
        }
    }
}

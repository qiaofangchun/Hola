package com.hola.skin.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.hola.skin.SkinCompatManager;
import com.hola.skin.model.ViewWrapper;

public abstract class BaseSkinHelper<T extends View> implements ISkinHelper {
    @Override
    public ViewWrapper createViewWrapper(View view, AttributeSet attrs) {
        ViewWrapper wrapper = new ViewWrapper.Builder().view(view).skinHelper(this).build();
        int[] skinAttrs = getSkinAttrs();
        Context context = view.getContext();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, skinAttrs, 0, 0);
        wrapper.setResource(typedArray, skinAttrs);
        typedArray.recycle();
        return wrapper;
    }

    @Override
    public final void applySkin(ViewWrapper wrapper) {
        applyView((T) wrapper.getView(), wrapper);
    }

    protected abstract int[] getSkinAttrs();

    public abstract void applyView(T view, ViewWrapper wrapper);

    protected final int getResourceId(ViewWrapper wrapper, int skinAttr) {
        int attrId = getSkinAttrs()[skinAttr];
        return wrapper.getResource(attrId);
    }

    public int getColor(int resourceId) {
        return SkinCompatManager.getInstance().getColor(resourceId);
    }

    public ColorStateList getColorStateList(int resourceId) {
        return SkinCompatManager.getInstance().getColorStateList(resourceId);
    }

    public Drawable getDrawable(int resourceId) {
        return SkinCompatManager.getInstance().getDrawable(resourceId);
    }

    public Drawable getBackground(int resourceId) {
        return SkinCompatManager.getInstance().getBackground(resourceId);
    }

    public Typeface getTypeface(int resourceId) {
        return SkinCompatManager.getInstance().getTypeface(resourceId);
    }
}

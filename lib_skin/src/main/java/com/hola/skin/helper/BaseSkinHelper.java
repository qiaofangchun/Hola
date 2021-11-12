package com.hola.skin.helper;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.hola.skin.SkinCompatManager;
import com.hola.skin.model.ViewAttrs;

public abstract class BaseSkinHelper<T extends View> implements ISkinHelper {

    @Override
    public final void applySkin(View view, ViewAttrs attrs) {
        applyView((T) view, attrs);
    }

    public abstract void applyView(T view, ViewAttrs attrs);

    protected final int getResourceId(ViewAttrs attrs, int skinAttr) {
        int attrId = getSkinAttrs()[skinAttr];
        return attrs.getResource(attrId);
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

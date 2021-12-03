package com.hola.skin.helper;

import android.util.AttributeSet;
import android.view.View;

import com.hola.skin.model.ViewWrapper;

public interface ISkinHelper {
    boolean viewMatch(View view);

    ViewWrapper createViewWrapper(View view, AttributeSet attrs);

    void applySkin(ViewWrapper viewWrapper);
}

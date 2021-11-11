package com.hola.skin.helper;

import android.view.View;

import com.hola.skin.model.ViewAttrs;

public interface ISkinHelper {
    boolean viewMatch(View view);

    int[] getSkinAttrs();

    void applySkin(View view, ViewAttrs attrs);
}

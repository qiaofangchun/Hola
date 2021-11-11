package com.hola.skin.model;

import com.hola.skin.helper.ISkinHelper;

public class SkinViewBinder {
    private final ViewAttrs mViewAttrs;
    private final ISkinHelper mSkinHelper;

    public SkinViewBinder(ViewAttrs viewAttrs, ISkinHelper skinHelper) {
        mViewAttrs = viewAttrs;
        mSkinHelper = skinHelper;
    }

    public ViewAttrs getViewAttrs() {
        return mViewAttrs;
    }

    public ISkinHelper getSkinHelper() {
        return mSkinHelper;
    }
}

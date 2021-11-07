package com.hola.skin.model;

import android.content.res.Resources;

public class SkinCache {
    private Resources mSkinResources;
    private String mSkinPackageName;

    public SkinCache(String skinPackageName, Resources skinResources) {
        mSkinResources = skinResources;
        mSkinPackageName = skinPackageName;
    }

    public Resources getSkinResources() {
        return mSkinResources;
    }

    public void setSkinResources(Resources skinResources) {
        this.mSkinResources = skinResources;
    }

    public String getSkinPackageName() {
        return mSkinPackageName;
    }

    public void setSkinPackageName(String skinPackageName) {
        this.mSkinPackageName = skinPackageName;
    }
}

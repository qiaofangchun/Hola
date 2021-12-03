package com.hola.skin.model;

import android.content.res.Resources;

public class SkinCache {
    private String mSkinName;
    private String mSkinPatch;
    private Resources mSkinRes;
    private String mSkinPkgName;

    private SkinCache() {
    }

    private SkinCache(SkinCache skinCache) {
        this.mSkinName = skinCache.mSkinName;
        this.mSkinPatch = skinCache.mSkinPatch;
        this.mSkinRes = skinCache.mSkinRes;
        this.mSkinPkgName = skinCache.mSkinPkgName;
    }

    public String getSkinName() {
        return this.mSkinName;
    }

    public String getSkinPatch() {
        return this.mSkinPatch;
    }

    public Resources getSkinRes() {
        return this.mSkinRes;
    }

    public String getSkinPkgName() {
        return this.mSkinPkgName;
    }

    public static class Builder {
        private SkinCache skinCache;

        public Builder() {
            this.skinCache = new SkinCache();
        }

        public Builder skinName(String skinName) {
            this.skinCache.mSkinName = skinName;
            return this;
        }

        public Builder skinPatch(String patch) {
            this.skinCache.mSkinPatch = patch;
            return this;
        }

        public Builder skinRes(Resources resources) {
            this.skinCache.mSkinRes = resources;
            return this;
        }

        public Builder skinPkgName(String packageName) {
            this.skinCache.mSkinPkgName = packageName;
            return this;
        }

        public SkinCache build() {
            return new SkinCache(this.skinCache);
        }
    }
}

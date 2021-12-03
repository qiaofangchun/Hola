package com.hola.skin.model;

import android.content.res.TypedArray;
import android.util.SparseIntArray;
import android.view.View;

import com.hola.skin.helper.ISkinHelper;

public class ViewWrapper {
    public static final int DEFAULT_VALUE = -1;
    private View mView;
    private ISkinHelper mSkinHelper;
    private SparseIntArray mResourcesMap = new SparseIntArray();

    private ViewWrapper() {
    }

    private ViewWrapper(ViewWrapper viewWrapper) {
        this.mView = viewWrapper.mView;
        this.mSkinHelper = viewWrapper.mSkinHelper;
        this.mResourcesMap = viewWrapper.mResourcesMap;
    }

    public void setView(View view) {
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setSkinHelper(ISkinHelper helper) {
        mSkinHelper = helper;
    }

    public ISkinHelper getSkinHelper() {
        return mSkinHelper;
    }

    public void setResource(TypedArray typedArray, int[] styleable) {
        for (int i = 0; i < typedArray.length(); i++) {
            int attrId = styleable[i];
            int resId = typedArray.getResourceId(i, DEFAULT_VALUE);
            mResourcesMap.put(attrId, resId);
        }
    }

    public int getResource(int attrId) {
        return mResourcesMap.get(attrId);
    }

    public static class Builder {
        private ViewWrapper viewWrapper;

        public Builder() {
            this.viewWrapper = new ViewWrapper();
        }

        public Builder view(View view) {
            this.viewWrapper.setView(view);
            return this;
        }

        public Builder skinHelper(ISkinHelper helper) {
            this.viewWrapper.setSkinHelper(helper);
            return this;
        }

        public Builder resource(TypedArray typedArray, int[] styleable) {
            this.viewWrapper.setResource(typedArray, styleable);
            return this;
        }

        public ViewWrapper build() {
            return new ViewWrapper(this.viewWrapper);
        }
    }
}

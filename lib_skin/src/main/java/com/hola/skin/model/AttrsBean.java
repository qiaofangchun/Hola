package com.hola.skin.model;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

public class AttrsBean {
    public static final int DEFAULT_VALUE = -1;
    private SparseIntArray resourcesMap;

    public AttrsBean() {
        resourcesMap = new SparseIntArray();
    }

    public void saveResource(TypedArray typedArray, int[] styleable) {
        for (int i = 0; i < typedArray.length(); i++) {
            int key = styleable[i];
            int resId = typedArray.getResourceId(i, DEFAULT_VALUE);
            resourcesMap.put(key, resId);
        }
    }
}

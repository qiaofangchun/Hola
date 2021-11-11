package com.hola.skin.model;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

import com.hola.skin.helper.ISkinHelper;

public class ViewAttrs {
    public static final int DEFAULT_VALUE = -1;
    private final SparseIntArray resourcesMap = new SparseIntArray();

    public void saveResource(TypedArray typedArray, int[] styleable) {
        for (int i = 0; i < typedArray.length(); i++) {
            int attrId = styleable[i];
            int resId = typedArray.getResourceId(i, DEFAULT_VALUE);
            resourcesMap.put(attrId, resId);
        }
    }

    public int getResource(int attrId) {
        return resourcesMap.get(attrId);
    }
}

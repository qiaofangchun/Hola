package com.hola.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hola.skin.core.SkinInflaterFactory;
import com.hola.skin.helper.ISkinHelper;
import com.hola.skin.model.SkinViewBinder;
import com.hola.skin.model.ViewAttrs;
import com.hola.skin.utils.ActionBarUtils;
import com.hola.skin.utils.NavigationBarUtils;
import com.hola.skin.utils.StatusBarUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinCompatDelegate {
    private final boolean mCanChangeSkin;
    private final Activity mActivity;
    private final SkinInflaterFactory mFactory;
    private Map<View, SkinViewBinder> mSkinViewBinderMap;
    private List<ISkinHelper> helpers;

    public SkinCompatDelegate(Activity activity, boolean canChangeSkin) {
        mActivity = activity;
        mCanChangeSkin = canChangeSkin;
        helpers = SkinCompatManager.getInstance().getAllSkinHelper();
        mFactory = new SkinInflaterFactory((view, attrs) -> {
            if (view == null) return;
            for (ISkinHelper helper : helpers) {
                if (!helper.viewMatch(view)) {
                    continue;
                }
                saveViewAttrs(helper, view, attrs);
                return;
            }
        });
    }

    public void installFactory2() {
        if (!mCanChangeSkin) return;
        mActivity.getLayoutInflater().setFactory2(mFactory);
    }

    public void useDefaultSkin(int themeId) {
        useDynamicSkin(null, themeId);
    }

    public void useDynamicSkin(String path, int themeId) {
        if (themeId != 0) {
            int themeColor = SkinCompatManager.getInstance().getColor(themeId);
            StatusBarUtils.forStatusBar(mActivity, themeColor);
            ActionBarUtils.forActionBar(mActivity, themeColor);
            NavigationBarUtils.forNavigationBar(mActivity, themeColor);
        }
        SkinCompatManager.getInstance().loadSkinResources(path);
        applySkin(mActivity.getWindow().getDecorView());
    }

    private void saveViewAttrs(ISkinHelper skinHelper, View view, AttributeSet attrs) {
        if (mSkinViewBinderMap == null) {
            mSkinViewBinderMap = new HashMap<>();
        }
        int[] skinAttrs = skinHelper.getSkinAttrs();
        Context context = view.getContext();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, skinAttrs, 0, 0);
        ViewAttrs viewAttrs = new ViewAttrs();
        viewAttrs.saveResource(typedArray, skinAttrs);
        typedArray.recycle();
        mSkinViewBinderMap.put(view, new SkinViewBinder(viewAttrs, skinHelper));
    }

    private void applySkin(View view) {
        if (mSkinViewBinderMap.containsKey(view)) {
            ISkinHelper skinHelper = mSkinViewBinderMap.get(view).getSkinHelper();
            if (skinHelper.viewMatch(view)) {
                skinHelper.applySkin(view, mSkinViewBinderMap.get(view).getViewAttrs());
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applySkin(parent.getChildAt(i));
            }
        }
    }
}

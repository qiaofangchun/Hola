package com.hola.skin;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.hola.skin.core.SkinInflaterFactory;
import com.hola.skin.helper.ISkinHelper;
import com.hola.skin.model.ViewWrapper;
import com.hola.skin.utils.ActionBarUtils;
import com.hola.skin.utils.NavigationBarUtils;
import com.hola.skin.utils.StatusBarUtils;

import java.util.HashMap;
import java.util.Map;

public class SkinCompatDelegate {
    private final boolean mCanChangeSkin;
    private final Activity mActivity;
    private final SkinInflaterFactory mFactory;
    private Map<View, ViewWrapper> mViewWrappers;

    public SkinCompatDelegate(Activity activity, boolean canChangeSkin) {
        mActivity = activity;
        mCanChangeSkin = canChangeSkin;
        mFactory = new SkinInflaterFactory((view, attrs) -> {
            if (view == null) return;
            for (ISkinHelper helper : SkinCompatManager.getInstance().getAllSkinHelper()) {
                if (!helper.viewMatch(view)) {
                    continue;
                }
                saveViewWrapper(view, helper.createViewWrapper(view, attrs));
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

    private void saveViewWrapper(View view, ViewWrapper viewWrapper) {
        if (mViewWrappers == null) {
            mViewWrappers = new HashMap<>();
        }
        mViewWrappers.put(view, viewWrapper);
    }

    private void applySkin(View view) {
        if (mViewWrappers.containsKey(view)) {
            ViewWrapper wrapper = mViewWrappers.get(view);
            if (wrapper.getView() == view) {
                wrapper.getSkinHelper().applySkin(wrapper);
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

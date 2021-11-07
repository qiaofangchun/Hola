package com.hola.skin;

import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import com.hola.skin.core.SkinViewInflater;
import com.hola.skin.core.ViewMatch;
import com.hola.skin.utils.ActionBarUtils;
import com.hola.skin.utils.NavigationBarUtils;
import com.hola.skin.utils.StatusBarUtils;

public class SkinDelegate {
    private boolean mCanChangeSkin;
    private AppCompatActivity mActivity;
    private SkinViewInflater mSkinViewInflater;

    public SkinDelegate(AppCompatActivity activity, boolean canChangeSkin) {
        mActivity = activity;
        mCanChangeSkin = canChangeSkin;
    }

    public void changeInflaterFactory2() {
        if (!mCanChangeSkin) return;
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LayoutInflaterCompat.setFactory2(inflater, mActivity);
    }

    public View skinViewMatch(@NonNull String name, @NonNull AttributeSet attrs) {
        if (!mCanChangeSkin) return null;
        if (mSkinViewInflater == null) {
            mSkinViewInflater = new SkinViewInflater(mActivity);
        }
        mSkinViewInflater.setAttrs(attrs);
        mSkinViewInflater.setName(name);
        return mSkinViewInflater.autoMatch();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void useDefaultSkin(int themeId) {
        useDynamicSkin(null, themeId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void useDynamicSkin(String path, int themeId) {
        if (themeId != 0) {
            SkinManager.getInstance().loadSkinResources(path);
            int themeColor = SkinManager.getInstance().getColor(themeId);
            StatusBarUtils.forStatusBar(mActivity, themeColor);
            ActionBarUtils.forActionBar(mActivity, themeColor);
            NavigationBarUtils.forNavigationBar(mActivity, themeColor);
        }
        applyViews(mActivity.getWindow().getDecorView());
    }

    private void applyViews(View view) {
        if (view instanceof ViewMatch) {
            ((ViewMatch) view).skinnableView();
        }
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyViews(parent.getChildAt(i));
            }
        }
    }
}

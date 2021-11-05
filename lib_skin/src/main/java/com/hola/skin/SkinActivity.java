package com.hola.skin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import com.hola.skin.core.SkinViewInflater;
import com.hola.skin.core.ViewMatch;
import com.hola.skin.utils.ActionBarUtils;
import com.hola.skin.utils.NavigationBarUtils;
import com.hola.skin.utils.StatusBarUtils;

public class SkinActivity extends AppCompatActivity {
    private SkinViewInflater mSkinViewInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (openChangeSkin()) {
            LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), this);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (openChangeSkin()) {
            if (mSkinViewInflater == null) {
                mSkinViewInflater = new SkinViewInflater(this);
            }
            mSkinViewInflater.setAttrs(attrs);
            mSkinViewInflater.setName(name);
            return mSkinViewInflater.autoMatch();
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    protected boolean openChangeSkin() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void useDefaultSkin(int themeId) {
        useDynamicSkin(null, themeId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void useDynamicSkin(String path, int themeId) {
        if (themeId != 0) {
            SkinManager.getInstance().loadSkinResources(path);
            int themeColor = SkinManager.getInstance().getColor(themeId);
            StatusBarUtils.forStatusBar(this, themeColor);
            ActionBarUtils.forActionBar(this, themeColor);
            NavigationBarUtils.forNavigationBar(this, themeColor);
        }
        applyViews(getWindow().getDecorView());
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

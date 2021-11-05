package com.hola.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SkinManager {
    private static final String ADD_ASSET_PATH = "addAssetPath";
    private static volatile SkinManager instance;

    private Application mApplication;
    private Resources mAppResources;
    private Resources mSkinResources;
    private String mSkinPackageName;
    private boolean isDefaultSkin = true;


    private SkinManager(Application application) {
        mApplication = application;
        mAppResources = mApplication.getResources();
    }

    public static SkinManager init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
        return instance;
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public boolean isDefaultSkin() {
        return isDefaultSkin;
    }

    public void loadSkinResources(String skinPath) {
        PackageInfo packageInfo = mApplication.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            return;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH, String.class);
            method.setAccessible(true);
            method.invoke(assetManager, skinPath);

            mSkinPackageName = packageInfo.packageName;
            mSkinResources = new Resources(assetManager,
                    mAppResources.getDisplayMetrics(), mAppResources.getConfiguration());
            isDefaultSkin = TextUtils.isEmpty(mSkinPackageName);
        } catch (Exception e) {
            isDefaultSkin = true;
            e.printStackTrace();
        }
    }

    public int getColor(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mAppResources.getColor(ids) : mSkinResources.getColor(ids);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mAppResources.getColorStateList(ids) : mSkinResources.getColorStateList(ids);
    }

    public Drawable getDrawableOrMipMap(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mAppResources.getDrawable(ids) : mSkinResources.getDrawable(ids);
    }

    public String getString(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? mAppResources.getString(ids) : mSkinResources.getString(ids);
    }

    public Object getBackgroundOrSrc(int resourceId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resourceId);
        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);
            case "drawable":
            case "mipmap":
                return getDrawableOrMipMap(resourceId);
        }
        return null;
    }

    public Typeface getTypeface(int resourceId) {
        String skinTypefacePath = getString(resourceId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        Resources resources = isDefaultSkin ? mAppResources : mSkinResources;
        return Typeface.createFromAsset(resources.getAssets(), skinTypefacePath);
    }

    private int getSkinResourceIds(int resourceId) {
        if (mSkinResources == null) {
            return resourceId;
        }
        String resourceName = mAppResources.getResourceEntryName(resourceId);
        String resourceTypeName = mAppResources.getResourceTypeName(resourceId);
        int skinResourceId = mSkinResources.getIdentifier(resourceName, resourceTypeName, mSkinPackageName);
        isDefaultSkin = (skinResourceId == 0);
        return isDefaultSkin ? resourceId : skinResourceId;
    }
}

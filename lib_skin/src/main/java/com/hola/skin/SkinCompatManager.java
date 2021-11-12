package com.hola.skin;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.core.content.res.ResourcesCompat;

import com.hola.skin.helper.ISkinHelper;
import com.hola.skin.model.SkinCache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinCompatManager {
    private static final String ADD_ASSET_PATH = "addAssetPath";
    private static volatile SkinCompatManager instance;

    private Application mApplication;
    private Resources mAppResources;
    private Resources mSkinResources;
    private String mSkinPackageName;
    private boolean isDefaultSkin = true;
    private Map<String, SkinCache> mSkinCache;
    private List<ISkinHelper> helpers;

    private SkinCompatManager(Application application) {
        mApplication = application;
        mAppResources = mApplication.getResources();
        mSkinCache = new HashMap<>();
        helpers = new ArrayList<>();
    }

    public void addSkinHelper(ISkinHelper skinHelper) {
        helpers.add(skinHelper);
    }

    public List<ISkinHelper> getAllSkinHelper() {
        return helpers;
    }

    public static SkinCompatManager init(Application application) {
        if (instance == null) {
            synchronized (SkinCompatManager.class) {
                if (instance == null) {
                    instance = new SkinCompatManager(application);
                }
            }
        }
        return instance;
    }

    public static SkinCompatManager getInstance() {
        return instance;
    }

    public boolean isDefaultSkin() {
        return isDefaultSkin;
    }

    public void loadSkinResources(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            isDefaultSkin = true;
            return;
        }
        if (findSkinCache(skinPath)) {
            isDefaultSkin = false;
            return;
        }
        String skinPackageName = getSkinPackageName(skinPath);
        if (TextUtils.isEmpty(skinPackageName)) {
            isDefaultSkin = true;
            return;
        }
        mSkinPackageName = skinPackageName;
        Resources resources = createSkinResources(skinPath);
        if (resources == null) {
            isDefaultSkin = true;
            return;
        }
        isDefaultSkin = false;
        mSkinResources = resources;
        mSkinCache.put(skinPath, new SkinCache(mSkinPackageName, mSkinResources));
    }

    private boolean findSkinCache(String skinPath) {
        SkinCache skinCache = mSkinCache.get(skinPath);
        if (skinCache == null) {
            return false;
        }
        mSkinPackageName = skinCache.getSkinPackageName();
        mSkinResources = skinCache.getSkinResources();
        return true;
    }

    private String getSkinPackageName(String skinPath) {
        try {
            PackageInfo packageInfo = mApplication.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            if (packageInfo == null) {
                throw new IllegalArgumentException("This file is not Skin, Path:" + skinPath);
            }
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Resources createSkinResources(String skinPath) {
        Resources resources = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getDeclaredMethod(ADD_ASSET_PATH, String.class);
            method.setAccessible(true);
            method.invoke(assetManager, skinPath);
            resources = new Resources(assetManager,
                    mAppResources.getDisplayMetrics(), mAppResources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Resources getResources() {
        return isDefaultSkin ? mAppResources : mSkinResources;
    }

    public int getColor(int resourceId) {
        int id = getSkinResourceId(resourceId);
        return ResourcesCompat.getColor(getResources(), id, null);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int id = getSkinResourceId(resourceId);
        return ResourcesCompat.getColorStateList(getResources(), id, null);
    }

    public Drawable getDrawable(int resourceId) {
        int id = getSkinResourceId(resourceId);
        return ResourcesCompat.getDrawable(getResources(), id, null);
    }

    public Drawable getBackground(int resourceId) {
        String resourceTypeName = mAppResources.getResourceTypeName(resourceId);
        switch (resourceTypeName) {
            case "color":
                return new ColorDrawable(getColor(resourceId));
            case "drawable":
            case "mipmap":
                return getDrawable(resourceId);
        }
        return null;
    }

    public Typeface getTypeface(int resourceId) {
        int id = getSkinResourceId(resourceId);
        String skinTypefacePath = getResources().getString(id);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        return Typeface.createFromAsset(getResources().getAssets(), skinTypefacePath);
    }

    private int getSkinResourceId(int resourceId) {
        if (mSkinResources == null || isDefaultSkin) {
            return resourceId;
        }
        String resourceName = mAppResources.getResourceEntryName(resourceId);
        String resourceTypeName = mAppResources.getResourceTypeName(resourceId);
        int skinResourceId = mSkinResources.getIdentifier(resourceName, resourceTypeName, mSkinPackageName);
        isDefaultSkin = (skinResourceId == 0);
        return isDefaultSkin ? resourceId : skinResourceId;
    }
}

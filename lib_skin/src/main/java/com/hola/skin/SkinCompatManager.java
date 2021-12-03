package com.hola.skin;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.core.content.res.ResourcesCompat;

import com.hola.skin.helper.ISkinHelper;
import com.hola.skin.model.SkinCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinCompatManager {
    private Application mApplication;
    private Resources mAppResources;
    private Resources mSkinResources;
    private String mSkinPackageName;
    private boolean isDefaultSkin = true;
    private Map<String, SkinCache> mSkinCache;
    private List<ISkinHelper> helpers;

    public static class InnerHolder {
        public static final SkinCompatManager instance = new SkinCompatManager();
    }

    public static SkinCompatManager getInstance() {
        return InnerHolder.instance;
    }

    private SkinCompatManager() {
    }

    public SkinCompatManager init(Application application) {
        mApplication = application;
        mAppResources = mApplication.getResources();
        mSkinCache = new HashMap<>();
        helpers = new ArrayList<>();
        return this;
    }

    public SkinCompatManager addSkinHelper(ISkinHelper skinHelper) {
        helpers.add(skinHelper);
        return this;
    }

    public List<ISkinHelper> getAllSkinHelper() {
        return helpers;
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
        mSkinCache.put(skinPath, new SkinCache.Builder().skinPkgName(skinPackageName).skinRes(resources).build());
    }

    private boolean findSkinCache(String skinPath) {
        SkinCache skinCache = mSkinCache.get(skinPath);
        if (skinCache == null) {
            return false;
        }
        mSkinPackageName = skinCache.getSkinPkgName();
        mSkinResources = skinCache.getSkinRes();
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
        try {
            PackageInfo pkgInfo = mApplication.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            if (pkgInfo == null) {
                return null;
            }
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            appInfo.sourceDir = skinPath;
            appInfo.publicSourceDir = skinPath;
            Resources res = mApplication.getPackageManager().getResourcesForApplication(appInfo);
            Resources superRes = mApplication.getResources();
            return new Resources(res.getAssets(), superRes.getDisplayMetrics(), superRes.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

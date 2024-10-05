package com.pasc.lib.widget.theme;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import com.pasc.lib.widget.theme.app.SkinActivityLifecycle;
import com.pasc.lib.widget.theme.app.SkinLayoutInflater;
import com.pasc.lib.widget.theme.load.SkinAssetsLoader;
import com.pasc.lib.widget.theme.load.SkinBuildInLoader;
import com.pasc.lib.widget.theme.load.SkinNoneLoader;
import com.pasc.lib.widget.theme.load.SkinPrefixBuildInLoader;
import com.pasc.lib.widget.theme.observe.SkinObservable;
import com.pasc.lib.widget.theme.utils.SkinPreference;
import com.pasc.lib.widget.theme.res.SkinCompatResources;

public class SkinCompatManager extends SkinObservable {
    public static final int SKIN_LOADER_STRATEGY_NONE = -1;
    public static final int SKIN_LOADER_STRATEGY_ASSETS = 0;
    public static final int SKIN_LOADER_STRATEGY_BUILD_IN = 1;
    public static final int SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN = 2;
    private static volatile SkinCompatManager sInstance;
    private final Object mLock = new Object();
    private final Context mAppContext;
    private boolean mLoading = false;
    private List<SkinLayoutInflater> mInflaters = new ArrayList<>();
    private List<SkinLayoutInflater> mHookInflaters = new ArrayList<>();
    private SparseArray<SkinLoaderStrategy> mStrategyMap = new SparseArray<>();
    private boolean mSkinAllActivityEnable = true;
    private boolean mSkinStatusBarColorEnable = false;
    private boolean mSkinWindowBackgroundColorEnable = true;


    public interface SkinLoaderListener {

        void onStart();


        void onSuccess();


        void onFailed(String errMsg);
    }


    public interface SkinLoaderStrategy {

        String loadSkinInBackground(Context context, String skinName);


        String getTargetResourceEntryName(Context context, String skinName, int resId);


        ColorStateList getColor(Context context, String skinName, int resId);


        ColorStateList getColorStateList(Context context, String skinName, int resId);


        Drawable getDrawable(Context context, String skinName, int resId);


        int getType();
    }


    public static SkinCompatManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinCompatManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatManager(context);
                }
            }
        }
        SkinPreference.init(context);
        return sInstance;
    }

    public static SkinCompatManager getInstance() {
        return sInstance;
    }


    public static SkinCompatManager withoutActivity(Application application) {
        init(application);
        SkinActivityLifecycle.init(application);
        return sInstance;
    }

    private SkinCompatManager(Context context) {
        mAppContext = context.getApplicationContext();
        initLoaderStrategy();
    }

    private void initLoaderStrategy() {
        mStrategyMap.put(SKIN_LOADER_STRATEGY_NONE, new SkinNoneLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_ASSETS, new SkinAssetsLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_BUILD_IN, new SkinBuildInLoader());
        mStrategyMap.put(SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN, new SkinPrefixBuildInLoader());
    }

    public Context getContext() {
        return mAppContext;
    }


    public SkinCompatManager addStrategy(SkinLoaderStrategy strategy) {
        mStrategyMap.put(strategy.getType(), strategy);
        return this;
    }

    public SparseArray<SkinLoaderStrategy> getStrategies() {
        return mStrategyMap;
    }


    public SkinCompatManager addInflater(SkinLayoutInflater inflater) {
        mInflaters.add(inflater);
        return this;
    }

    public List<SkinLayoutInflater> getInflaters() {
        return mInflaters;
    }



    public SkinCompatManager addHookInflater(SkinLayoutInflater inflater) {
        mHookInflaters.add(inflater);
        return this;
    }

    public List<SkinLayoutInflater> getHookInflaters() {
        return mHookInflaters;
    }


    @Deprecated
    public String getCurSkinName() {
        return SkinPreference.getInstance().getSkinName();
    }


    public void restoreDefaultTheme() {
        loadSkin("", SKIN_LOADER_STRATEGY_NONE);
    }


    public SkinCompatManager setSkinAllActivityEnable(boolean enable) {
        mSkinAllActivityEnable = enable;
        return this;
    }

    public boolean isSkinAllActivityEnable() {
        return mSkinAllActivityEnable;
    }


    public SkinCompatManager setSkinStatusBarColorEnable(boolean enable) {
        mSkinStatusBarColorEnable = enable;
        return this;
    }

    public boolean isSkinStatusBarColorEnable() {
        return mSkinStatusBarColorEnable;
    }


    public SkinCompatManager setSkinWindowBackgroundEnable(boolean enable) {
        mSkinWindowBackgroundColorEnable = enable;
        return this;
    }

    public boolean isSkinWindowBackgroundEnable() {
        return mSkinWindowBackgroundColorEnable;
    }


    public AsyncTask loadSkin() {
        String skin = SkinPreference.getInstance().getSkinName();
        int strategy = SkinPreference.getInstance().getSkinStrategy();
        if (TextUtils.isEmpty(skin) || strategy == SKIN_LOADER_STRATEGY_NONE) {
            return null;
        }
        return loadSkin(skin, null, strategy);
    }


    public AsyncTask loadSkin(SkinLoaderListener listener) {
        String skin = SkinPreference.getInstance().getSkinName();
        int strategy = SkinPreference.getInstance().getSkinStrategy();
        if (TextUtils.isEmpty(skin) || strategy == SKIN_LOADER_STRATEGY_NONE) {
            return null;
        }
        return loadSkin(skin, listener, strategy);
    }

    @Deprecated
    public AsyncTask loadSkin(String skinName) {
        return loadSkin(skinName, null);
    }

    @Deprecated
    public AsyncTask loadSkin(String skinName, final SkinLoaderListener listener) {
        return loadSkin(skinName, listener, SKIN_LOADER_STRATEGY_ASSETS);
    }


    public AsyncTask loadSkin(String skinName, int strategy) {
        return loadSkin(skinName, null, strategy);
    }


    public AsyncTask loadSkin(String skinName, SkinLoaderListener listener, int strategy) {
        SkinLoaderStrategy loaderStrategy = mStrategyMap.get(strategy);
        if (loaderStrategy == null) {
            return null;
        }
        return new SkinLoadTask(listener, loaderStrategy).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, skinName);
    }

    private class SkinLoadTask extends AsyncTask<String, Void, String> {
        private final SkinLoaderListener mListener;
        private final SkinLoaderStrategy mStrategy;

        SkinLoadTask(@Nullable SkinLoaderListener listener, @NonNull SkinLoaderStrategy strategy) {
            mListener = listener;
            mStrategy = strategy;
        }

        protected void onPreExecute() {
            if (mListener != null) {
                mListener.onStart();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            synchronized (mLock) {
                while (mLoading) {
                    try {
                        mLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mLoading = true;
            }
            try {
                if (params.length == 1) {
                    String skinName = mStrategy.loadSkinInBackground(mAppContext, params[0]);
                    if (TextUtils.isEmpty(skinName)) {
                        SkinCompatResources.getInstance().reset(mStrategy);
                    }
                    return params[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SkinCompatResources.getInstance().reset();
            return null;
        }

        protected void onPostExecute(String skinName) {
            synchronized (mLock) {

                if (skinName != null) {
                    SkinPreference.getInstance().setSkinName(skinName).setSkinStrategy(mStrategy.getType()).commitEditor();
                    notifyUpdateSkin();
                    if (mListener != null) mListener.onSuccess();
                } else {
                    SkinPreference.getInstance().setSkinName("").setSkinStrategy(SKIN_LOADER_STRATEGY_NONE).commitEditor();
                    if (mListener != null) mListener.onFailed("皮肤资源获取失败");
                }
                mLoading = false;
                mLock.notifyAll();
            }
        }
    }


    public String getSkinPackageName(String skinPkgPath) {
        PackageManager mPm = mAppContext.getPackageManager();
        PackageInfo info = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
        return info.packageName;
    }


    @Nullable
    public Resources getSkinResources(String skinPkgPath) {
        try {
            PackageInfo packageInfo = mAppContext.getPackageManager().getPackageArchiveInfo(skinPkgPath, 0);
            packageInfo.applicationInfo.sourceDir = skinPkgPath;
            packageInfo.applicationInfo.publicSourceDir = skinPkgPath;
            Resources res = mAppContext.getPackageManager().getResourcesForApplication(packageInfo.applicationInfo);
            Resources superRes = mAppContext.getResources();
            return new Resources(res.getAssets(), superRes.getDisplayMetrics(), superRes.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
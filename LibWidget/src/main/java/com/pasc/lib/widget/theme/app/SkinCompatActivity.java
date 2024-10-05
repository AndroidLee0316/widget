package com.pasc.lib.widget.theme.app;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.pasc.lib.widget.theme.SkinCompatManager;
import com.pasc.lib.widget.theme.res.SkinCompatResources;
import com.pasc.lib.widget.theme.observe.SkinObservable;
import com.pasc.lib.widget.theme.observe.SkinObserver;
import com.pasc.lib.widget.theme.res.SkinCompatThemeUtils;

import static com.pasc.lib.widget.theme.widget.SkinCompatHelper.INVALID_ID;
import static com.pasc.lib.widget.theme.widget.SkinCompatHelper.checkResourceId;


@Deprecated
public class SkinCompatActivity extends AppCompatActivity implements SkinObserver {

    private SkinCompatDelegate mSkinDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), getSkinDelegate());
        super.onCreate(savedInstanceState);
        updateStatusBarColor();
        updateWindowBackground();
    }

    @NonNull
    public SkinCompatDelegate getSkinDelegate() {
        if (mSkinDelegate == null) {
            mSkinDelegate = SkinCompatDelegate.create(this);
        }
        return mSkinDelegate;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinCompatManager.getInstance().addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinCompatManager.getInstance().deleteObserver(this);
    }


    protected boolean skinStatusBarColorEnable() {
        return true;
    }

    protected void updateStatusBarColor() {
        if (skinStatusBarColorEnable() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusBarColorResId = SkinCompatThemeUtils.getStatusBarColorResId(this);
            int colorPrimaryDarkResId = SkinCompatThemeUtils.getColorPrimaryDarkResId(this);
            if (checkResourceId(statusBarColorResId) != INVALID_ID) {
                getWindow().setStatusBarColor(SkinCompatResources.getColor(this, statusBarColorResId));
            } else if (checkResourceId(colorPrimaryDarkResId) != INVALID_ID) {
                getWindow().setStatusBarColor(SkinCompatResources.getColor(this, colorPrimaryDarkResId));
            }
        }
    }

    protected void updateWindowBackground() {
        int windowBackgroundResId = SkinCompatThemeUtils.getWindowBackgroundResId(this);
        if (checkResourceId(windowBackgroundResId) != INVALID_ID) {
            Drawable drawable = SkinCompatResources.getDrawableCompat(this, windowBackgroundResId);
            if (drawable != null) {
                getWindow().setBackgroundDrawable(drawable);
            }
        }
    }

    @Override
    public void updateSkin(SkinObservable observable, Object o) {
        updateStatusBarColor();
        updateWindowBackground();
        getSkinDelegate().applySkin();
    }
}

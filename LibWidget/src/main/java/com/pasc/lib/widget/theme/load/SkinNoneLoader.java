package com.pasc.lib.widget.theme.load;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.pasc.lib.widget.theme.SkinCompatManager;
import com.pasc.lib.widget.theme.SkinCompatManager.SkinLoaderStrategy;

public class SkinNoneLoader implements SkinLoaderStrategy {
    @Override
    public String loadSkinInBackground(Context context, String skinName) {
        return "";
    }

    @Override
    public String getTargetResourceEntryName(Context context, String skinName, int resId) {
        return "";
    }

    @Override
    public ColorStateList getColor(Context context, String skinName, int resId) {
        return null;
    }

    @Override
    public ColorStateList getColorStateList(Context context, String skinName, int resId) {
        return null;
    }

    @Override
    public Drawable getDrawable(Context context, String skinName, int resId) {
        return null;
    }

    @Override
    public int getType() {
        return SkinCompatManager.SKIN_LOADER_STRATEGY_NONE;
    }
}

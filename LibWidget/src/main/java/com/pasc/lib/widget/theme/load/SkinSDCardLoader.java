package com.pasc.lib.widget.theme.load;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.pasc.lib.widget.theme.SkinCompatManager;
import com.pasc.lib.widget.theme.SkinCompatManager.SkinLoaderStrategy;
import com.pasc.lib.widget.theme.res.SkinCompatResources;
import com.pasc.lib.widget.theme.utils.SkinFileUtils;

public abstract class SkinSDCardLoader implements SkinLoaderStrategy {
    @Override
    public String loadSkinInBackground(Context context, String skinName) {
        if (TextUtils.isEmpty(skinName)) {
            return skinName;
        }
        String skinPkgPath = getSkinPath(context, skinName);
        if (SkinFileUtils.isFileExists(skinPkgPath)) {
            String pkgName = SkinCompatManager.getInstance().getSkinPackageName(skinPkgPath);
            Resources resources = SkinCompatManager.getInstance().getSkinResources(skinPkgPath);
            if (resources != null && !TextUtils.isEmpty(pkgName)) {
                SkinCompatResources.getInstance().setupSkin(
                        resources,
                        pkgName,
                        skinName,
                        this);
                return skinName;
            }
        }
        return null;
    }

    protected abstract String getSkinPath(Context context, String skinName);

    @Override
    public String getTargetResourceEntryName(Context context, String skinName, int resId) {
        return null;
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
}

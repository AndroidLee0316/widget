package com.pasc.lib.widget.theme.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.pasc.lib.widget.theme.widget.SkinCompatSupportable;



public class SkinCompatDelegate implements LayoutInflaterFactory {
    private final Context mContext;
    private SkinCompatViewInflater mSkinCompatViewInflater;
    private List<WeakReference<SkinCompatSupportable>> mSkinHelpers = new ArrayList<>();

    private SkinCompatDelegate(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            view = createView(parent, name, context, attrs);
        } catch (Exception e) {

        }

        if (view == null) {
            return null;
        }
        if (view instanceof SkinCompatSupportable) {
            mSkinHelpers.add(new WeakReference<>((SkinCompatSupportable) view));
        }

        return view;
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mSkinCompatViewInflater == null) {
            mSkinCompatViewInflater = new SkinCompatViewInflater();
        }


        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mSkinCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21,
                true,
                VectorEnabledTintResources.shouldBeUsed()
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {

            return false;
        }
        if (mContext instanceof Activity) {
            final View windowDecor = ((Activity) mContext).getWindow().getDecorView();
            while (true) {
                if (parent == null) {




                    return true;
                } else if (parent == windowDecor || !(parent instanceof View)
                        || ViewCompat.isAttachedToWindow((View) parent)) {




                    return false;
                }
                parent = parent.getParent();
            }
        }
        return false;
    }

    public static SkinCompatDelegate create(Context context) {
        return new SkinCompatDelegate(context);
    }

    public void applySkin() {
        if (mSkinHelpers != null && !mSkinHelpers.isEmpty()) {
            for (WeakReference ref : mSkinHelpers) {
                if (ref != null && ref.get() != null) {
                    ((SkinCompatSupportable) ref.get()).applySkin();
                }
            }
        }
    }
}

package com.pasc.lib.widget.theme.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;

import com.pasc.lib.widget.R;



public class SkinCompatRatingBar extends AppCompatRatingBar implements SkinCompatSupportable {
    private SkinCompatProgressBarHelper mSkinCompatProgressBarHelper;

    public SkinCompatRatingBar(Context context) {
        this(context, null);
    }

    public SkinCompatRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ratingBarStyle);
    }

    public SkinCompatRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSkinCompatProgressBarHelper = new SkinCompatProgressBarHelper(this);
        mSkinCompatProgressBarHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void applySkin() {
        if (mSkinCompatProgressBarHelper != null) {
            mSkinCompatProgressBarHelper.applySkin();
        }
    }

}

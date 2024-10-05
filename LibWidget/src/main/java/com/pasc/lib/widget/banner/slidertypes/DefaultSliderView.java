package com.pasc.lib.widget.banner.slidertypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.lib.widget.R;

;

/**
 * a simple slider view, which just show an image. If you want to make your own slider view,
 * <p>
 * just extend BaseSliderView, and implement getView() method.
 */
public class DefaultSliderView extends BaseSliderView {

    private RoundedImageView roundedImageView;
    private float mRadius;

    public DefaultSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default, null);
        roundedImageView = (RoundedImageView) v.findViewById(R.id.daimajia_slider_image);
        roundedImageView.setCornerRadius(mRadius);
        bindEventAndShow(v, roundedImageView);
        return v;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        if (mRadius != radius) {
            this.mRadius = radius;
            if(roundedImageView!=null){
                roundedImageView.setCornerRadius(radius);
            }
        }
    }
}

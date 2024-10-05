package com.pasc.lib.widget.banner.slidertypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.lib.widget.R;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    private RoundedImageView roundedImageView;
    private float mRadius;

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

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        roundedImageView = (RoundedImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        roundedImageView.setCornerRadius(getRadius());
        bindEventAndShow(v, roundedImageView);
        return v;
    }
}

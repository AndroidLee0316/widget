package com.pasc.lib.widget.banner.transformers;

/**
 * Created by daimajia on 14-5-29.
 */
import android.view.View;



public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }

}
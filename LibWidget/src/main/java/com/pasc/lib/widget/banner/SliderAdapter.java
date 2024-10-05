package com.pasc.lib.widget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.lib.widget.banner.slidertypes.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A slider ListAdapter
 */
public class SliderAdapter extends PagerAdapter implements BaseSliderView.ImageLoadListener {

    private Context mContext;
    private ArrayList<BaseSliderView> mImageContents;

    public SliderAdapter(Context context) {
        mContext = context;
        mImageContents = new ArrayList<BaseSliderView>();
    }

    public ArrayList<BaseSliderView> getImageContents() {
        return mImageContents;
    }

    public <T extends BaseSliderView> void addSlider(T slider) {
        slider.setOnImageLoadListener(this);
        mImageContents.add(slider);
        notifyDataSetChanged();
    }

    public <T extends BaseSliderView> void addSliders(List<T> sliders) {
        if (sliders == null) return;
        for (T slider : sliders) {
            slider.setOnImageLoadListener(this);
        }
        mImageContents.addAll(sliders);
        notifyDataSetChanged();
    }

    public <T extends BaseSliderView> void setSliders(List<T> sliders) {
        if (sliders == null) return;
        for (T slider : sliders) {
            slider.setOnImageLoadListener(this);
        }
        mImageContents.clear();
        mImageContents.addAll(sliders);
        notifyDataSetChanged();
    }

    public BaseSliderView getSliderView(int position) {
        if (position < 0 || position >= mImageContents.size()) {
            return null;
        } else {
            return mImageContents.get(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public <T extends BaseSliderView> int getIndexOfSlider(T slider) {
        if (slider == null) return -1;
        return mImageContents.indexOf(slider);
    }

    public <T extends BaseSliderView> void removeSlider(T slider) {
        if (mImageContents.contains(slider)) {
            mImageContents.remove(slider);
            notifyDataSetChanged();
        }
    }

    public void removeSliderAt(int position) {
        if (mImageContents.size() > position) {
            mImageContents.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeAllSliders() {
        mImageContents.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageContents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseSliderView b = mImageContents.get(position);
        View v = b.getView();
        container.addView(v);
        return v;
    }

    @Override
    public void onStart(BaseSliderView target) {

    }

    /**
     * When image download error, then remove.
     *
     * @param result
     * @param target
     */
    @Override
    public void onEnd(boolean result, BaseSliderView target) {
        if (target.isErrorDisappear() == false || result == true) {
            return;
        }
        for (BaseSliderView slider : mImageContents) {
            if (slider.equals(target)) {
                removeSlider(target);
                break;
            }
        }
    }

}

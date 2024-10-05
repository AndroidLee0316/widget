package com.pasc.lib.widget.banner.imageloader;

import android.widget.ImageView;

import java.io.File;

public class ImageLoadParams {

    public ImageView targetImageView;
    public String mUrl;
    public File mFile;
    public int mRes;
    public String mScaleType = ScaleType.Fit.name();
    public int mEmptyPlaceHolderRes;
    public int mErrorPlaceHolderRes;
}

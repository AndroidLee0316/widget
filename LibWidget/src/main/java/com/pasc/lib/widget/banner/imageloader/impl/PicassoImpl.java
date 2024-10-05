package com.pasc.lib.widget.banner.imageloader.impl;

import android.content.Context;
import android.widget.ImageView;

import com.pasc.lib.widget.banner.imageloader.ImageLoadCallback;
import com.pasc.lib.widget.banner.imageloader.ImageLoadParams;
import com.pasc.lib.widget.banner.imageloader.ImageLoader;
import com.pasc.lib.widget.banner.imageloader.ScaleType;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

public class PicassoImpl implements ImageLoader {

    private Picasso mPicasso;
    private Context mContext;

    public PicassoImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void load(ImageLoadParams imageLoadParams, final ImageLoadCallback imageLoadCallback) {
        String mUrl = imageLoadParams.mUrl;
        File mFile = imageLoadParams.mFile;
        int mRes = imageLoadParams.mRes;
        int empty = imageLoadParams.mEmptyPlaceHolderRes;
        int error = imageLoadParams.mErrorPlaceHolderRes;
        ScaleType mScaleType = ScaleType.valueOf(imageLoadParams.mScaleType);
        ImageView targetImageView = imageLoadParams.targetImageView;

        Picasso p = (mPicasso != null) ? mPicasso : Picasso.with(mContext);
        RequestCreator rq = null;

        if (mUrl != null) {
            rq = p.load(mUrl);
        } else if (mFile != null) {
            rq = p.load(mFile);
        } else if (mRes != 0) {
            rq = p.load(mRes);
        } else {
            return;
        }

        if (rq == null) {
            return;
        }

        if (empty != 0) {
            rq.placeholder(empty);
        }

        if (error != 0) {
            rq.error(error);
        }

        switch (mScaleType) {
            case Fit:
                rq.fit();
                break;
            case CenterCrop:
                rq.fit().centerCrop();
                break;
            case CenterInside:
                rq.fit().centerInside();
                break;
        }

        rq.into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onSuccess();
                }
            }

            @Override
            public void onError() {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onError();
                }
            }
        });
    }
}

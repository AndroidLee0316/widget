package com.pasc.lib.widget.banner.imageloader.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pasc.lib.widget.banner.imageloader.ImageLoadCallback;
import com.pasc.lib.widget.banner.imageloader.ImageLoadParams;
import com.pasc.lib.widget.banner.imageloader.ImageLoader;
import com.pasc.lib.widget.banner.imageloader.ScaleType;

import java.io.File;

public class GlideImpl implements ImageLoader {

    private Context mContext;

    public GlideImpl(Context context) {
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

        RequestManager requestManager = Glide.with(mContext);

        RequestBuilder<Drawable> requestBuilder = null;

        RequestOptions requestOptions = new RequestOptions();
        if (mUrl != null) {
            requestBuilder = requestManager.load(mUrl);
        } else if (mFile != null) {
            requestBuilder = requestManager.load(mFile);
        } else if (mRes != 0) {
            requestBuilder = requestManager.load(mRes);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else {
            return;
        }

        if (requestBuilder == null) {
            return;
        }

        if (empty != 0) {
            requestOptions = requestOptions.placeholder(empty);
        }

        if (error != 0) {
            requestOptions = requestOptions.error(error);
        }

        switch (mScaleType) {
            case CenterCrop:
                requestOptions = requestOptions.centerCrop();
                break;
            case CenterInside:
                requestOptions = requestOptions.centerInside();
                break;
            default:
                requestOptions = requestOptions.centerCrop();
                break;
        }

        requestBuilder.apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onError();
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onSuccess();
                }
                return false;
            }
        }).into(targetImageView);
    }
}

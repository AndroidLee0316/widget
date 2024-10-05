package com.pasc.lib.widget.dialog.loading;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnDismissListener;

public class LoadingDialogFragment extends BaseDialogFragment {

    public static final String ARG_MESSAGE = "message";
    public static final String ARG_ROTATE_IMAGE_RES = "rotateImageRes";

    private ImageView img;
    private TextView content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.LoadingDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, null);
        img = view.findViewById(R.id.img);
        content = view.findViewById(R.id.content);

        String message = null;
        int rotateImageRes = R.drawable.ic_loading;
        Bundle arguments = getArguments();
        if (arguments != null) {
            message = arguments.getString(ARG_MESSAGE);
            int imageRes = arguments.getInt(ARG_ROTATE_IMAGE_RES);
            if (imageRes != 0) {
                rotateImageRes = imageRes;
            }
        }

        updateContentView(message);

        img.setImageResource(rotateImageRes);

        return view;
    }

    private void updateContentView(String message) {
        if (TextUtils.isEmpty(message)) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(message);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        img.startAnimation(animation);
    }

    @Override
    public void onStop() {
        img.clearAnimation();
        super.onStop();
    }

    public void updateMessage(String newMessage) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putString(ARG_MESSAGE, newMessage);
        updateContentView(newMessage);
    }

    public static class Builder {

        String message;
        int rotateImageRes = R.drawable.ic_loading;
        boolean cancelable = false;
        OnDismissListener onDismissListener;

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener<LoadingDialogFragment> onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setRotateImageRes(@DrawableRes int rotateImageRes) {
            this.rotateImageRes = rotateImageRes;
            return this;
        }

        public LoadingDialogFragment build() {
            LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
            Bundle args = new Bundle();
            if (message != null) {
                args.putString(ARG_MESSAGE, message);
            }
            args.putInt(ARG_ROTATE_IMAGE_RES, rotateImageRes);
            if (onDismissListener != null) {
                args.putParcelable(ARG_ON_DISMISS_LISTENER, loadingDialogFragment.obtainMessage(WHAT_ON_DISMISS_LISTENER, onDismissListener));
            }

            loadingDialogFragment.setArguments(args);

            loadingDialogFragment.setCancelable(cancelable);
            return loadingDialogFragment;
        }
    }

}

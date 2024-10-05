package com.pasc.lib.widget.dialog.categoryselection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;

@SuppressLint("AppCompatCustomView")
public class CategoryTextView extends TextView{

    private int mTextColor;
    private int mPressedTextColor;

    public CategoryTextView(Context context) {
        super(context);
        init();
    }

    public CategoryTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPressedTextColor = getResources().getColor(R.color.category_check_color);
        mTextColor = getResources().getColor(R.color.category_list_text_color);

        this.setOnTouchListener(new OnTouchListener() {
            boolean isContinue = true;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://0
                        isContinue =true;
                        setTextColor(mPressedTextColor);
                        break;
                    case MotionEvent.ACTION_UP://1
                        isContinue =false;
//                        setTextColor(mTextColor);
                        break;
                    case MotionEvent.ACTION_MOVE://2
                        isContinue =false;
//                       setTextColor(mTextColor);
                        break;
                    case MotionEvent.ACTION_CANCEL://2
                        isContinue =false;
//                        setTextColor(mTextColor);
                        break;
                }
               return false;
            }
        });

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}

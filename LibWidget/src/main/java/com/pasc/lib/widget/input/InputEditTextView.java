package com.pasc.lib.widget.input;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.catalog.CatalogSelect;

@SuppressLint("AppCompatCustomView")
public class InputEditTextView extends EditText {

    private final static String TAG = "EditTextWithDel";
    private Drawable imgDel;
    private Context mContext;
    private int mWidth,mHeight;
    private int mSumNumberValue = 50;
    private int mSumNumberTextColor = 0;
    private int mSumNumberTextSize = 0;

    private int mCurrentNumberValue = 0;
    private int mCurrentNumberTextColor = 0;
    private int mCurrentNumberTextSize = 0;
    private int mTextOffset = 5;

    private boolean mImgDelShow;

    private boolean mNumberShow;

    private Paint mPaint;
    private Rect mBounds;

    private OnDeleteListener onDeleteListener;


    public interface OnDeleteListener {

        void delete();

    }


    public void setOnDeleteListener(OnDeleteListener listener) {
        onDeleteListener = listener;
    }

    public InputEditTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public InputEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mBounds = new Rect();
        setCustomAttributes(mContext,attrs);
        init();
    }

    public InputEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void setCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.InputEditTextView);

        mSumNumberTextColor = getResources().getColor(R.color.pasc_explain_text);
        mSumNumberTextSize = 30;

        mCurrentNumberTextColor = getResources().getColor(R.color.pasc_explain_text);
        mCurrentNumberTextSize = 30;

        mImgDelShow =false;
        mNumberShow = false;

        mImgDelShow =array.getBoolean(R.styleable.InputEditTextView_imgDelShow, false);
        mNumberShow = array.getBoolean(R.styleable.InputEditTextView_numberShow, false);

        int sumValue = array.getInteger(R.styleable.InputEditTextView_sumNumberValue, 0);
        if(sumValue != 0){
            mSumNumberValue = sumValue;
        }
        int sumColor = array.getColor(R.styleable.InputEditTextView_sumNumberTextColor,0);
        if(sumColor != 0){
            mSumNumberTextColor = sumColor;
        }
        int sumSize = array.getDimensionPixelSize(
                (R.styleable.InputEditTextView_sumNumberTextSize),
                32);
        if(sumSize != 0){
            mSumNumberTextSize = sumSize;
        }

        int currentValue = array.getInteger(R.styleable.InputEditTextView_currentNumberValue, 0);
        if(currentValue != 0){
            mCurrentNumberValue = currentValue;
        }
        int currentColor = array.getColor(R.styleable.InputEditTextView_currentNumberTextColor,0);
        if(currentColor != 0){
            mCurrentNumberTextColor = currentColor;
        }
        int currentSize = array.getDimensionPixelSize(
                (R.styleable.InputEditTextView_currentNumberTextSize),
                32);
        if(currentSize != 0){
            mCurrentNumberTextSize = currentSize;
        }


        array.recycle();
    }

    private void init() {

        setBackgroundColor(getResources().getColor(R.color.white));
        imgDel = mContext.getResources().getDrawable(R.drawable.clear);

        setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSumNumberValue)});
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
                mCurrentNumberValue = s.toString().trim().length();
                invalidate();
            }
        });
        setDrawable();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             if(containsArea){
                 setText("");
             }
            }
        });
    }

    private void setDrawable() {

        if (getText().toString().trim().equals("")) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            if(mImgDelShow){
                setCompoundDrawablesWithIntrinsicBounds(null, null, imgDel, null);
            }

        }
    }

     boolean containsArea =false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right-100 ;
            containsArea = false;
            if(rect.contains(eventX, eventY))
                if(mImgDelShow){
                    if(onDeleteListener != null){
                        onDeleteListener.delete();
                    }
                    containsArea =true;
                }

        }

        return super.onTouchEvent(event);
    }



    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!mNumberShow){
            return;
        }

        mPaint.setColor(mSumNumberTextColor);
        mPaint.setTextSize(mSumNumberTextSize);
        canvas.drawText("/"+mSumNumberValue, getWidth()-DensityUtils.dp2px(30),
                getHeight()-(DensityUtils.dp2px(8)) , mPaint);

        mPaint.setColor(mCurrentNumberTextColor);
        mPaint.setTextSize(mCurrentNumberTextSize);

        String text = String.valueOf(mCurrentNumberValue);
        float textWidth = mPaint.measureText(text)/2+mTextOffset;
        if(text.length()>1){
            textWidth = textWidth -mTextOffset;
        }
        canvas.drawText(""+mCurrentNumberValue, getWidth()-DensityUtils.dp2px(30+textWidth),
                getHeight()-(DensityUtils.dp2px(8)) , mPaint);
    }
}


package com.pasc.lib.widget.feedback;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.R;

public class FeedbackView extends LinearLayout{
    private Context mContext;
    private ImageView mImg;

    private String mTitleText;
    private int mTitleSize;
    private int mTitleColor;

    private String mDescText;
    private int mDescSize;
    private int mDescColor;

    public FeedbackView(Context context) {
        this(context, null);
    }

    public FeedbackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.FeedbackViewStyle);
    }

    public FeedbackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FeedbackView);
        Drawable img = a.getDrawable(R.styleable.FeedbackView_img);

        mTitleText = a.getString(R.styleable.FeedbackView_titleText);
        mTitleSize = (int) a.getDimension(R.styleable.FeedbackView_titleTextSize, 0);
        mTitleColor = a.getColor(R.styleable.FeedbackView_titleTextColor,
                getResources().getColor(R.color.pasc_primary_text));

        mDescText = a.getString(R.styleable.FeedbackView_descText);
        mDescSize = (int) a.getDimension(R.styleable.FeedbackView_descTextSize, 0);
        mDescColor = a.getColor(R.styleable.FeedbackView_descTextColor,
                getResources().getColor(R.color.pasc_explain_text));

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.feedback_result, this, true);
        ImageView imageView  = view.findViewById(R.id.feedback_img);
        TextView title = view.findViewById(R.id.feedback_title);
        TextView desc = view.findViewById(R.id.feedback_desc);

        if(img != null){
            imageView.setImageDrawable(img);
        }
        if(mTitleText == null ||mTitleText.equals("")){
            title.setVisibility(GONE);
        }else {
            title.setVisibility(VISIBLE);
            title.setText(mTitleText);
        }


        if(mTitleSize == 0){
            title.setTextSize(17);
        }else {
            title.getPaint().setTextSize(mTitleSize);
        }
        title.setTextColor(mTitleColor);

        if(mDescText == null ||mDescText.equals("")){
            desc.setVisibility(GONE);
        }else {
            desc.setVisibility(VISIBLE);
            desc.setText(mDescText);
        }
        if(mDescSize == 0){
            desc.setTextSize(15);
        }else {
            desc.getPaint().setTextSize(mDescSize);
        }
        desc.setTextColor(mDescColor);


        a.recycle();
    }
}

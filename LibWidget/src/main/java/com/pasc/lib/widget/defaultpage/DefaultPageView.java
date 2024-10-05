package com.pasc.lib.widget.defaultpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.ICallBack;
import com.pasc.lib.widget.R;

public class DefaultPageView extends LinearLayout{
    private String primaryButtonText;
    private Drawable primaryButtonBackground;
    private String secondaryButtonText;
    private Drawable secondaryButtonBackground;
    private String mDesc;
    private int defaultLayout = -1;
    private Button primaryButton;
    private Button secondaryButton;
    private ImageView imageView;
    private TextView desc;


    public DefaultPageView(Context context) {
        this(context, null);
    }

    public DefaultPageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.DefaultPageViewStyle);
    }

    public DefaultPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DefaultPageView);
        Drawable img = a.getDrawable(R.styleable.DefaultPageView_img);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.default_page_layout, this, true);
        imageView  = view.findViewById(R.id.default_page_img);
        desc = view.findViewById(R.id.default_page_desc);
        primaryButton = view.findViewById(R.id.primary_button);
        secondaryButton = view.findViewById(R.id.secondary_button);

        defaultLayout = a.getInt(R.styleable.DefaultPageView_default_layout, -1);

        if (-1 != defaultLayout) {

            if(defaultLayout == 0){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_data));
                desc.setText(getResources().getString(R.string.default_no_data));
                primaryButton.setVisibility(GONE);
                secondaryButton.setVisibility(GONE);


            }else if(defaultLayout == 1){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_address));
                desc.setText(getResources().getString(R.string.default_no_address));
                primaryButton.setVisibility(VISIBLE);
                primaryButton.setText(getResources().getString(R.string.default_add_address));
                primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_primary_button));
                primaryButton.setTextColor(getResources().getColor(R.color.white));
                secondaryButton.setVisibility(GONE);

            } else if(defaultLayout == 2){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_message));
                desc.setText(getResources().getString(R.string.default_no_message));
                primaryButton.setVisibility(GONE);
                secondaryButton.setVisibility(GONE);

            }
            else if(defaultLayout == 3){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_network));
                desc.setText(getResources().getString(R.string.default_no_network));
                primaryButton.setVisibility(VISIBLE);
                primaryButton.setText(getResources().getString(R.string.default_reload));
                primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
                primaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                secondaryButton.setVisibility(GONE);

            }
            else if(defaultLayout == 4){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_search_result));
                desc.setText(getResources().getString(R.string.default_no_search_result));
                primaryButton.setVisibility(GONE);
                secondaryButton.setVisibility(GONE);

            }
            else if(defaultLayout == 5){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_server_error));
                desc.setText(getResources().getString(R.string.default_server_error));
                primaryButton.setVisibility(VISIBLE);
                primaryButton.setText(getResources().getString(R.string.default_back_homepage));
                primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_primary_button));
                primaryButton.setTextColor(getResources().getColor(R.color.white));
                secondaryButton.setVisibility(VISIBLE);
                secondaryButton.setText(getResources().getString(R.string.default_reload));
                secondaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
                secondaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));

            }
            else if(defaultLayout == 6){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_server_maintain));
                desc.setText(getResources().getString(R.string.default_server_maintain));
                primaryButton.setVisibility(VISIBLE);
                primaryButton.setText(getResources().getString(R.string.default_back_homepage));
                primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
                primaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                secondaryButton.setVisibility(GONE);

            }
        }
        mDesc = a.getString(R.styleable.DefaultPageView_desc);
        primaryButtonText = a.getString(R.styleable.DefaultPageView_primary_button_text);
        primaryButtonBackground = a.getDrawable(R.styleable.DefaultPageView_primary_button_background);
        int primaryButtonTextColor = a.getColor(R.styleable.DefaultPageView_primary_button_text_color,
                -1);
        secondaryButtonText = a.getString(R.styleable.DefaultPageView_secondary_button_text);
        secondaryButtonBackground = a.getDrawable(R.styleable.DefaultPageView_secondary_button_background);
        int secondaryButtonTextColor = a.getColor(R.styleable.DefaultPageView_secondary_button_text_color,
                -1);
        if(img != null){
            imageView.setImageDrawable(img);
        }
        if(mDesc != null){
            desc.setText(mDesc);
        }
        if(primaryButtonText != null){
            primaryButton.setText(primaryButtonText);
        }
        if(primaryButtonBackground != null){
            primaryButton.setBackground(primaryButtonBackground);
        }
        if(primaryButtonTextColor != -1){
            primaryButton.setTextColor(primaryButtonTextColor);
        }

        if(secondaryButtonText != null){
            secondaryButton.setText(secondaryButtonText);
        }

        if(secondaryButtonBackground != null){
            secondaryButton.setBackground(secondaryButtonBackground);
        }
        if(secondaryButtonTextColor != -1){
            secondaryButton.setTextColor(secondaryButtonTextColor);
        }

        a.recycle();
    }

    public void setPrimaryOnClickListener(final ICallBack callBack){

        primaryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callBack != null){
                    callBack.callBack();
                }
            }
        });
    }

    public void setSecondaryOnClickListener(final ICallBack callBack){

        secondaryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callBack != null){
                    callBack.callBack();
                }
            }
        });
    }

    public void setImg(int img){
        imageView.setImageResource(img);

    }
    public void setDesc(CharSequence text){
        desc.setText(text);
    }

    public void setPrimaryButtonText(CharSequence primaryButtonText){
        primaryButton.setText(primaryButtonText);
    }

    public void setPrimaryButtonBackground(Drawable background){
        primaryButton.setBackground(background);

    }
    public void setPrimaryButtonVisibility(boolean isVisibility){
        if(isVisibility){
            primaryButton.setVisibility(VISIBLE);
        }else {
            primaryButton.setVisibility(GONE);
        }


    }
    public void setSecondaryButtonText(CharSequence secondaryButtonText){
        secondaryButton.setText(secondaryButtonText);
    }

    public void setSecondaryButtonBackground(Drawable background){
        secondaryButton.setBackground(background);
    }
    public void setSecondaryButtonVisibility(boolean isVisibility){

        if(isVisibility){
            primaryButton.setVisibility(VISIBLE);
        }else {
            primaryButton.setVisibility(GONE);
        }

    }

    public void setDefaultLayout(DefaultLayoutType type){
        setLayout(type);

    }

    private void setLayout(DefaultLayoutType type){
        if(type == DefaultLayoutType.NO_DATA){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_data));
            desc.setText(getResources().getString(R.string.default_no_data));
            primaryButton.setVisibility(GONE);
            secondaryButton.setVisibility(GONE);

        }else if(type == DefaultLayoutType.NO_ADDRESS){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_address));
            desc.setText(getResources().getString(R.string.default_no_address));
            primaryButton.setVisibility(VISIBLE);
            primaryButton.setText(getResources().getString(R.string.default_add_address));
            primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_primary_button));
            primaryButton.setTextColor(getResources().getColor(R.color.white));
            secondaryButton.setVisibility(GONE);

        }else if(type == DefaultLayoutType.NO_MESSAGE){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_message));
            desc.setText(getResources().getString(R.string.default_no_message));
            primaryButton.setVisibility(GONE);
            secondaryButton.setVisibility(GONE);

        } else if(type == DefaultLayoutType.NO_NETWORK){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_network));
            desc.setText(getResources().getString(R.string.default_no_network));
            primaryButton.setVisibility(VISIBLE);
            primaryButton.setText(getResources().getString(R.string.default_reload));
            primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
            primaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));
            secondaryButton.setVisibility(GONE);

        } else if(type == DefaultLayoutType.NO_SEARCH_RESULT){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_no_search_result));
            desc.setText(getResources().getString(R.string.default_no_search_result));
            primaryButton.setVisibility(GONE);
            secondaryButton.setVisibility(GONE);

        } else if(type == DefaultLayoutType.SERVER_ERROR){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_server_error));
            desc.setText(getResources().getString(R.string.default_server_error));
            primaryButton.setVisibility(VISIBLE);
            primaryButton.setText(getResources().getString(R.string.default_back_homepage));
            primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_primary_button));
            primaryButton.setTextColor(getResources().getColor(R.color.white));
            secondaryButton.setVisibility(VISIBLE);
            secondaryButton.setText(getResources().getString(R.string.default_reload));
            secondaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
            secondaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));

        } else if(type == DefaultLayoutType.SERVER_MAINTAIN){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_server_maintain));
            desc.setText(getResources().getString(R.string.default_server_maintain));
            primaryButton.setVisibility(VISIBLE);
            primaryButton.setText(getResources().getString(R.string.default_back_homepage));
            primaryButton.setBackground(getResources().getDrawable(R.drawable.selector_secondary_button));
            primaryButton.setTextColor(getResources().getColor(R.color.pasc_primary_text));
            secondaryButton.setVisibility(GONE);
        }

    }

}

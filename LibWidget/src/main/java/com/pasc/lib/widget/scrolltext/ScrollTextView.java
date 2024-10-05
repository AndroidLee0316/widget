package com.pasc.lib.widget.scrolltext;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

public class ScrollTextView extends AppCompatTextView {
    public ScrollTextView(Context context) {
        super(context);
        initView();
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setSingleLine(true);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}

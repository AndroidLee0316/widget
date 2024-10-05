package com.pasc.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
@Deprecated
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MarqueeTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}


package com.pasc.lib.widget.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * 继承自 {@link PascPopup}，在 {@link PascPopup} 的基础上，支持显示一个列表。
 * Created by chendaixi947 on 2019/4/24
 *
 * @version 1.0
 */

public class PascListPopup extends PascPopup {
    private ListView mListView;

    /**
     * 构造方法。
     *
     * @param context 传入一个 Context。
     */
    private PascListPopup(Context context) {
        super(context);
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void build() {
        int height = mListMaxHeight;
        if (mListMaxHeight == 0) {
            height = FrameLayout.LayoutParams.WRAP_CONTENT;
        }
        mListView = new WrapContentListView(mContext, height);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(mListWidth, height);
        mListView.setLayoutParams(lp);
        mListView.setAdapter(mAdapter);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setDivider(null);
        setContentView(mListView);
    }

    public static class ListPopupBuilder extends PopupBuilder {
        private PascListPopup mListPopup;

        public ListPopupBuilder(Context context) {
            super(context);
        }

        @Override
        protected PascPopup createPopup(Context context) {
            mListPopup = new PascListPopup(context);
            return mListPopup;
        }

        @Override
        public PascListPopup create() {
            mListPopup.build();
            return mListPopup;
        }
    }

    /**
     * 支持高度值为 wrap_content 的 {@link ListView}，解决原生 {@link ListView} 在设置高度为 wrap_content 时高度计算错误的 bug。
     */
    private static class WrapContentListView extends ListView {
        private int mMaxHeight = Integer.MAX_VALUE >> 2;

        public WrapContentListView(Context context) {
            super(context);
        }

        public WrapContentListView(Context context, int maxHeight) {
            super(context);
            mMaxHeight = maxHeight;
        }

        public WrapContentListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public WrapContentListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setMaxHeight(int maxHeight) {
            if (mMaxHeight != maxHeight) {
                mMaxHeight = maxHeight;
                requestLayout();
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(mMaxHeight,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }
    }
}

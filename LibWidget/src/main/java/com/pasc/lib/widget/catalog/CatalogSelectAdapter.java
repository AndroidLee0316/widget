package com.pasc.lib.widget.catalog;

import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;

import java.util.List;

/**
 * Created by chendaixi947 on 2019/4/24
 *
 * @since 1.0
 */
public class CatalogSelectAdapter extends BaseQuickAdapter<CharSequence, BaseViewHolder> {
    private Catalog mCatalog;
    private int mLastSelectPosition = 0;
    private int mCurrentSelectPosition = 0;
    private boolean mIsNeedChangeItemBg;

    CatalogSelectAdapter(@Nullable List<CharSequence> data, boolean isNeedChangeItemBg, Catalog catalog) {
        super(R.layout.pasc_item_catalog, data);
        mIsNeedChangeItemBg = isNeedChangeItemBg;
        mCatalog = catalog;
    }

    public void setCurrentSelectPosition(int mCurrentSelectPosition) {
        this.mCurrentSelectPosition = mCurrentSelectPosition;
        this.mLastSelectPosition = mCurrentSelectPosition;
    }

    public int getCurrentSelectPosition() {
        return mCurrentSelectPosition;
    }

    public void setSelect(int selectPosition) {
        this.mCurrentSelectPosition = selectPosition;
        notifyItemChanged(mCurrentSelectPosition);
        if (mCurrentSelectPosition != mLastSelectPosition) {
            notifyItemChanged(mLastSelectPosition);
            mLastSelectPosition = mCurrentSelectPosition;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CharSequence item) {
        setItemParams(helper);
        TextView textView = helper.getView(R.id.textView);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCatalog.getTextSize());
        textView.setText(item);

        int position = helper.getAdapterPosition();
        if (position == mCurrentSelectPosition) {
            helper.itemView.setBackgroundColor(mCatalog.getSelectedBgColor());
            textView.setTextColor(mCatalog.getSelectedTextColor());
        } else {
            helper.itemView.setBackgroundColor(mCatalog.getNormalBgColor());
            textView.setTextColor(mCatalog.getNormalTextColor());
        }
        if (!mIsNeedChangeItemBg) {
            helper.itemView.setBackgroundColor(mCatalog.getSelectedBgColor());
        } else {
            if (mLastSelectPosition != mCurrentSelectPosition && position == mLastSelectPosition) {
                helper.itemView.setBackgroundColor(mCatalog.getNormalBgColor());
            }
        }
    }

    private void setItemParams(BaseViewHolder helper) {
        ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
        layoutParams.height = mCatalog.getItemHeight();
    }
}

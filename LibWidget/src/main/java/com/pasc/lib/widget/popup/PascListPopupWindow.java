package com.pasc.lib.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;

import java.util.List;

public class PascListPopupWindow extends PopupWindow {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    public PascListPopupWindow(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PascListPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PascListPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PascListPopupWindow(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.window_pasc_list_popup, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        setContentView(view);
    }

    public void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
        adapter.bindToRecyclerView(recyclerView);
    }

    public static class Builder {

        Context context; // 上下文
        List<CharSequence> items; // 列表项
        OnSingleChoiceListener<PascListPopupWindow> onSingleChoiceListener; // 单选监听

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setItems(List<CharSequence> items) {
            this.items = items;
            return this;
        }

        public Builder setOnSingleChoiceListener(OnSingleChoiceListener<PascListPopupWindow> onSingleChoiceListener) {
            this.onSingleChoiceListener = onSingleChoiceListener;
            return this;
        }

        public PascListPopupWindow build() {
            final PascListPopupWindow pascListPopupWindow = new PascListPopupWindow(context);

            pascListPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_popup_choice));
            pascListPopupWindow.setFocusable(true);
            pascListPopupWindow.setTouchable(true);
            pascListPopupWindow.setOutsideTouchable(true);

            ItemAdapter adapter = new ItemAdapter(items);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (onSingleChoiceListener != null) {
                        onSingleChoiceListener.onSingleChoice(pascListPopupWindow, position);
                    }
                }
            });
            pascListPopupWindow.setAdapter(adapter);
            int itemHeightDp = 45;
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int itemHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeightDp, displayMetrics);
            int windowWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemHeightDp * 3, displayMetrics);
            int windowHeight = (int) (itemHeight * Math.min(6.5, items.size()));
            pascListPopupWindow.setWidth(windowWidth);
            pascListPopupWindow.setHeight(windowHeight);

            return pascListPopupWindow;
        }
    }

    public static class ItemAdapter extends BaseQuickAdapter<CharSequence, BaseViewHolder> {

        public ItemAdapter(@Nullable List<CharSequence> data) {
            super(R.layout.item_popup_choice, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CharSequence item) {
            helper.setText(R.id.text, item);
            helper.setVisible(R.id.divider, helper.getLayoutPosition() < (getItemCount() - 1));
        }
    }
}

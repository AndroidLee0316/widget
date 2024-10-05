package com.pasc.lib.widget.dialog.iconaction;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 图标操作对话框.
 * Created by chenruihan410 on 2018/10/11.
 */
public class IconActionDialogFragment extends BaseDialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CLOSE_TEXT = "closeText";
    private static final String ARG_ITEMS = "items";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.IconActionDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_icon_action, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<IconActionItem> items = null;
        final Bundle arguments = getArguments();
        if (arguments != null) {
            items = (ArrayList) arguments.getSerializable(ARG_ITEMS);
        }
        if (items == null || items.size() == 0) {
            items = new ArrayList<>();
            items.add(new IconActionItem(R.drawable.ic_wechat, "微信好友"));
            items.add(new IconActionItem(R.drawable.ic_wechat_friend_circle, "微信朋友圈"));
            items.add(new IconActionItem(R.drawable.ic_qq, "QQ好友"));
            items.add(new IconActionItem(R.drawable.ic_qq_zone, "QQ空间"));
        }

        IconActionAdapter iconActionAdapter = new IconActionAdapter(items);
        iconActionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                sendMessageSingleChoice(position);
            }
        });

        iconActionAdapter.bindToRecyclerView(recyclerView);

        // 标题
        TextView titleTextView = view.findViewById(R.id.title);
        CharSequence title = "分享到";
        if (arguments != null) {
            CharSequence argTitle = arguments.getCharSequence(ARG_TITLE);
            if (!TextUtils.isEmpty(argTitle)) {
                title = argTitle;
            }
        }
        titleTextView.setText(title);

        // 关闭按钮
        Button closeButton = view.findViewById(R.id.close);
        CharSequence closeText = "取消";
        if (arguments != null) {
            CharSequence argCloseText = arguments.getCharSequence(ARG_CLOSE_TEXT);
            if (!TextUtils.isEmpty(argCloseText)) {
                closeText = argCloseText;
            }
        }
        closeButton.setText(closeText);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendMessage(ARG_ON_CLOSE_LISTENER)) {
                    dismiss();
                }
            }
        });
    }

    class IconActionAdapter extends BaseQuickAdapter<IconActionItem, BaseViewHolder> {

        public IconActionAdapter(@Nullable List<IconActionItem> data) {
            super(R.layout.item_icon_action, data);
        }

        int getItemWidth() {
            int itemWidth;
            int itemCount = getItemCount();
            int minWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8 + 50 + 8, getResources().getDisplayMetrics());
            if (itemCount > 0) {
                int widthPixels = getResources().getDisplayMetrics().widthPixels;
                if (itemCount <= 4) {
                    itemWidth = Math.max(minWidth, (int) ((float) widthPixels / (float) itemCount));
                } else {
                    itemWidth = Math.max(minWidth, (int) ((float) widthPixels / 9.0 * 2.0));
                }
            } else {
                itemWidth = minWidth;
            }
            return itemWidth;
        }

        @Override
        protected void convert(BaseViewHolder helper, IconActionItem item) {
            helper.itemView.getLayoutParams().width = getItemWidth();
            helper.setImageResource(R.id.icon, item.iconRes);
            helper.setText(R.id.desc, item.iconDesc);
        }
    }

    public static class IconActionItem {
        int iconRes;
        CharSequence iconDesc;

        public IconActionItem(int iconRes, CharSequence iconDesc) {
            this.iconRes = iconRes;
            this.iconDesc = iconDesc;
        }
    }

    public static class Builder {

        ArrayList<IconActionItem> items;
        CharSequence title; // 标题
        CharSequence closeText; // 关闭文本
        OnCloseListener<IconActionDialogFragment> onCloseListener; // 关闭监听器
        OnSingleChoiceListener<IconActionDialogFragment> onSingleChoiceListener; // 单选监听器

        /**
         * 添加操作项.
         *
         * @param iconRes  图标资源.
         * @param iconDesc 图标描述.
         * @return 构建器.
         */
        public Builder addItem(int iconRes, CharSequence iconDesc) {
            if (items == null) {
                items = new ArrayList<IconActionItem>();
            }
            items.add(new IconActionItem(iconRes, iconDesc));
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setCloseText(CharSequence closeText) {
            this.closeText = closeText;
            return this;
        }

        public Builder setOnCloseListener(OnCloseListener<IconActionDialogFragment> onCloseListener) {
            this.onCloseListener = onCloseListener;
            return this;
        }

        public Builder setOnSingleChoiceListener(OnSingleChoiceListener<IconActionDialogFragment> onSingleChoiceListener) {
            this.onSingleChoiceListener = onSingleChoiceListener;
            return this;
        }

        public IconActionDialogFragment build() {
            IconActionDialogFragment dialogFragment = new IconActionDialogFragment();

            Bundle args = new Bundle();
            if (items != null) {
                args.putSerializable(ARG_ITEMS, items);
            }
            if (!TextUtils.isEmpty(title)) {
                args.putCharSequence(ARG_TITLE, title);
            }
            if (!TextUtils.isEmpty(closeText)) {
                args.putCharSequence(ARG_CLOSE_TEXT, closeText);
            }
            if (onSingleChoiceListener != null) {
                args.putParcelable(ARG_ON_SINGLE_CHOICE_LISTENER, dialogFragment.obtainMessage(WHAT_ON_SINGLE_CHOICE_LISTENER, onSingleChoiceListener));
            }
            if (onCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, onCloseListener));
            }

            dialogFragment.setArguments(args);
            dialogFragment.setCancelable(true);
            return dialogFragment;
        }
    }
}

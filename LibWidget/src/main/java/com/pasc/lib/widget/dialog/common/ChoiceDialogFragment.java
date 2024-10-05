package com.pasc.lib.widget.dialog.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChoiceDialogFragment extends BaseDialogFragment {
    private ChoiceController mController;
    private int mPosition = -1;
    private int mBeforePosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.InsetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pasc_widget_dialog_listview1, container, false);
        TextView confirmTv = rootView.findViewById(R.id.widget_confirm_tv);
        RecyclerView recyclerView = rootView.findViewById(R.id.widget_recyclerView);
        TextView choiceTitleTv = rootView.findViewById(R.id.widget_choice_title);
        if (savedInstanceState != null) {
            mController = (ChoiceController) savedInstanceState.getSerializable(KEY_SAVE);
        }
        if (mController == null) {
            return rootView;
        }

        if (mController.getTitle() != null) {
            choiceTitleTv.setText(mController.getTitle());
        }
        if (mController.getTitleColor() != 0) {
            choiceTitleTv.setTextColor(mController.getTitleColor());
        }
        if (mController.getTitleSize() != 0) {
            choiceTitleTv.setTextSize(mController.getTitleSize());
        }

        if (mController.getButtonText() != null) {
            confirmTv.setText(mController.getButtonText());
        }
        if (mController.getButtonColor() != 0) {
            confirmTv.setTextColor(mController.getButtonColor());
        }
        if (mController.getButtonSize() != 0) {
            confirmTv.setTextSize(mController.getButtonSize());
        }
        setAdapter(recyclerView);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController.getOnSingleChoiceListener() != null) {
                    mController.getOnSingleChoiceListener().onSingleChoice(ChoiceDialogFragment.this, mPosition);
                }
            }
        });
        return rootView;
    }

    private void setAdapter(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
        if (mController.getItems().size() > 5) {
            lp.height = DensityUtils.dp2px(230);
        }
        recyclerView.setLayoutParams(lp);
        final ChoiceListAdapter choiceListAdapter = new ChoiceListAdapter(mController.getItems());
        choiceListAdapter.bindToRecyclerView(recyclerView);
        mPosition = mController.getCurrentSelectPosition();
        choiceListAdapter.setSingleSelection(mBeforePosition, mPosition);
        mBeforePosition = mPosition;

        choiceListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                choiceListAdapter.setSingleSelection(mBeforePosition, position);
                mBeforePosition = position;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(mController.isCancelable());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public ChoiceDialogFragment() {
        mController = new ChoiceController();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SAVE, mController);
    }

    class ChoiceListAdapter extends BaseQuickAdapter<CharSequence, BaseViewHolder> {
        private int selectedPosition = -1;
        private int beforePosition = -1;
        private Map<Integer, Boolean> map = new HashMap<>();

        //初始化map集合,默认为不选中
        private void initMap(List<CharSequence> data) {
            for (int i = 0; i < data.size(); i++) {
                map.put(i, false);
            }
        }

        private ChoiceListAdapter(@Nullable List<CharSequence> data) {
            super(R.layout.dialog_item, data);
            if (mController.ismIsMultiChoice()) {
                initMap(data);
            }
        }

        @Override
        protected void convert(BaseViewHolder helper, CharSequence item) {
            TextView titleView = helper.getView(R.id.item_title);
            CheckBox checkSingle = helper.getView(R.id.check_single);
            View topLine = helper.getView(R.id.top_line);
            final CheckBox checkmulti = helper.getView(R.id.check_multi);
            titleView.setText(item);
            if (helper.getLayoutPosition() == 0) {
                topLine.setVisibility(View.VISIBLE);
            } else {
                topLine.setVisibility(View.GONE);
            }
            if (mController.getItemTextColor() != 0) {
                titleView.setTextColor(mController.getItemTextColor());

            }
            if (mController.getItemTextSize() != 0) {
                titleView.setTextSize(mController.getItemTextSize());

            }

            if (mController.ismIsSingleChoice()) {
                checkSingle.setVisibility(View.VISIBLE);
                checkmulti.setVisibility(View.GONE);
                if (selectedPosition == helper.getLayoutPosition()) {
                    checkSingle.setChecked(true);
                    helper.itemView.setSelected(true);
                } else {
                    checkSingle.setChecked(false);
                    helper.itemView.setSelected(false);
                }
                if (beforePosition == helper.getLayoutPosition()) {
                    checkSingle.setChecked(false);
                    helper.itemView.setSelected(false);
                }
            } else if (mController.ismIsMultiChoice()) {
                checkSingle.setVisibility(View.GONE);
                checkmulti.setVisibility(View.VISIBLE);
                final int curPosition = helper.getLayoutPosition();
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkmulti.isChecked()) {
                            checkmulti.setChecked(false);
                        } else {
                            checkmulti.setChecked(true);
                        }
                        map.put(curPosition, checkmulti.isChecked());
                    }
                });

                if (map.get(curPosition) == null) {
                    map.put(curPosition, false);
                }
                checkmulti.setChecked(map.get(curPosition));
            }
        }

        public void setSingleSelection(int beforePostion, int selectedPosition) {
            this.selectedPosition = selectedPosition;
            notifyItemChanged(this.selectedPosition);
            if (beforePostion != selectedPosition) {
                this.beforePosition = beforePostion;
                notifyItemChanged(beforePostion);
            }
        }
    }


    public static class Builder {
        private final ChoiceController.ControllerParams controllerParams;

        public Builder() {
            controllerParams = new ChoiceController.ControllerParams();
        }

        /**
         * 设置是否允许需求弹窗
         *
         * @param isCancelable true:可取消，false:不能取消
         */
        public Builder setCancelable(boolean isCancelable) {
            controllerParams.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置弹窗标题
         *
         * @param title 标题
         */
        public Builder setTitle(CharSequence title) {
            controllerParams.title = title;
            return this;
        }

        /**
         * 设置标题文字大小
         *
         * @param size 文字大小
         */
        public Builder setTitleSize(int size) {
            controllerParams.titleSize = size;
            return this;
        }

        /**
         * 设置标题颜色
         *
         * @param color 标题颜色
         */
        public Builder setTitleColor(int color) {
            controllerParams.titleColor = color;
            return this;
        }

        /**
         * 设置选择栏文字大小
         *
         * @param size 文字大小
         */
        public Builder setItemTextSize(int size) {
            controllerParams.itemTextSize = size;
            return this;
        }

        /**
         * 设置选择栏文字颜色
         *
         * @param color 文字颜色
         */
        public Builder setItemTextColor(int color) {
            controllerParams.itemTextColor = color;
            return this;
        }

        /**
         * 设置按钮文字
         *
         * @param text 文字
         */
        public Builder setButtonText(CharSequence text) {
            controllerParams.buttonText = text;
            return this;
        }

        /**
         * 设置按钮文字颜色
         *
         * @param color 文字颜色
         */
        public Builder setButtonColor(int color) {
            controllerParams.buttonColor = color;
            return this;
        }

        /**
         * 设置按钮文字大小
         *
         * @param size 文字大小
         */
        public Builder setButtonSize(int size) {
            controllerParams.buttonSize = size;
            return this;
        }

        /**
         * 设置选择栏目回调监听
         *
         * @param onSingleChoiceListener 监听
         */
        public Builder setOnSingleChoiceListener(OnSingleChoiceListener<ChoiceDialogFragment> onSingleChoiceListener) {
            controllerParams.mIsSingleChoice = true;
            controllerParams.onSingleChoiceListener = onSingleChoiceListener;
            return this;
        }

        /**
         * 设置栏目数据源
         *
         * @param items                 栏目数据list
         * @param currentSelectPosition 默认选择position
         */
        public Builder setSingletItems(ArrayList<CharSequence> items, int currentSelectPosition) {
            controllerParams.items = items;
            controllerParams.currentSelectPosition = currentSelectPosition;
            return this;
        }

        public ChoiceDialogFragment build() {
            ChoiceDialogFragment choiceDialogFragment = new ChoiceDialogFragment();
            controllerParams.apply(choiceDialogFragment.mController);
            return choiceDialogFragment;
        }

        public ChoiceDialogFragment show(FragmentManager manager, String tag) {
            ChoiceDialogFragment customDialog = build();
            customDialog.show(manager, tag);
            return customDialog;
        }

    }

}

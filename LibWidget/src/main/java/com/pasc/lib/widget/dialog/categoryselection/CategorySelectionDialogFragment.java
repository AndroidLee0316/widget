package com.pasc.lib.widget.dialog.categoryselection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnCategoryItemSelectListener;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnSelectedCategoryClickListener;
import com.pasc.lib.widget.toast.Toasty;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类选择对话框.
 */
public class CategorySelectionDialogFragment extends BaseDialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CLOSE_TEXT = "closeText";
    private static final String ARG_CONFIRM_TEXT = "confirmText";
    private static final String ARG_ITEMS = "items";
    private static final String ARG_SELECTED_ITEMS = "selectedItems";

    private ICategoryItem iCategoryItem;
    private ArrayList<ICategoryItem> items;
    private ArrayList<CharSequence> selectedItems;
    private CategoryAdapter categoryAdapter;
    private int curLevel = 0;
    private LinearLayout mTopCategoryLin;
    private ImageView mTopCategoryImg;
    private TextView mTopCategoryText;
    private View mLine1;

    private LinearLayout mSecondCategoryLin;
    private ImageView mSecondCategoryImg;
    private TextView mSecondCategoryText;
    private View mLine2;

    private LinearLayout mTertiaryCategoryLin;
    private ImageView mTertiaryCategoryImg;
    private TextView mTertiaryCategoryText;
    private boolean isSelectTopCategory = false;
    private boolean isSelectSecondCategory = false;
    private boolean isSelectTertiaryCategory = false;
    private String categoryToast;


    private int currentSelectedPosition = -1; // 当前选中的已选项的位置

    private List<ICategoryItem> itemsSelected = new ArrayList<ICategoryItem>(); // 已选中的对象

    private String CategoryText1,CategoryText2,CategoryText3;

    public static interface ICategoryItem {
        CharSequence getCategoryName();
    }

    public String getCategory1(){
        return mTopCategoryText.getText().toString().trim();
    }
    public String getCategory2(){
        return mSecondCategoryText.getText().toString().trim();
    }
    public String getCategory3(){
        return mTertiaryCategoryText.getText().toString().trim();
    }

    public int getCurLevel() {
        return curLevel;
    }

    public int setCurLevel(int mCurLevel) {
        return mCurLevel;
    }

    public void setCategoryToast(String toast){
        categoryToast = toast;
    }

    public void setOnCategoryItemSelectListener(ICategoryItem mICategoryItem){
        this.iCategoryItem = mICategoryItem;
    }

    public <T extends ICategoryItem> void setCategoryList(ArrayList<T> items) {
        this.items = (ArrayList<ICategoryItem>) items;
        if(categoryAdapter != null){
            initCategoryTextValues();
            categoryAdapter.setNewData((ArrayList<ICategoryItem>) items);
        }
    }

    public ArrayList<CharSequence> getSelectedCategoryItems(){
        ArrayList<CharSequence> items = new ArrayList<>();
        if(isSelectTopCategory){
            items.add(mTopCategoryText.getText().toString().trim());
        }
        if(isSelectSecondCategory){
            items.add(mSecondCategoryText.getText().toString().trim());
        }
        if(isSelectTertiaryCategory){
            items.add(mTertiaryCategoryText.getText().toString().trim());
        }
        return items;
    }

    private void setSelectedItems(ArrayList<CharSequence> items){
        if(items == null){
            return;
        }
        for (int i=0;i<items.size();i++){
            if(i == 0 ){
                curLevel =0;
                mTopCategoryText.setText(items.get(i));
                mTopCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
                mTopCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
                mLine1.setVisibility(View.VISIBLE);
                isSelectTopCategory = true;
                CategoryText1 = mTopCategoryText.getText().toString();
            }
            if(i == 1){
                curLevel =1;
                mSecondCategoryText.setText(items.get(i));
                mSecondCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
                mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
                mLine2.setVisibility(View.VISIBLE);
                isSelectSecondCategory = true;
                CategoryText2 = mSecondCategoryText.getText().toString();
            }
            if(i == 2){
                curLevel =2;
                mTertiaryCategoryText.setText(items.get(i));
                mTertiaryCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
                mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
                isSelectTertiaryCategory = true;
                CategoryText3 = mTertiaryCategoryText.getText().toString();
                curLevel = 3;
            }


        }

    }

    private void initCategoryTextValues(){
        CategoryText1 = mTopCategoryText.getText().toString();
        CategoryText2 = mSecondCategoryText.getText().toString();
        CategoryText3 = mTertiaryCategoryText.getText().toString();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.BottomChoiceDialog);
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
        View view = inflater.inflate(R.layout.dialog_category_selection, null);
        return view;
    }

    private void setCategoryView(int position){
        if(curLevel == 0){
            isSelectTopCategory =true;
            isSelectSecondCategory = false;
            isSelectTertiaryCategory = false;
            mTopCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
            mTopCategoryText.setText(items.get(position).getCategoryName());
            mTopCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mLine1.setVisibility(View.VISIBLE);
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_check_color));
            mSecondCategoryText.setText(getResources().getString(R.string.default_category_selection_second_text));
            mTertiaryCategoryText.setText(getResources().getString(R.string.default_category_selection_tertiary_text));
            mLine2.setVisibility(View.INVISIBLE);
            mSecondCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.hollow_circle_blue_point));
            mTertiaryCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.hollow_circle_point));
            curLevel =1;
            CategoryText1 = mTopCategoryText.getText().toString();
        }else if(curLevel == 1){
            isSelectSecondCategory = true;
            isSelectTertiaryCategory = false;
            mTopCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mSecondCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
            mSecondCategoryText.setText(items.get(position).getCategoryName());
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mLine2.setVisibility(View.VISIBLE);
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_check_color));
            mTertiaryCategoryText.setText(getResources().getString(R.string.default_category_selection_tertiary_text));
            mTertiaryCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.hollow_circle_blue_point));
            curLevel =2;
            CategoryText2 = mSecondCategoryText.getText().toString();

        }else if (curLevel == 2){
            isSelectTertiaryCategory = true;
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mTertiaryCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
            mTertiaryCategoryText.setText(items.get(position).getCategoryName());
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            categoryAdapter.setSelection(position);
            CategoryText3 = mTertiaryCategoryText.getText().toString();
            curLevel =3;

        }else if(curLevel == 3){
            isSelectTertiaryCategory = true;
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mTertiaryCategoryImg.setImageDrawable(getResources().getDrawable(R.drawable.solid_circle_point));
            mTertiaryCategoryText.setText(items.get(position).getCategoryName());
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            categoryAdapter.setSelection(position);
            CategoryText3 = mTertiaryCategoryText.getText().toString();
        }
    }

    private void setCategoryTextColor(int type){
        if(type == 0){
            mTopCategoryText.setTextColor(getResources().getColor(R.color.category_check_color));
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));

        }else if(type == 1){
            mTopCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_check_color));
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));

        }if(type == 2){
            mTopCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mSecondCategoryText.setTextColor(getResources().getColor(R.color.category_list_text_color));
            mTertiaryCategoryText.setTextColor(getResources().getColor(R.color.category_check_color));

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mTopCategoryLin = view.findViewById(R.id.top_category_lin);
        mTopCategoryImg = view.findViewById(R.id.top_category_img);
        mTopCategoryText = view.findViewById(R.id.top_category_text);
        mLine1 =view.findViewById(R.id.line1);

        mSecondCategoryLin = view.findViewById(R.id.second_category_lin);
        mSecondCategoryImg = view.findViewById(R.id.second_category_img);
        mSecondCategoryText = view.findViewById(R.id.second_category_text);
        mLine2 =view.findViewById(R.id.line2);


        mTertiaryCategoryLin = view.findViewById(R.id.tertiary_category_lin);
        mTertiaryCategoryImg = view.findViewById(R.id.tertiary_category_img);
        mTertiaryCategoryText = view.findViewById(R.id.tertiary_category_text);
        categoryToast = getResources().getString(R.string.category_toast);
        mTopCategoryLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curLevel<0 || !isSelectTopCategory ){
                    return;
                }
                curLevel =0;
                setCategoryTextColor(0);
                CategoryText1 = mTopCategoryText.getText().toString();
                sendSelectedCategory(0);
            }
        });
        mSecondCategoryLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curLevel<0 || !isSelectSecondCategory){
                    return;
                }
                curLevel =1;
                setCategoryTextColor(1);
                CategoryText2 = mSecondCategoryText.getText().toString();
                sendSelectedCategory(1);

            }
        });
        mTertiaryCategoryLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curLevel<0 || !isSelectTertiaryCategory){
                    return;
                }
                curLevel =2;
                setCategoryTextColor(2);
                CategoryText3 = mTertiaryCategoryText.getText().toString();
                sendSelectedCategory(2);

            }
        });

        selectedItems =  getArgSerializable(ARG_SELECTED_ITEMS, new ArrayList<CharSequence>());
        setSelectedItems(selectedItems);

        items = getArgSerializable(ARG_ITEMS, new ArrayList<ICategoryItem>());

        categoryAdapter = new CategoryAdapter(items);
//
//        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ICategoryItem iCategoryItem = items.get(position);
//                itemsSelected.add(iCategoryItem);
//                sendCategoryItemSelect(position);
//                setCategoryView(position);
//
//            }
//        });
        categoryAdapter.bindToRecyclerView(recyclerView);

        TextView closeButton = view.findViewById(R.id.close_tv);
        CharSequence closeText = getArgCharSequence(ARG_CLOSE_TEXT, "取消");
        closeButton.setText(closeText);

        // 标题
        TextView titleTextView = view.findViewById(R.id.title_tv);
        CharSequence title = getArgCharSequence(ARG_TITLE, "");
        if(title.equals("")){
            titleTextView.setVisibility(View.GONE);
        }
        titleTextView.setText(title);

        TextView confirmTv = view.findViewById(R.id.confirm_tv);
        CharSequence confirmText = getArgCharSequence(ARG_CONFIRM_TEXT, "确定");

        confirmTv.setText(confirmText);

        // 确定按钮
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSelectTopCategory && isSelectSecondCategory && isSelectTertiaryCategory){
                    if (!sendMessage(ARG_ON_CONFIRM_LISTENER)) {
                        dismiss();
                    }
                }else {
                    Toasty.init(getContext())
                            .setMessage(categoryToast)
                            .show();
                }

            }
        });

        // 关闭按钮
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendMessage(ARG_ON_CLOSE_LISTENER)) {
                    dismiss();
                }
            }
        });
    }

//    int mPressedTextColor = getContext().getResources().getColor(R.color.category_check_color);
//    int mTextColor =  getContext().getResources().getColor(R.color.category_list_text_color);

    class CategoryAdapter extends BaseQuickAdapter<ICategoryItem, BaseViewHolder> {

        private int curPosition =-1;

        public CategoryAdapter(@Nullable List<ICategoryItem> data) {
            super(R.layout.item_bottom_category, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, ICategoryItem item) {
            final TextView titleView = helper.getView(R.id.text);
            titleView.setText(item.getCategoryName());
            titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
            helper.itemView.setSelected(false);
            titleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://0
                            titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                            break;
                        case MotionEvent.ACTION_UP://1
                            titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                            sendCategoryItemSelect(helper.getLayoutPosition());
                            setCategoryView(helper.getLayoutPosition());
                            break;
                        case MotionEvent.ACTION_MOVE://2
                            titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                            break;
                        case MotionEvent.ACTION_CANCEL://2
                            titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                            break;
                    }
                    return true;
                }
            });
            if(curLevel == 0){
                if(item.getCategoryName().equals(CategoryText1)){
                    titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                    helper.itemView.setSelected(true);
                } else {
                    titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                    helper.itemView.setSelected(false);
                }

            }
            if(curLevel == 1){
                if(item.getCategoryName().equals(CategoryText2)){
                    titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                    helper.itemView.setSelected(true);
                } else {
                    titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                    helper.itemView.setSelected(false);
                }

            }
            if(curLevel == 2){

                if(curPosition == helper.getLayoutPosition()){
                    titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                    helper.itemView.setSelected(true);
                    curPosition = -1;
                } else {
                    if(item.getCategoryName().equals(CategoryText3)){
                        titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                        helper.itemView.setSelected(true);
                    } else {
                        titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                        helper.itemView.setSelected(false);
                    }

                }


            }
            if(curLevel == 3){

                if(curPosition == helper.getLayoutPosition()){
                    titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                    helper.itemView.setSelected(true);
                    curPosition = -1;
                } else {
                    if(item.getCategoryName().equals(CategoryText3)){
                        titleView.setTextColor(getResources().getColor(R.color.category_check_color));
                        helper.itemView.setSelected(true);
                    } else {
                        titleView.setTextColor(getResources().getColor(R.color.category_list_text_color));
                        helper.itemView.setSelected(false);
                    }

                }


            }
        }
        public void setSelection(int selectedPosition){
            curPosition = selectedPosition;
           notifyDataSetChanged();
        }
    }


    public static class Builder {

        ArrayList<ICategoryItem> items;
        ArrayList<CharSequence> selectedCategoryItems;
        CharSequence title; // 标题
        CharSequence closeText; // 关闭文本
        CharSequence confirmText; // 关闭文本
        int maxCategoryLevel;
        int maxReservedLevel;
        OnCloseListener<CategorySelectionDialogFragment> onCloseListener; // 关闭监听器
        OnConfirmListener<CategorySelectionDialogFragment> onConfirmListener; // 关闭监听器
        OnCategoryItemSelectListener<CategorySelectionDialogFragment> onCategoryItemSelectListener;
        OnSelectedCategoryClickListener<CategorySelectionDialogFragment> onSelectedCategoryClickListener;

        public <T extends ICategoryItem> Builder setCategoryList(ArrayList<T> items) {
            this.items = (ArrayList<ICategoryItem>) items;
            return this;
        }

        public  Builder setSelectedCategoryItems(ArrayList<CharSequence> items){
            this.selectedCategoryItems = items;
            return this;
        }

        public Builder setCancelText(CharSequence mCancelText) {
            this.closeText = mCancelText;
            return this;
        }
        public Builder setConfirmText(CharSequence mConfirmText) {
            this.confirmText = mConfirmText;
            return this;
        }
        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }
        public Builder setMaxCategoryLevel(int mMaxCategoryLevel) {
            this.maxCategoryLevel  = mMaxCategoryLevel;
            return this;
        }
        public Builder setReservedLevel(int mReservedLevel) {
            this.maxReservedLevel  = mReservedLevel;
            return this;
        }

        public Builder setOnCancelListener(OnCloseListener<CategorySelectionDialogFragment> mOnCloseListener) {
            this.onCloseListener = mOnCloseListener;
            return this;
        }
        public Builder setOnConfirmListener(OnConfirmListener<CategorySelectionDialogFragment> mOnConfirmListener) {
           this.onConfirmListener = mOnConfirmListener;
            return this;
        }

        public Builder setOnSelectedCategoryClickListener(OnSelectedCategoryClickListener<CategorySelectionDialogFragment> onSelectedCategoryClickListener) {
            this.onSelectedCategoryClickListener = onSelectedCategoryClickListener;
            return this;
        }

        public Builder setOnCategoryItemSelectListener(OnCategoryItemSelectListener<CategorySelectionDialogFragment> onCategoryItemSelectListener) {
            this.onCategoryItemSelectListener = onCategoryItemSelectListener;
            return this;
        }


        public CategorySelectionDialogFragment build() {
            CategorySelectionDialogFragment dialogFragment = new CategorySelectionDialogFragment();

            Bundle args = new Bundle();
            if (items != null) {
                args.putSerializable(ARG_ITEMS, items);
            }
            if (selectedCategoryItems != null) {
                args.putSerializable(ARG_SELECTED_ITEMS, selectedCategoryItems);
            }
            if (!TextUtils.isEmpty(title)) {
                args.putCharSequence(ARG_TITLE, title);
            }
            if (!TextUtils.isEmpty(closeText)) {
                args.putCharSequence(ARG_CLOSE_TEXT, closeText);
            }
            if (!TextUtils.isEmpty(confirmText)) {
                args.putCharSequence(ARG_CONFIRM_TEXT, confirmText);
            }

            if (onCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, onCloseListener));
            }
            if (onConfirmListener != null) {
                args.putParcelable(ARG_ON_CONFIRM_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CONFIRM_LISTENER, onConfirmListener));
            }
            if (onCategoryItemSelectListener != null) {
                args.putParcelable(ARG_ON_CATEGORY_ITEM_SELECT_LISTENER, dialogFragment.obtainMessage(WHAT_ON_CATEGORY_ITEM_SELECT_LISTENER, onCategoryItemSelectListener));
            }

            if (onSelectedCategoryClickListener != null) {
                args.putParcelable(ARG_ON_SELECTED_CATEGORY_LISTENER, dialogFragment.obtainMessage(WHAT_ON_SELECTED_CATEGORY_LISTENER, onSelectedCategoryClickListener));
            }

            dialogFragment.setArguments(args);
            dialogFragment.setCancelable(true);
            return dialogFragment;
        }

    }
}

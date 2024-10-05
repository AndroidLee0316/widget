package com.pasc.lib.widget.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.list.bean.MultiIem;

import java.util.ArrayList;
import java.util.List;

public class PaRecyclerView extends RecyclerView {

    private Context mContext;

//    private RecyclerView singleRecyclerView;

    private SingleListAdapter singleListAdapter;
    private MultiListAdapter multiListAdapter;

    private int mSinglePosition = -1;
    private int mSingleBeforePosition = -1;

    private int mListSize;
    private int mListColor;

    ArrayList<CharSequence> subdirectorySingleDataList;

    private OnCatalogChangedListener onCatalogChangedListener;



    public interface OnCatalogChangedListener {

        void onItemChanged(int position);
    }


    public PaRecyclerView(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public PaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PaRecyclerView);

        mListSize = (int) a.getDimension(R.styleable.PaRecyclerView_listTextSize, 0);
        mListColor = a.getColor(R.styleable.PaRecyclerView_listTextColor,
                getResources().getColor(R.color.pasc_primary_text));
        setLayoutMode(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view  = inflater.inflate(R.layout.directory_dialog, this, true);
//
//        singleRecyclerView = view.findViewById(R.id.driectory_singrecy_clerView);
        LinearLayoutManager layoutSingleManager = new LinearLayoutManager(mContext);
        layoutSingleManager.setOrientation(RecyclerView.VERTICAL);
        setLayoutManager(layoutSingleManager);

    }

    public void setSingleData( ArrayList<CharSequence> mSubdirectorySingleDataList,int mcurrentSelectPosition,
                              OnCatalogChangedListener mOnCatalogSingleChangedListener){

        if(mSubdirectorySingleDataList == null){
            return;
        }
        onCatalogChangedListener = mOnCatalogSingleChangedListener;
        singleListAdapter = new SingleListAdapter(mSubdirectorySingleDataList);
        singleListAdapter.bindToRecyclerView(this);
        mSinglePosition = mcurrentSelectPosition;
        singleListAdapter.setSelection(mSingleBeforePosition,mSinglePosition);
        mSingleBeforePosition = mSinglePosition;

        singleListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

                mSinglePosition = position;
                singleListAdapter.setSelection(mSingleBeforePosition, position);
                mSingleBeforePosition = mSinglePosition;

                if (onCatalogChangedListener != null) {
                    onCatalogChangedListener.onItemChanged(position);
                }

            }
        });
    }

    public void setMultiData(ArrayList<MultiIem> multiIemList,
                             OnCatalogChangedListener mOnCatalogSingleChangedListener){
        onCatalogChangedListener = mOnCatalogSingleChangedListener;
        if(multiIemList == null){
            return;
        }
        multiListAdapter = new MultiListAdapter(multiIemList);
        multiListAdapter.bindToRecyclerView(this);

    }


    class SingleListAdapter extends  BaseQuickAdapter<CharSequence,BaseViewHolder>{


        private int singlePosition =-1;
        private int singleBeforePosition = -1;

        public SingleListAdapter(@Nullable List<CharSequence> data) {
            super(R.layout.directory_single_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CharSequence item) {

            TextView titleView = helper.getView(R.id.item_title);

            titleView.setText(item);

            if(mListSize == 0){
                titleView.setTextSize(17);
            }else {
                titleView.getPaint().setTextSize(mListSize);
            }
            titleView.setTextColor(mListColor);

            if(singlePosition == helper.getLayoutPosition()){
//                titleView.setTextColor(getResources().getColor(R.color.pasc_primary));
                helper.itemView.setSelected(true);
            } else {
//                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                helper.itemView.setSelected(false);
            }
            if(singleBeforePosition == helper.getLayoutPosition()){
//                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                helper.itemView.setSelected(false);
            }

        }
        public void setSelection(int beforePosition ,int selectedPosition){
            this.singlePosition = selectedPosition;
            notifyItemChanged(singlePosition);
            if(beforePosition != selectedPosition){
                this.singleBeforePosition = beforePosition;
                notifyItemChanged(singleBeforePosition);
            }

        }

    }



    class MultiListAdapter extends  BaseQuickAdapter<MultiIem,BaseViewHolder>{


        private int singlePosition =-1;
        private int singleBeforePosition = -1;

        public MultiListAdapter(@Nullable List<MultiIem> data) {
            super(R.layout.directory_multi_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiIem item) {

            RelativeLayout item2Rel = helper.getView(R.id.item2_rel);
            LinearLayout linGo = helper.getView(R.id.lin_go);
            TextView titleView = helper.getView(R.id.item_title);
            TextView item1Left =  helper.getView(R.id.item1_left);
            TextView item1Right =  helper.getView(R.id.item1_right);

            TextView item2Left =  helper.getView(R.id.item2_left);
            TextView item2Right =  helper.getView(R.id.item2_right);

            titleView.setText(item.getTitle());
            item1Left.setText(item.getItemSub1().getLeftText());
            item1Right.setText(item.getItemSub1().getRightText());

            if(item.getItemSub2() != null){
                item2Left.setText(item.getItemSub2().getLeftText());
                item2Right.setText(item.getItemSub2().getRightText());
            }else {
                item2Rel.setVisibility(GONE);
            }

            final int position = helper.getLayoutPosition();

            linGo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCatalogChangedListener != null) {
                        onCatalogChangedListener.onItemChanged(position);
                    }
                }
            });

        }

    }


}

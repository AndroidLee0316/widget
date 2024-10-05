package com.pasc.lib.widget.catalog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.catalog.bean.CatalogDataBean;
import com.pasc.lib.widget.catalog.bean.SubCatalogDataBean;

import java.util.ArrayList;
import java.util.List;

public class CatalogSelect extends LinearLayout{
    private Context mContext;

    private RecyclerView singleRecyclerView;
    private RecyclerView recyclerView;
    private RecyclerView subRecyclerView;

    private SingleListAdapter singleListAdapter;
    private SelectListAdapter selectListAdapter;
    private SelectSubListAdapter selectSubListAdapter;

    private int mSinglePosition = -1;
    private int mSingleBeforePosition = -1;

    private int mPosition = -1;
    private int mBeforePosition = -1;

    private int mSubPosition = -1;
    private int mSubBeforePosition = -1;

    private int mOneLevelPosition = -1;
    private int mSubTwoLevelPosition = -1;

    private ArrayList<SubCatalogDataBean> subdirectoryDataList;

    ArrayList<CharSequence> subdirectorySingleDataList;

    private OnCatalogMultiChangedListener onCatalogMultiChangedListener;

    private OnCatalogSingleChangedListener onCatalogSingleChangedListener;


    public interface OnCatalogMultiChangedListener {

        void onItemChanged(int oneLevelPosition,int twoLevelPosition);
    }

    public interface OnCatalogSingleChangedListener {

        void onItemChanged(int position);
    }


    public CatalogSelect(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public CatalogSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setLayoutMode(VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.directory_dialog, this, true);



        singleRecyclerView = view.findViewById(R.id.driectory_singrecy_clerView);
        LinearLayoutManager layoutSingleManager = new LinearLayoutManager(mContext);
        layoutSingleManager.setOrientation(RecyclerView.VERTICAL);
        singleRecyclerView.setLayoutManager(layoutSingleManager);


         recyclerView = view.findViewById(R.id.driectory_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

         subRecyclerView = view.findViewById(R.id.sub_driectory_recyclerView);
        LinearLayoutManager layoutManagerNew = new LinearLayoutManager(mContext);
        layoutManagerNew.setOrientation(RecyclerView.VERTICAL);
        subRecyclerView.setLayoutManager(layoutManagerNew);

    }

    public void setSingleData( ArrayList<CharSequence> mSubdirectorySingleDataList,int mcurrentSelectPosition,
                               OnCatalogSingleChangedListener mOnCatalogSingleChangedListener){

     if(mSubdirectorySingleDataList == null){
         return;
     }
        onCatalogSingleChangedListener = mOnCatalogSingleChangedListener;
        recyclerView.setVisibility(GONE);
        subRecyclerView.setVisibility(GONE);
        singleRecyclerView.setVisibility(VISIBLE);
        singleListAdapter = new SingleListAdapter(mSubdirectorySingleDataList);
        singleListAdapter.bindToRecyclerView(singleRecyclerView);
        mSinglePosition = mcurrentSelectPosition;
        singleListAdapter.setSelection(mSingleBeforePosition,mSinglePosition);
        mSingleBeforePosition = mSinglePosition;

        singleListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

                mSinglePosition = position;
                singleListAdapter.setSelection(mSingleBeforePosition, position);
                mSingleBeforePosition = mSinglePosition;

                if (onCatalogSingleChangedListener != null) {
                    onCatalogSingleChangedListener.onItemChanged(position);
                }

            }
        });
    }


    public void setMultiData(final ArrayList<CatalogDataBean> directoryDataList, int mSelectPosition,
                             int mSubSelectPosition , OnCatalogMultiChangedListener mOnCatalogMultiChangedListener){
      if(directoryDataList == null){
       return;
        }
        singleRecyclerView.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);
        subRecyclerView.setVisibility(VISIBLE);
        onCatalogMultiChangedListener = mOnCatalogMultiChangedListener;
        selectListAdapter = new SelectListAdapter(directoryDataList);
        selectListAdapter.bindToRecyclerView(recyclerView);
        mOneLevelPosition = mSelectPosition;
        mSubTwoLevelPosition = mSubSelectPosition;
        mPosition = mSelectPosition;
        selectListAdapter.setSelection(mBeforePosition,mOneLevelPosition);
        mBeforePosition = mPosition;
        subdirectoryDataList = directoryDataList.get(mSubTwoLevelPosition).getSubList();
        selectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {


                subdirectoryDataList = directoryDataList.get(position).getSubList();

                mOneLevelPosition = position;

                        mSubPosition = position;
                selectListAdapter.setSelection(mBeforePosition,position);
                if(mBeforePosition != position){
                    selectSubListAdapter.setSubSelectedPostion( -1);
                }
                mBeforePosition = position;

                selectSubListAdapter.setNewData(subdirectoryDataList);
            }
        });

        selectSubListAdapter = new SelectSubListAdapter(directoryDataList.get(mSelectPosition).getSubList());
        selectSubListAdapter.bindToRecyclerView(subRecyclerView);


        mSubPosition = mSelectPosition;
        selectSubListAdapter.setSubSelection(mSubBeforePosition,mSubTwoLevelPosition);
        mSubBeforePosition = mPosition;
        selectSubListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

                mSubPosition = position;

                mSubTwoLevelPosition = position;
                selectSubListAdapter.setSubSelection(mSubBeforePosition,position);
                mSubBeforePosition = position;

                if (onCatalogMultiChangedListener != null) {
                    onCatalogMultiChangedListener.onItemChanged(mOneLevelPosition,mSubTwoLevelPosition);
                }
            }
        });


    }

    public void setOnCatalogMultiChangedListener(OnCatalogMultiChangedListener listener) {
        onCatalogMultiChangedListener = listener;
    }

    public void setOnCatalogSingleChangedListener(OnCatalogSingleChangedListener listener) {
        onCatalogSingleChangedListener = listener;
    }




    class SingleListAdapter extends  BaseQuickAdapter<CharSequence,BaseViewHolder >{


        private int singlePosition =-1;
        private int singleBeforePosition = -1;

        public SingleListAdapter(@Nullable List<CharSequence> data) {
            super(R.layout.directory_single_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CharSequence item) {

            TextView titleView = helper.getView(R.id.item_title);

            titleView.setText(item);

            if(singlePosition == helper.getLayoutPosition()){
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary));
                helper.itemView.setSelected(true);
            } else {
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                helper.itemView.setSelected(false);
            }
            if(singleBeforePosition == helper.getLayoutPosition()){
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
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



    class SelectListAdapter extends BaseQuickAdapter<CatalogDataBean,BaseViewHolder> {

        private int selectedPosition = -1;
        private int beforePosition = -1;

        public SelectListAdapter(@Nullable ArrayList<CatalogDataBean> data) {
            super(R.layout.directory_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CatalogDataBean directoryDataBean) {
            TextView titleView = helper.getView(R.id.item_title);

            titleView.setText(directoryDataBean.getTitle());

            if(selectedPosition == helper.getLayoutPosition()){
                helper.itemView.setSelected(true);
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary));
                titleView.setBackgroundColor(getResources().getColor(R.color.catalog_select_first_press_color));
            } else {
                helper.itemView.setSelected(false);
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                titleView.setBackgroundColor(getResources().getColor(R.color.white));
            }
            if(beforePosition == helper.getLayoutPosition()){
                helper.itemView.setSelected(false);
                titleView.setBackgroundColor(getResources().getColor(R.color.white));
            }

        }

        public void setSelection(int beforePosition ,int selectedPosition){
            this.selectedPosition = selectedPosition;
            notifyItemChanged(selectedPosition);
            if(beforePosition != selectedPosition){
                this.beforePosition = beforePosition;
                notifyItemChanged(beforePosition);
            }

        }

    }

    class SelectSubListAdapter extends BaseQuickAdapter<SubCatalogDataBean,BaseViewHolder> {

        private int subSelectedPosition = -1;
        private int subBeforePosition = -1;


        public SelectSubListAdapter(@Nullable ArrayList<SubCatalogDataBean> data) {
            super(R.layout.directory_sub_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SubCatalogDataBean subDirectoryDataBean) {
            TextView titleView = helper.getView(R.id.item_sub_title);

            titleView.setText(subDirectoryDataBean.getSubTitle());

            if(subSelectedPosition == helper.getLayoutPosition()){
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary));
                helper.itemView.setSelected(true);

            } else {
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                helper.itemView.setSelected(false);
            }
            if(subBeforePosition == helper.getLayoutPosition()){
                titleView.setTextColor(getResources().getColor(R.color.pasc_primary_text));
                helper.itemView.setSelected(false);
            }

        }

        public void setSubSelectedPostion (int postion){
            subSelectedPosition= postion;
        }

        public void setSubSelection(int beforePostion ,int selectedPosition){
            this.subSelectedPosition = selectedPosition;
            notifyItemChanged(subSelectedPosition);
            if(beforePostion != selectedPosition){
                this.subBeforePosition = beforePostion;
                notifyItemChanged(beforePostion);
            }

        }

    }

}

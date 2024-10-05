package com.pasc.lib.widget.catalog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.catalog.bean.CatalogDataBean;

import java.util.ArrayList;
import java.util.List;

public class CatalogDialogFragment extends BaseDialogFragment {

    final CatalogController controller;

    public CatalogDialogFragment(){
        controller = new CatalogController();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.directory_dialog, null);

        RecyclerView recyclerView = view.findViewById(R.id.driectory_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        final CatalogDialogFragment.SelectListAdapter selectListAdapter = new CatalogDialogFragment.SelectListAdapter(controller.getDirectoryDataList());
        selectListAdapter.bindToRecyclerView(recyclerView);




        RecyclerView subRecyclerView = view.findViewById(R.id.sub_driectory_recyclerView);
        LinearLayoutManager layoutManagerNew = new LinearLayoutManager(getActivity());
        layoutManagerNew.setOrientation(RecyclerView.VERTICAL);
        subRecyclerView.setLayoutManager(layoutManagerNew);
        final CatalogDialogFragment.SelectSubListAdapter selectSubListAdapter = new CatalogDialogFragment.SelectSubListAdapter(controller.getDirectoryDataList());
        selectSubListAdapter.bindToRecyclerView(subRecyclerView);





        return view;
    }

    class SelectListAdapter extends BaseQuickAdapter<CatalogDataBean,BaseViewHolder> {
        public SelectListAdapter(int layoutResId, @Nullable List<CatalogDataBean> data) {
            super(layoutResId, data);
        }

        public SelectListAdapter(@Nullable List<CatalogDataBean> data) {
            super(R.layout.directory_item, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, CatalogDataBean directoryDataBean) {
            TextView titleView = helper.getView(R.id.item_title);

            titleView.setText(directoryDataBean.getTitle());

        }

    }

    class SelectSubListAdapter extends BaseQuickAdapter<CatalogDataBean,BaseViewHolder> {
        public SelectSubListAdapter(int layoutResId, @Nullable List<CatalogDataBean> data) {
            super(layoutResId, data);
        }

        public SelectSubListAdapter(@Nullable List<CatalogDataBean> data) {
            super(R.layout.directory_item, data);
        }


        @Override
        protected void convert(BaseViewHolder helper, CatalogDataBean directoryDataBean) {
            TextView titleView = helper.getView(R.id.item_title);

            titleView.setText(directoryDataBean.getTitle());

        }

    }


    public static class Builder{
        public final CatalogController.ControllerParams mDialogcontroller;
        public Builder(){
            mDialogcontroller = new CatalogController.ControllerParams();
        }
        public Builder setCurrentSelectTextColor(int mColor) {
            mDialogcontroller.currentSelectTextColor = mColor;
            return this;
        }
        public Builder setCurrentSelectbackground(int mBackground) {
            mDialogcontroller.currentSelectbackground = mBackground;
            return this;
        }
        public Builder setDirectoryDataList(ArrayList<CatalogDataBean> mDirectoryDataList) {
            mDialogcontroller.directoryDataList = mDirectoryDataList;
            return this;
        }

        public CatalogDialogFragment build(){
            CatalogDialogFragment directoryDialogFragment = new CatalogDialogFragment();
            mDialogcontroller.apply(directoryDialogFragment.controller);

            return directoryDialogFragment;
        }
        public CatalogDialogFragment show(FragmentManager manager, String tag){
            CatalogDialogFragment customDialog = build();
            customDialog.show(manager,tag);
            return customDialog;
        }

    }

}

package com.pasc.lib.widget.dialog.bottompicker;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.BaseDialogFragment;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.bottompicker.widget.DatePicker;

import java.util.List;

public class ListPickerDialogFragment extends BaseDialogFragment{

    final pickerController  controller;
    private int mPosition;

    public ListPickerDialogFragment(){
        controller = new pickerController();
    }

    public int getPosition(){
        return  mPosition;
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
        View view = inflater.inflate(R.layout.date_picker_dialog, null);
        TextView cancelTv = view.findViewById(R.id.close_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        TextView confirmTv = view.findViewById(R.id.confirm_tv);

        if(controller.getCloseText() != null){
            cancelTv.setText(controller.getCloseText());
        }
        if(controller.getTitle() != null){
            titleTv.setText(controller.getTitle());
            //判断当前标题是否需要加粗
            if (controller.isBoldForTitle()) {
                //加粗
                titleTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                //常规
                titleTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }

        if(controller.getConfirmText() != null){
            confirmTv.setText(controller.getConfirmText());
        }


        DatePicker datePicker = view.findViewById(R.id.date_picker);

            if(controller.getItems() != null){
                if(controller.getItems().length>0){
                    mPosition = controller.getCurrentPosition();
                    datePicker.showList(controller.getItems(),controller.getCurrentPosition(),controller.isCircling());
                }
            }

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }

            @Override
            public void onItemChanged(int position) {

                mPosition = position;

            }
        });
        final Bundle arguments = getArguments();

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean hasListener = false;
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_ON_CLOSE_LISTENER);
                    if (parcelable != null && parcelable instanceof Message) {
                        Message.obtain(((Message) parcelable)).sendToTarget();
                        hasListener = true;
                    }
                }
                if (!hasListener) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }

            }
        });

        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasListener = false;
                if (arguments != null) {
                    Parcelable parcelable = arguments.getParcelable(ARG_ON_CONFIRM_LISTENER);
                    if (parcelable != null && parcelable instanceof Message) {
                        Message.obtain(((Message) parcelable)).sendToTarget();
                        hasListener = true;
                    }
                }
                if (!hasListener) {
                    // 如果没有设置监听，则关闭窗口
                    dismiss();
                }

            }
        });

        return view;
    }

    public static class Builder{
        public final pickerController.ControllerParams controllerParams;
        public Builder(){
            controllerParams = new pickerController.ControllerParams();
        }

        public ListPickerDialogFragment.Builder setCloseText(CharSequence mCloseText) {
            controllerParams.closeText = mCloseText;
            return this;
        }

        public ListPickerDialogFragment.Builder setTitle(CharSequence title){
            controllerParams.title = title;
            return this;
        }

        public ListPickerDialogFragment.Builder setConfirmText(CharSequence mConfirmText) {
            controllerParams.confirmText = mConfirmText;
            return this;
        }

        public ListPickerDialogFragment.Builder setListPicker(List<String> listPicker, int mCurrentPosition){
            String[] displayedValues = listPicker.toArray(new String[listPicker.size()]);
            setItems(displayedValues,mCurrentPosition);
            return this;
        }

        public ListPickerDialogFragment.Builder setItems(String[] items, int mCurrentPosition){
            controllerParams.items = items;
            controllerParams.currentPosition = mCurrentPosition;
            controllerParams.isList = true;
            return this;
        }

        public ListPickerDialogFragment.Builder setOnConfirmListener(OnConfirmListener<ListPickerDialogFragment> onConfirmListener) {
            controllerParams.onListConfirmListener = onConfirmListener;
            return this;
        }
        public ListPickerDialogFragment.Builder setCircling(boolean isCircling) {
            controllerParams.circling = isCircling;
            return this;
        }

        public ListPickerDialogFragment.Builder setBoldForTitle(boolean isBoldForTitle) {
            controllerParams.isBoldForTitle = isBoldForTitle;
            return this;
        }

        public ListPickerDialogFragment.Builder setOnCloseListener(OnCloseListener<ListPickerDialogFragment> onCloseListener) {
            controllerParams.onListCloseListener = onCloseListener;
            return this;
        }


        public ListPickerDialogFragment build(){
            ListPickerDialogFragment bottomPickerDialogFragment = new ListPickerDialogFragment();
            controllerParams.apply(bottomPickerDialogFragment.controller);


            Bundle args = new Bundle();

            if (controllerParams.onListConfirmListener != null) {
                args.putParcelable(ARG_ON_CONFIRM_LISTENER, bottomPickerDialogFragment.obtainMessage(WHAT_ON_CONFIRM_LISTENER, controllerParams.onListConfirmListener));
            }
            if (controllerParams.onListCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, bottomPickerDialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, controllerParams.onListCloseListener));
            }
            bottomPickerDialogFragment.setArguments(args);


            return bottomPickerDialogFragment;
        }
        public ListPickerDialogFragment show(FragmentManager manager, String tag){
            ListPickerDialogFragment customDialog = build();
            customDialog.show(manager,tag);
            return customDialog;
        }

    }



}

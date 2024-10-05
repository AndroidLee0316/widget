package com.pasc.lib.widget.dialog.bottompicker;

import android.app.Dialog;
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
import com.pasc.lib.widget.dialog.bottompicker.utils.PickerDateType;
import com.pasc.lib.widget.dialog.bottompicker.widget.DatePicker;

public class DatePickerDialogFragment extends BaseDialogFragment {

    final pickerController controller;
    private int mYear, mMonth, mDay;
    private int mPosition;

    public DatePickerDialogFragment() {
        controller = new pickerController();
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth + 1;
    }

    public int getDay() {
        return mDay;
    }

    public int getPosition() {
        return mPosition;
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

        if (controller.getCloseText() != null) {
            cancelTv.setText(controller.getCloseText());
        }
        if (controller.getTitle() != null) {
            titleTv.setText(controller.getTitle());
        }

        if (controller.getConfirmText() != null) {
            confirmTv.setText(controller.getConfirmText());
        }

        DatePicker datePicker = view.findViewById(R.id.date_picker);

        if (controller.getStartYear() != 0) {
            datePicker.setStartYear(controller.getStartYear());

        }

        if (controller.getEndYear() != 0) {
            datePicker.setEndYear(controller.getEndYear());

        }

        if (controller.getPickerDateType() != null) {
            if (controller.getPickerDateType() == PickerDateType.YEAR_MONTH) {
                datePicker.showYearMonth(controller.isCircling());
                if (controller.getYear() != 0 && controller.getMonth() != 0) {
                    datePicker.setSelectedDate(controller.getYear(), controller.getMonth(),PickerDateType.YEAR_MONTH);
                }
            } else if (controller.getPickerDateType() == PickerDateType.MONTH_DAY) {
                datePicker.showMonthDay(controller.isCircling());
                if (controller.getMonth() != 0 && controller.getDay() != 0) {
                    datePicker.setSelectedDate(controller.getMonth(), controller.getDay(),PickerDateType.MONTH_DAY);
                }
            } else {
                datePicker.showYearMonthDay(controller.isCircling());
                if (controller.getYear() != 0 && controller.getMonth() != 0 && controller.getDay() != 0) {
                    datePicker.setSelectedDate(controller.getYear(), controller.getMonth(), controller.getDay());
                }
            }
        } else {
            datePicker.showYearMonthDay(controller.isCircling());
            if (controller.getYear() != 0 && controller.getMonth() != 0 && controller.getDay() != 0) {
                datePicker.setSelectedDate(controller.getYear(), controller.getMonth(), controller.getDay());
            }
        }

        mYear = datePicker.getYear();
        mMonth = datePicker.getMonth();
        mDay = datePicker.getDayOfMonth();

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
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

    public static class Builder {
        public final pickerController.ControllerParams mDialogcontroller;

        public Builder() {
            mDialogcontroller = new pickerController.ControllerParams();
        }

        public DatePickerDialogFragment.Builder setCloseText(CharSequence mCloseText) {
            mDialogcontroller.closeText = mCloseText;
            return this;
        }

        public DatePickerDialogFragment.Builder setTitle(CharSequence title) {
            mDialogcontroller.title = title;
            return this;
        }

        public DatePickerDialogFragment.Builder setConfirmText(CharSequence mConfirmText) {
            mDialogcontroller.confirmText = mConfirmText;
            return this;
        }

        public DatePickerDialogFragment.Builder setPickerDateType(PickerDateType pickerDateType) {
            mDialogcontroller.pickerDateType = pickerDateType;
            return this;
        }

        public DatePickerDialogFragment.Builder setOnConfirmListener(OnConfirmListener<DatePickerDialogFragment> onConfirmListener) {
            mDialogcontroller.onConfirmListener = onConfirmListener;
            return this;
        }

        public DatePickerDialogFragment.Builder setCircling(boolean isCircling) {
            mDialogcontroller.circling = isCircling;
            return this;
        }

        public DatePickerDialogFragment.Builder setStartYear(int mStartYear) {
            mDialogcontroller.startYear = mStartYear;
            return this;
        }

        public DatePickerDialogFragment.Builder setEndYear(int mEndYear) {
            mDialogcontroller.endYear = mEndYear;
            return this;
        }

        public DatePickerDialogFragment.Builder setSelectedDate(int year, int month, int day) {
            mDialogcontroller.year = year;
            mDialogcontroller.month = month;
            mDialogcontroller.day = day;
            return this;
        }

        public DatePickerDialogFragment.Builder setSelectedDate(int yearOrMonth, int monthOrDay) {
            if (mDialogcontroller.pickerDateType == PickerDateType.YEAR_MONTH) {
                mDialogcontroller.year = yearOrMonth;
                mDialogcontroller.month = monthOrDay;
            } else if (mDialogcontroller.pickerDateType == PickerDateType.MONTH_DAY) {
                mDialogcontroller.month = yearOrMonth;
                mDialogcontroller.day = monthOrDay;
            }

            return this;
        }

        public DatePickerDialogFragment.Builder setOnCloseListener(OnCloseListener<DatePickerDialogFragment> onCloseListener) {
            mDialogcontroller.onCloseListener = onCloseListener;
            return this;
        }


        public DatePickerDialogFragment build() {
            DatePickerDialogFragment bottomPickerDialogFragment = new DatePickerDialogFragment();
            mDialogcontroller.apply(bottomPickerDialogFragment.controller);


            Bundle args = new Bundle();

            if (mDialogcontroller.onConfirmListener != null) {
                args.putParcelable(ARG_ON_CONFIRM_LISTENER, bottomPickerDialogFragment.obtainMessage(WHAT_ON_CONFIRM_LISTENER, mDialogcontroller.onConfirmListener));
            }
            if (mDialogcontroller.onCloseListener != null) {
                args.putParcelable(ARG_ON_CLOSE_LISTENER, bottomPickerDialogFragment.obtainMessage(WHAT_ON_CLOSE_LISTENER, mDialogcontroller.onCloseListener));
            }
            bottomPickerDialogFragment.setArguments(args);


            return bottomPickerDialogFragment;
        }

        public DatePickerDialogFragment show(FragmentManager manager, String tag) {
            DatePickerDialogFragment customDialog = build();
            customDialog.show(manager, tag);
            return customDialog;
        }

    }


}

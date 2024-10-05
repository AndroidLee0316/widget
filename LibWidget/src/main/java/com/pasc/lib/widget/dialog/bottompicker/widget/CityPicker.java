package com.pasc.lib.widget.dialog.bottompicker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.pasc.lib.widget.R;
import com.pasc.lib.widget.dialog.bottompicker.bean.AreaItem;
import com.pasc.lib.widget.dialog.bottompicker.bean.SecondAreaItem;
import com.pasc.lib.widget.dialog.bottompicker.bean.ThirdAreaItem;

import java.util.ArrayList;
import java.util.List;

public class CityPicker<T> extends FrameLayout {
    private Context mContext;
    private final NumberPicker mPickerProvince;
    private final EditText mPickerProvinceInput;

    private final NumberPicker mPickerCity;
    private final EditText mPickerCityInput;

    private final NumberPicker mPickerCountry;
    private final EditText mPickerCountryInput;

    private OnDateChangedListener mOnDateChangedListener;


    private List<T> options1Items;
    private List<List<T>> options2Items;
    private List<List<List<T>>> options3Items;

    private int option1 = 0,option2 = 0,option3 = 0;

    private boolean mIsCircle = false;

    public interface OnDateChangedListener {

        void onDateChanged( int options1, int options2, int options3);
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        mOnDateChangedListener = listener;
    }

    public CityPicker(@NonNull Context context) {
        this(context, null);
    }

    public CityPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.datePickerStyle);
    }

    public CityPicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.DatePicker, defStyle, 0);
        int layoutResourceId = attributesArray.getResourceId(R.styleable.DatePicker_layout, R.layout.city_picker);
        attributesArray.recycle();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dataPickerView  = inflater.inflate(layoutResourceId, this, true);


        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updateInputState();
                updateSpinners(picker);
                if (mOnDateChangedListener != null) {
                    mOnDateChangedListener.onDateChanged(mPickerProvince.getValue(),mPickerCity.getValue(),mPickerCountry.getValue());
                }

            }
        };

        mPickerProvince = (NumberPicker)findViewById(R.id.province);
        mPickerProvince.setOnLongPressUpdateInterval(100);
        mPickerProvince.setOnValueChangedListener(onValueChangeListener);
        mPickerProvinceInput = (EditText) findViewById(R.id.numberpicker_input);

        mPickerCity = (NumberPicker)findViewById(R.id.city);
        mPickerCity.setOnLongPressUpdateInterval(100);
        mPickerCity.setOnValueChangedListener(onValueChangeListener);
        mPickerCityInput = (EditText) findViewById(R.id.numberpicker_input);

        mPickerCountry = (NumberPicker)findViewById(R.id.country);
        mPickerCountry.setOnLongPressUpdateInterval(100);
        mPickerCountry.setOnValueChangedListener(onValueChangeListener);
        mPickerCountryInput = (EditText)findViewById(R.id.numberpicker_input);
    }


    private void updateSpinners(NumberPicker picker) {
        if(picker == mPickerProvince){
            int options1 = mPickerProvince.getValue();

            List<String> listCity = new ArrayList<>();
            int citySize = 0;
            if(options2Items.get(options1) != null){
               citySize = options2Items.get(options1).size();
            }
            for (int i =0; i< citySize;i++){
                listCity.add(options2Items.get(options1).get(i).toString());
            }
            if(listCity.size() == 0){
                listCity.add("");
            }

            String[] displayedCityValues = listCity.toArray(new String[listCity.size()]);
            mPickerCity.setDisplayedValues(displayedCityValues);
            mPickerCity.invalidate();
            int maxValue = 0;
            if(displayedCityValues.length > 0){
                maxValue = displayedCityValues.length -1;
            }
            mPickerCity.setMaxValue(maxValue);
            mPickerCity.setMinValue(0);
            mPickerCity.setValue(0);
            mPickerCity.setWrapSelectorWheel(mIsCircle);


            int options2 = mPickerCity.getValue();

            List<String> listCountry = new ArrayList<>();

            int CountrySize = 0;
            if(options2Items.get(options1) != null){
                if(options3Items.get(options1) != null){
                    if(options3Items.get(options1).size() >options2){
                        if(options3Items.get(options1).get(options2) != null){
                            CountrySize = options3Items.get(options1).get(options2).size();
                        }
                    }

                }

            }
            for (int i =0; i< CountrySize;i++){
                listCountry.add(options3Items.get(options1).get(options2).get(i).toString());
            }
            if(listCountry.size() == 0){
                listCountry.add("");
            }
            String[] displayedCountryValues = listCountry.toArray(new String[listCountry.size()]);
            mPickerCountry.invalidate();
            mPickerCountry.setDisplayedValues(displayedCountryValues);
            int maxCountryValue = 0;
            if(displayedCityValues.length > 0){
                maxCountryValue = displayedCityValues.length -1;
            }
            mPickerCountry.setMaxValue(maxCountryValue);
            mPickerCountry.setMinValue(0);
            mPickerCountry.setValue(0);
            mPickerCountry.setWrapSelectorWheel(mIsCircle);


        } else if(picker == mPickerCity){

            int options1 = mPickerProvince.getValue();
            int options2 = mPickerCity.getValue();

            List<String> listCountry = new ArrayList<>();

            int CountrySize = 0;
            if(options2Items.get(options1) != null){
                if(options3Items.get(options1) != null){
                    if(options3Items.get(options1).size() >options2){
                        if(options3Items.get(options1).get(options2) != null){
                            CountrySize = options3Items.get(options1).get(options2).size();
                        }
                    }

                }

            }
            for (int i =0; i< CountrySize;i++){
                listCountry.add(options3Items.get(options1).get(options2).get(i).toString());
            }
            if(listCountry.size() == 0){
                listCountry.add("");
            }
            mPickerCountry.invalidate();
            String[] displayedCountryValues = listCountry.toArray(new String[listCountry.size()]);
            mPickerCountry.setDisplayedValues(displayedCountryValues);

            int maxCountryValue = 0;
            if(displayedCountryValues.length > 0){
                maxCountryValue = displayedCountryValues.length -1;
            }
            mPickerCountry.setMaxValue(maxCountryValue);
            mPickerCountry.setMinValue(0);
            mPickerCountry.setValue(0);
            mPickerCountry.setWrapSelectorWheel(mIsCircle);
        }

    }

//    public void showList(String[] displayedValues,int CurrentPosition,boolean isCircling){
//
//        mPickerProvince.setDisplayedValues(displayedValues);
//        mPickerProvince.setMinValue(0);
//        mPickerProvince.setMaxValue(displayedValues.length -1);
//        mPickerProvince.setValue(CurrentPosition);
////        mPickerProvince.setWrapSelectorWheel(isCircling);
//        invalidate();
//    }
    public void setCircle(boolean isCircle){
        mIsCircle = isCircle;
    }


    public void setSelectOptions(int mOption1, int mOption2, int mOption3) {
        this.option1 = mOption1;
        this.option2 = mOption2;
        this.option3 = mOption3;
        if(options1Items != null && options2Items != null
                && options3Items != null){
            setPicker(options1Items,options2Items,options3Items);

        }
    }

    public void setPicker(List<T> mOptions1Items, List<List<T>> mOptions2Items, List<List<List<T>>> mOptions3Items){
        options1Items = mOptions1Items;
        options2Items= mOptions2Items;
        options3Items = mOptions3Items;
        List<String> listProvince = new ArrayList<>();
        for (int i =0; i< options1Items.size();i++){
            listProvince.add(options1Items.get(i).toString());
        }
        String[] displayedValues = listProvince.toArray(new String[listProvince.size()]);

        mPickerProvince.setDisplayedValues(displayedValues);
        mPickerProvince.setMinValue(0);

        int maxValue = 0;
        if(displayedValues.length > 0){
            maxValue = displayedValues.length -1;
        }
        mPickerProvince.setMaxValue(maxValue);

        if(displayedValues.length > option1){
            mPickerProvince.setValue(option1);
        }else {
            mPickerProvince.setValue(0);
        }

        mPickerProvince.setWrapSelectorWheel(mIsCircle);


        List<String> listCity = new ArrayList<>();

        int citySize = 0;
        if(options2Items.get(option1) != null){
            citySize = options2Items.get(option1).size();
        }
        for (int i =0; i< citySize;i++){
            listCity.add(options2Items.get(option1).get(i).toString());
        }
        if(listCity.size() == 0){
            listCity.add("");
        }

        String[] displayedCityValues = listCity.toArray(new String[listCity.size()]);
        mPickerCity.setDisplayedValues(displayedCityValues);
        mPickerCity.setMinValue(0);
        int maxCityValue = 0;
        if(displayedCityValues.length > 0){
            maxCityValue = displayedCityValues.length -1;
        }
        mPickerCity.setMaxValue(maxCityValue);

        if(displayedCityValues.length > option2){
            mPickerCity.setValue(option2);
        }else {
            mPickerCity.setValue(0);
        }
        mPickerCity.setWrapSelectorWheel(mIsCircle);

        List<String> listCountry = new ArrayList<>();

        int CountrySize = 0;

        if(options2Items.get(option1) != null){
            if(options3Items.get(option1) != null){
                if(options3Items.get(option1).size() >option2){
                    if(options3Items.get(option1).get(option2) != null){
                        CountrySize = options3Items.get(option1).get(option2).size();
                    }
                }

            }

        }

        for (int i =0; i< CountrySize;i++){
            listCountry.add(options3Items.get(option1).get(option2).get(i).toString());
        }
        String[] displayedCountryValues = listCountry.toArray(new String[listCountry.size()]);
        mPickerCountry.setDisplayedValues(displayedCountryValues);
        mPickerCountry.setMinValue(0);
        int maxCountryValue = 0;
        if(displayedCountryValues.length > 0){
            maxCountryValue = displayedCountryValues.length -1;
        }
        mPickerCountry.setMaxValue(maxCountryValue);
        mPickerCountry.setValue(0);
        if(displayedCountryValues.length > option3){
            mPickerCountry.setValue(option3);
        }else {
            mPickerCountry.setValue(0);
        }
        mPickerCountry.setWrapSelectorWheel(mIsCircle);
    }

    private void updateInputState() {

        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            if (inputMethodManager.isActive(mPickerProvinceInput)) {
                mPickerProvinceInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(mPickerCity)) {
                mPickerCity.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(mPickerCountry)) {
                mPickerCountry.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }


}

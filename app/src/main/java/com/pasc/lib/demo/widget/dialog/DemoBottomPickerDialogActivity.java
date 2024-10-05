package com.pasc.lib.demo.widget.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.bottompicker.bean.AreaItem;
import com.pasc.lib.widget.dialog.bottompicker.bean.SecondAreaItem;
import com.pasc.lib.widget.dialog.bottompicker.bean.ThirdAreaItem;
import com.pasc.lib.widget.dialog.OnCloseListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.bottompicker.CityPickerDialogFragment;
import com.pasc.lib.widget.dialog.bottompicker.DatePickerDialogFragment;
import com.pasc.lib.widget.dialog.bottompicker.ListPickerDialogFragment;
import com.pasc.lib.widget.dialog.bottompicker.utils.PickerDateType;
import com.pasc.lib.widget.pickerview.OptionsPickerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Route(path = "/Demo/Dialogs/DatePickerDialogFragment")
public class DemoBottomPickerDialogActivity extends AppCompatActivity {

    private int mPosition = 0;

    private int option1 = 2, option2 = 0, option3 = 0;

    OptionsPickerView pvOptions;
    List<AreaItem> options1Items = new ArrayList<>();
    List<List<SecondAreaItem>> options2Items = new ArrayList<List<SecondAreaItem>>();
    List<List<List<ThirdAreaItem>>> options3Items = new ArrayList<List<List<ThirdAreaItem>>>();
    String province, city, country;
    String provinceName = "", cityName = "", countryName = "";
    List<AreaItem> areaItemList = new ArrayList<>();
    Intent intent;
    String addressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_bottom_selector_dialog);
        setAreaDate();


        ArrayList<String> pickerList = new ArrayList<>();
        pickerList.add("深圳市公安局盐田分局1");
        pickerList.add("深圳市公安局盐田分局2");
        pickerList.add("深圳市公安局盐田分局3");
        pickerList.add("深圳市公安局盐田分局4");
        pickerList.add("深圳市公安局盐田分局5");
        pickerList.add("深圳市公安局盐田分局6");

        //添加数据
        final String[] items = new String[]{"深圳市公安局盐田分局1", "深圳市公安局盐田分局2",
                "深圳市公安局福田分局3", "深圳市公安局福田分局4", "深圳市公安局福田分局5", "深圳市公安局福田分局6"};
        mPosition = items.length / 2;

        findViewById(R.id.bt_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPickerDialogFragment listPickerDialogFragment = new ListPickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("名称")
                        .setBoldForTitle(true)
                        .setListPicker(pickerList, mPosition)
//                        .setItems(items,mPosition)
                        .setCircling(false)
                        .setOnConfirmListener(new OnConfirmListener<ListPickerDialogFragment>() {
                            @Override
                            public void onConfirm(ListPickerDialogFragment dialogFragment) {
                                mPosition = dialogFragment.getPosition();
                                Toast.makeText(DemoBottomPickerDialogActivity.this, "id =" + mPosition, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<ListPickerDialogFragment>() {
                            @Override
                            public void onClose(ListPickerDialogFragment dialogFragment) {

                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                listPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");


            }
        });


        findViewById(R.id.oneoption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialogFragment bottomPickerDialogFragment = new DatePickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("日期")
                        .setCircling(false)
                        .setStartYear(1980)
                        .setEndYear(2030)
                        .setPickerDateType(PickerDateType.YEAR_MONTH)
                        .setOnConfirmListener(new OnConfirmListener<DatePickerDialogFragment>() {
                            @Override
                            public void onConfirm(DatePickerDialogFragment dialogFragment) {
                                String ss = dialogFragment.getYear() + "年" + dialogFragment.getMonth() + "月";
                                Toast.makeText(DemoBottomPickerDialogActivity.this, ss, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<DatePickerDialogFragment>() {
                            @Override
                            public void onClose(DatePickerDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                bottomPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");


            }
        });

        findViewById(R.id.twooption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialogFragment bottomPickerDialogFragment = new DatePickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("日期")
                        .setCircling(true)
                        .setStartYear(1980)
                        .setEndYear(2030)
                        .setPickerDateType(PickerDateType.YEAR_MONTH)
                        .setSelectedDate(2017,4)
                        .setOnConfirmListener(new OnConfirmListener<DatePickerDialogFragment>() {
                            @Override
                            public void onConfirm(DatePickerDialogFragment dialogFragment) {
                                String ss = dialogFragment.getYear() + "年" + dialogFragment.getMonth() + "月"
                                      ;
                                Toast.makeText(DemoBottomPickerDialogActivity.this, ss, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<DatePickerDialogFragment>() {
                            @Override
                            public void onClose(DatePickerDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                bottomPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");


            }
        });
        findViewById(R.id.threeoption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialogFragment bottomPickerDialogFragment = new DatePickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("日期")
                        .setCircling(true)
                        .setStartYear(1980)
                        .setEndYear(2030)
                        .setOnConfirmListener(new OnConfirmListener<DatePickerDialogFragment>() {
                            @Override
                            public void onConfirm(DatePickerDialogFragment dialogFragment) {
                                String ss = dialogFragment.getYear() + "年" + dialogFragment.getMonth() + "月"
                                        + dialogFragment.getDay() + "日";
                                Toast.makeText(DemoBottomPickerDialogActivity.this, ss, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<DatePickerDialogFragment>() {
                            @Override
                            public void onClose(DatePickerDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                bottomPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");


            }
        });
        findViewById(R.id.fouroption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialogFragment bottomPickerDialogFragment = new DatePickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("日期")
                        .setCircling(true)
                        .setStartYear(1980)
                        .setEndYear(2030)
                        .setSelectedDate(2017,2,14)
                        .setOnConfirmListener(new OnConfirmListener<DatePickerDialogFragment>() {
                            @Override
                            public void onConfirm(DatePickerDialogFragment dialogFragment) {
                                String ss = dialogFragment.getYear() + "年" + dialogFragment.getMonth() + "月"
                                        + dialogFragment.getDay() + "日";
                                Toast.makeText(DemoBottomPickerDialogActivity.this, ss, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<DatePickerDialogFragment>() {
                            @Override
                            public void onClose(DatePickerDialogFragment dialogFragment) {
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
                bottomPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");


            }
        });
        findViewById(R.id.city_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CityPickerDialogFragment cityPickerDialogFragment = new CityPickerDialogFragment.Builder()
                        .setCloseText("取消")
                        .setConfirmText("确定")
                        .setTitle("名称")
                        .setCircling(false)
                        .setSelectOptions(option1, option2, option3)
                        .setPicker(options1Items, options2Items, options3Items)
                        .setOnConfirmListener(new OnConfirmListener<CityPickerDialogFragment>() {
                            @Override
                            public void onConfirm(CityPickerDialogFragment dialogFragment) {
                                option1 = dialogFragment.getOptions1();
                                option2 = dialogFragment.getOptions2();
                                option3 = dialogFragment.getOptions3();

                                Toast.makeText(DemoBottomPickerDialogActivity.this,
                                        "options1 =" + option1 + "options2 =" + option2 + "option3=" + option3,
                                        Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .setOnCloseListener(new OnCloseListener<CityPickerDialogFragment>() {
                            @Override
                            public void onClose(CityPickerDialogFragment dialogFragment) {

                                dialogFragment.dismiss();
                            }
                        })
                        .build();

                cityPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");

            }
        });

        findViewById(R.id.city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOptionsPickerView();
//
//                CityPickerDialogFragment cityPickerDialogFragment =new CityPickerDialogFragment.Builder()
//                        .setCloseText("取消")
//                        .setConfirmText("确定")
//                        .setTitle("名称")
//                        .setCircling(false)
//                        .setPicker(options1Items, options2Items, options3Items)
//                        .setOnClickListener(new OnConfirmListener<CityPickerDialogFragment>() {
//                            @Override
//                            public void onConfirm(CityPickerDialogFragment dialogFragment) {
//                                option1 = dialogFragment.getOptions1();
//                                Toast.makeText(DemoBottomPickerDialogActivity.this,"options1 ="+option1,Toast.LENGTH_SHORT).show();
//                                dialogFragment.dismiss();
//                            }
//                        })
//                        .setOnDismissListener(new OnCloseListener<CityPickerDialogFragment>() {
//                            @Override
//                            public void onClose(CityPickerDialogFragment dialogFragment) {
//
//                                dialogFragment.dismiss();
//                            }
//                        })
//                        .build();
//
//                cityPickerDialogFragment.show(getSupportFragmentManager(), "bottomPickerDialogFragment");
            }
        });
    }


    private void setAreaDate() {


        InputStream is = null;
        String s = "";
        try {
            is = this.getAssets().open("areaList.json");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            s = new String(bytes, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            //异常，不处理
            return;
        }
        Gson gson = new Gson();
        List<AreaItem> areaItems = gson.fromJson(s,
                new TypeToken<List<AreaItem>>() {
                }.getType());

        for (int i = 0; i < areaItems.size(); i++) {
            if (areaItems.get(i).parentid.equals("0")) {
                options1Items.add(areaItems.get(i));
            }
        }

        List<SecondAreaItem> tempList1;
        for (int j = 0; j < options1Items.size(); j++) {
            tempList1 = options1Items.get(j).children;
            //解决台湾香港崩溃问题
            if (tempList1.size() == 0) {
                tempList1.add(new SecondAreaItem());
            }
            options2Items.add(tempList1);
        }
        List<List<ThirdAreaItem>> tempListList;
        List<ThirdAreaItem> tempList2;
        for (int i = 0; i < options2Items.size(); i++) {
            tempListList = new ArrayList<List<ThirdAreaItem>>();
            for (int j = 0; j < options2Items.get(i).size(); j++) {
                tempList2 = options2Items.get(i).get(j).children;
                if (tempList2 == null || tempList2.size() == 0) {
                    tempList2 = new ArrayList<ThirdAreaItem>();
                    ThirdAreaItem temp = new ThirdAreaItem();
                    temp.cityName = "";
                    temp.parentid = options2Items.get(i).get(j).codeid;
                    tempList2.add(temp);
                }
                tempListList.add(tempList2);
            }
            options3Items.add(tempListList);
        }

    }

    private void showOptionsPickerView() {
//        if (pvOptions != null && pvOptions.isShowing()) {
//            return;
//        }
        //条件选择器
        pvOptions = new OptionsPickerView.Builder(DemoBottomPickerDialogActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                option1 = options1;
                //返回的分别是三个级别的选中位置
                province = options1Items.get(options1).codeid;

                if (options2Items.get(options1) != null) {
                    city = options2Items.get(options1).get(options2).codeid;
                    if (options3Items.get(options1) != null) {
                        if (options3Items.get(options1).size() > options2) {
                            if (options3Items.get(options1).get(options2) != null) {
                                if (options3Items.get(options1).get(options2).get(options3) != null) {
                                    country = options3Items.get(options1).get(options2).get(options3).codeid;
                                    provinceName = options1Items.get(options1).cityName;
                                    cityName = options2Items.get(options1).get(options2).cityName;
                                    countryName = options3Items.get(options1).get(options2).get(options3).cityName;

                                    option2 = options2;
                                    option3 = options3;

                                }
                            }
                        }

                    }

                }


//                tvChooseAddress.setText(provinceName + cityName + countryName);

//                changeLoginButtonStatus(etName, etPhone, etAddress);
            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("请选择地址")//标题
                .setSubCalSize(17)//确定和取消文字大小
                .setTitleSize(17)//标题文字大小
                .setTitleColor(0xff333333)//标题文字颜色
                .setSubmitColor(0xff4d72f3)//确定按钮文字颜色
                .setCancelColor(0xff4d72f3)//取消按钮文字颜色
                .setTitleBgColor(0xffffffff)//标题背景颜色 Night mode
                .setBgColor(0xffffffff)//滚轮背景颜色 Night mode
                .setContentTextSize(17)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否

                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

        if (options3Items != null && options3Items.size() > 0) {    //根据三级菜单判断有数据就展示选择器
            pvOptions.setPicker(options1Items, options2Items, options3Items);
            pvOptions.setSelectOptions(option1, option2, option3);  //设置默认选中项
            pvOptions.show();
        }
    }


}

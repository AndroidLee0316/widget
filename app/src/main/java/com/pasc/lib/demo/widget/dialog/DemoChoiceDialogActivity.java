package com.pasc.lib.demo.widget.dialog;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnMultiChoiceListener;
import com.pasc.lib.widget.dialog.OnSingleChoiceListener;
import com.pasc.lib.widget.dialog.common.ChoiceDialogFragment;
import com.pasc.lib.widget.dialog.common.ConfirmDialogFragment;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Dialogs/ChoiceDialogFragment")
public class DemoChoiceDialogActivity extends AppCompatActivity {

    private Button mListDialog,mLsitSingleChoiceDialog,mListMultiChoiceDialog;

    private int currentSelectPosition = -1;
    private  List<Integer> positionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_common_list_dialg);
        initView();
    }

    private void initView(){

        mListDialog = findViewById(R.id.lsit_dialog);

        mLsitSingleChoiceDialog = findViewById(R.id.lsit_single_choice_dialog);

        mListMultiChoiceDialog = findViewById(R.id.list_multi_choice_Dialog);

        //添加数据
        final ArrayList<CharSequence> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i ==1 ||i == 2){
             list.add("多行文字多行文字多行文字多行文字多行文字多行文字");
            }else {
                list.add("一行文字一行文字一行文字");
            }

        }

        positionList.add(0);
        positionList.add(1);

        mListDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChoiceDialogFragment commonDialog = new ChoiceDialogFragment.Builder()
                        .setTitle("请选择办理情形哦")
                        .setTitleColor(getResources().getColor(R.color.choice_dialog_title_color))
                        .setTitleSize(18)
                        .setCancelable(true)
                        .setSingletItems(list,currentSelectPosition)
                        .setItemTextColor(getResources().getColor(R.color.choice_dialog_title_color))
                        .setItemTextSize(15)
                        .setButtonText("确定")
                        .setButtonColor(getResources().getColor(R.color.white))
                        .setButtonSize(17)
                        .setOnSingleChoiceListener(new OnSingleChoiceListener<ChoiceDialogFragment>() {
                            @Override
                            public void onSingleChoice(ChoiceDialogFragment dialogFragment, int position) {
                                currentSelectPosition = position;
                                Toast.makeText(DemoChoiceDialogActivity.this,"id="+position,Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();
//                commonDialog.show(getFragmentManager(), "ChoiceDialogFragment");
                commonDialog.show(DemoChoiceDialogActivity.this, "df");


            }
        });

        mLsitSingleChoiceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ChoiceDialogFragment commonDialog = new ChoiceDialogFragment.Builder()
                        .setSingletItems(list, currentSelectPosition)
                        .setOnSingleChoiceListener(new OnSingleChoiceListener<ChoiceDialogFragment>() {
                            @Override
                            public void onSingleChoice(ChoiceDialogFragment dialogFragment, int position) {
                                currentSelectPosition = position;
                                Toast.makeText(DemoChoiceDialogActivity.this, "id=" + position, Toast.LENGTH_SHORT).show();
                                dialogFragment.dismiss();
                            }
                        })
                        .build();

                commonDialog.show(getSupportFragmentManager(), "ChoiceDialogFragment");


            }
        });
        mListMultiChoiceDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final ChoiceDialogFragment commonDialog = new ChoiceDialogFragment.Builder()
//                        .setMultitItems(list,postionList)
//                        .setOnMultiChoiceListener(new OnMultiChoiceListener<ChoiceDialogFragment>() {
//                            @Override
//                            public void onMultiChoice(ChoiceDialogFragment dialogFragment, List<Integer> positionList) {
//                                postionList = new ArrayList<>();
//
//                                dialogFragment.dismiss();
//                                Toast.makeText(DemoChoiceDialogActivity.this,"fuxuankuang",Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .build();
//
//                commonDialog.show(getSupportFragmentManager(), "ChoiceDialogFragment");
            }
        });

    }
    int yourChoice;
    private void showSingleChoiceDialog(){
        final String[] items = { "我是1","我是2","我是3","我是4" };
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(DemoChoiceDialogActivity.this);
        singleChoiceDialog.setTitle("我是一个单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            Toast.makeText(DemoChoiceDialogActivity.this,
                                    "你选择了" + items[yourChoice],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

//    private void showListDialog() {
//        final String[] items = { "我是1","我是2","我是3","我是4" };
//        AlertDialog.Builder listDialog =
//                new AlertDialog.Builder(MainActivity.this);
//        listDialog.setTitle("我是一个列表Dialog");
//        listDialog.setItems(items, new DialogFragmentInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogFragmentInterface dialog, int which) {
//                // which 下标从0开始
//                // ...To-do
//                Toast.makeText(MainActivity.this,
//                        "你点击了" + items[which],
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        listDialog.show();
//    }
//ArrayList<Integer> yourChoices = new ArrayList<>();
//    private void showMultiChoiceDialog() {
//        final String[] items = { "我是1","我是2","我是3","我是4" };
//        // 设置默认选中的选项，全为false默认均未选中
//        final boolean initChoiceSets[]={false,false,false,false};
//        yourChoices.clear();
//        AlertDialog.Builder multiChoiceDialog =
//                new AlertDialog.Builder(MainActivity.this);
//        multiChoiceDialog.setTitle("我是一个多选Dialog");
//        multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets,
//                new DialogFragmentInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogFragmentInterface dialog, int which,
//                                        boolean isChecked) {
//                        if (isChecked) {
//                            yourChoices.add(which);
//                        } else {
//                            yourChoices.remove(which);
//                        }
//                    }
//                });
//        multiChoiceDialog.setPositiveButton("确定",
//                new DialogFragmentInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogFragmentInterface dialog, int which) {
//                        int size = yourChoices.size();
//                        String str = "";
//                        for (int i = 0; i < size; i++) {
//                            str += items[yourChoices.get(i)] + " ";
//                        }
//                        Toast.makeText(MainActivity.this,
//                                "你选中了" + str,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        multiChoiceDialog.show();
//    }

}
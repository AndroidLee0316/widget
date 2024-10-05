package com.pasc.lib.demo.widget.catalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.pasc.lib.demo.R;
import com.pasc.lib.widget.catalog.CatalogSelect;
import com.pasc.lib.widget.catalog.bean.CatalogDataBean;
import com.pasc.lib.widget.catalog.bean.SubCatalogDataBean;

import java.util.ArrayList;

public class DemoCatalogSelectionDialogActivity extends AppCompatActivity {


    private CatalogSelect directorySelect;
    private int singlePosition = 0;

    private int mOneLevelPosition = 0;
    private int mSubTwoLevelPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_directory_selector_dialog);
        directorySelect = findViewById(R.id.directorySelect_id);


        final ArrayList<CatalogDataBean> DirectoryList = new ArrayList<>();
        ArrayList<SubCatalogDataBean> subList = new ArrayList<>();
        SubCatalogDataBean subCatalogDataBean;

        CatalogDataBean directoryDataBean = new CatalogDataBean();
        directoryDataBean.setDirectoryID("1");
        directoryDataBean.setTitle("手机数码");

        subList = new ArrayList<>();
        subCatalogDataBean =new SubCatalogDataBean("小米");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("三星");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("魅族手机");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("OPPO");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("ViVO");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("HUAWEI");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("一加手机");
        subList.add(subCatalogDataBean);
        directoryDataBean.setSubList(subList);
        DirectoryList.add(directoryDataBean);


        directoryDataBean = new CatalogDataBean();
        directoryDataBean.setDirectoryID("2");
        directoryDataBean.setTitle("电脑办公");

        subList = new ArrayList<>();
        subCatalogDataBean =new SubCatalogDataBean("游戏本");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("轻薄本");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("机械键盘");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("移动硬盘");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("显卡");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("游戏台式机");
        subList.add(subCatalogDataBean);
        directoryDataBean.setSubList(subList);
        DirectoryList.add(directoryDataBean);
        directoryDataBean = new CatalogDataBean();
        directoryDataBean.setDirectoryID("3");
        directoryDataBean.setTitle("家用电器");

        subList = new ArrayList<>();
        subCatalogDataBean =new SubCatalogDataBean("厨房小电");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("电视");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("空调");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("洗衣机");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("冰箱");
        subList.add(subCatalogDataBean);
        directoryDataBean.setSubList(subList);

        DirectoryList.add(directoryDataBean);

        directoryDataBean = new CatalogDataBean();
        directoryDataBean.setDirectoryID("4");
        directoryDataBean.setTitle("食品");

        subList = new ArrayList<>();
        subCatalogDataBean =new SubCatalogDataBean("水果");
        subList.add(subCatalogDataBean);
        subCatalogDataBean =new SubCatalogDataBean("特产");
        subList.add(subCatalogDataBean);

        directoryDataBean.setSubList(subList);
        DirectoryList.add(directoryDataBean);


        //添加数据
        final ArrayList<CharSequence> singleList=new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            singleList.add("标题" + i);


        }


        // 只有一级目录
        findViewById(R.id.top_ategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           directorySelect.setSingleData(singleList, singlePosition, new CatalogSelect.OnCatalogSingleChangedListener() {
               @Override
               public void onItemChanged(int position) {
                   singlePosition = position;
                   Toast.makeText(DemoCatalogSelectionDialogActivity.this,position+"",Toast.LENGTH_SHORT).show();
               }
           });
            }
        });
        // 两级目录
        findViewById(R.id.secondary_classification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directorySelect.setMultiData(DirectoryList, mOneLevelPosition, mSubTwoLevelPosition, new CatalogSelect.OnCatalogMultiChangedListener() {
                    @Override
                    public void onItemChanged(int oneLevelPosition, int twoLevelPosition) {
                        mOneLevelPosition = oneLevelPosition;
                        mSubTwoLevelPosition = twoLevelPosition;

                        Toast.makeText(DemoCatalogSelectionDialogActivity.this,"根目录position"+oneLevelPosition +"子目录position"+twoLevelPosition,Toast.LENGTH_SHORT).show();
                }
            });

            }
        });




    }
}

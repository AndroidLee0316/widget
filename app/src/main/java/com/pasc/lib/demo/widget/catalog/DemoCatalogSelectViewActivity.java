package com.pasc.lib.demo.widget.catalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.catalog.PascCatalogSelectView;
import com.pasc.lib.widget.catalog.bean.MultiBean;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Containers/CatalogSelectView")
public class DemoCatalogSelectViewActivity extends AppCompatActivity {
    private PascCatalogSelectView mPascCatalogSelectView;

    private List<CharSequence> mSingleList = new ArrayList<>();
    private List<MultiBean> mMultiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_catalog_select_view);
        mPascCatalogSelectView = findViewById(R.id.PascCatalogSelectView);
        buildData();
    }

    public void onBtn1Click(View view) {
        mPascCatalogSelectView.setSingleData(mSingleList, 3, new PascCatalogSelectView.OnSingleClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(DemoCatalogSelectViewActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBtn2Click(View view) {
        mPascCatalogSelectView.setMultiData(mMultiList, 2, new PascCatalogSelectView.OnMultiClickListener() {
            @Override
            public void onMultiClick(int leftPosition, int rightPosition) {
                Toast.makeText(DemoCatalogSelectViewActivity.this, "点击了：leftPosition = " + leftPosition + " , rightPosition = " + rightPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftItemClick(int position) {
                Toast.makeText(DemoCatalogSelectViewActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildData() {
        List<CharSequence> rightList = new ArrayList<>();
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");
        rightList.add("街道名称");

        for (int i = 0; i < 8; i++) {
            MultiBean multiBean;
            if (i == 0) {
                multiBean = new MultiBean("福田区", rightList);
            } else if (i == 1) {
                multiBean = new MultiBean("南山区", rightList);
            } else if (i == 2) {
                multiBean = new MultiBean("罗湖区", rightList);
            } else if (i == 3) {
                multiBean = new MultiBean("宝安区", rightList);
            } else if (i == 4) {
                multiBean = new MultiBean("大鹏新区", rightList);
            } else if (i == 5) {
                multiBean = new MultiBean("龙华区", rightList);
            } else if (i == 6) {
                multiBean = new MultiBean("光明区", rightList);
            } else {
                multiBean = new MultiBean("龙岗区", rightList);
            }
            mMultiList.add(multiBean);
            mSingleList.add(multiBean.getLeftName());
        }
    }
}

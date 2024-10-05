package com.pasc.lib.demo.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.defaultpage.DefaultLayoutType;
import com.pasc.lib.widget.defaultpage.DefaultPageView;
import com.pasc.lib.widget.ICallBack;
import com.pasc.lib.widget.flowlayout.FlowLayout;
import com.pasc.lib.widget.flowlayout.TagAdapter;
import com.pasc.lib.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Containers/DefaultPageView")
public class DemoDefaultPageActivity extends AppCompatActivity {
    private DefaultPageView defaultPageView;
    private TagFlowLayout tagFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_default_page);
        defaultPageView = findViewById(R.id.default_page_view);
        defaultPageView.setDefaultLayout(DefaultLayoutType.SERVER_ERROR);
        tagFlowLayout = findViewById(R.id.flow);
//        final List<String> data= new ArrayList<>();
//        data.add("无数据");
//        data.add("无地址");
//        data.add("无消息");
//        data.add("无网络");
//        data.add("无搜索结果");
//        data.add("服务器报错");
//        data.add("服务器维护");

//        tagFlowLayout.setAdapter(new TagAdapter(data) {
//            @Override
//            public View getView(FlowLayout parent, int position, Object o) {
//                View tag = View.inflate(DemoDefaultPageActivity.this, R.layout.item_flow_tag, null);
//                ((TextView) tag.findViewById(R.id.tv_tag)).setText((CharSequence) o);
//                return tag;
//            }
//        });
//        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                setDefaultPageView(position);
//                return false;
//            }
//        });
        defaultPageView.setPrimaryOnClickListener(new ICallBack() {
            @Override
            public void callBack() {
                Toast.makeText(DemoDefaultPageActivity.this,"按钮1点击事件",Toast.LENGTH_SHORT).show();
            }
        });
        defaultPageView.setSecondaryOnClickListener(new ICallBack() {
            @Override
            public void callBack() {
                Toast.makeText(DemoDefaultPageActivity.this,"按钮2点击事件",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void setDefaultPageView(int position){
//
//        if(position == 0){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.NO_DATA);
//        }else if(position == 1){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.NO_ADDRESS);
//        }else if(position == 2){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.NO_MESSAGE);
//        }else if(position == 3){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.NO_NETWORK);
//        }else if(position == 4){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.NO_SEARCH_RESULT);
//        }else if(position == 5){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.SERVER_ERROR);
//        }else if(position == 6){
//            defaultPageView.setDefaultLayout(DefaultLayoutType.SERVER_MAINTAIN);
//        }
//
//    }
    
}

package com.pasc.lib.demo.widget.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.flowlayout.FlowLayout;
import com.pasc.lib.widget.flowlayout.TagAdapter;
import com.pasc.lib.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Route(path = "/Demo/Containers/TagFlowLayout")
public class DemoFlowLayoutActivity extends AppCompatActivity {
    private TagFlowLayout tagFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_flow_layout_view);
        tagFlowLayout = findViewById(R.id.tag_flow);
        final List<String> data= new ArrayList<>();
        data.add("宫保鸡丁");
        data.add("番茄炒蛋");
        data.add("酸辣鸡杂");
        data.add("酸辣土豆丝");
        data.add("茄子豆角");
        data.add("空心菜");
        data.add("四季豆炒辣椒");

        tagFlowLayout.setAdapter(new TagAdapter(data) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View tag = View.inflate(DemoFlowLayoutActivity.this, R.layout.item_flow_tag, null);
                ((TextView) tag.findViewById(R.id.tv_tag)).setText((CharSequence) o);
                return tag;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(DemoFlowLayoutActivity.this,"选择:"+data.get(position),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Toast.makeText(DemoFlowLayoutActivity.this,"选择:"+selectPosSet.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

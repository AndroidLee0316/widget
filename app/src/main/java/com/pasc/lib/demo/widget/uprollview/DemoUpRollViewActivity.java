package com.pasc.lib.demo.widget.uprollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.PascUpRollView;
import com.pasc.lib.widget.UpRollView;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Containers/PascUpRollView")
public class DemoUpRollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_up_roll_view);

        PascUpRollView upRollView = findViewById(R.id.upRollView);
        upRollView.setRollDuration(1000);
        upRollView.setRollInterval(2000);

        ArrayList<List<String>> dataArr = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<String> item = new ArrayList<String>();
            int randomCount = (int) (Math.random() * 3) + 1;
            for (int j = 0; j < randomCount; j++) {
                item.add("测试文本" + i + "" + j);
            }
            dataArr.add(item);
        }
        PascUpRollView.MultiLineTextAdapter adapter = new PascUpRollView.MultiLineTextAdapter(this, dataArr);
        adapter.setLineCount(2);
        adapter.setLineGap(30);
        adapter.setLineLayoutId(com.pasc.lib.widget.R.layout.pasc_up_roll_view_line_text_view);
        upRollView.setAdapter(adapter);
        upRollView.setOnItemClickListener(new PascUpRollView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(DemoUpRollViewActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        upRollView.startAutoScroll();
    }
}

package com.pasc.lib.demo.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.FaceCircleProcessView;

@Route(path = "/Demo/Widgets/FaceCircleProcessView")
public class DemoFaceCircleProcessViewActivity extends AppCompatActivity {

  private FaceCircleProcessView faceCircleProcessView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_face_circle);
        faceCircleProcessView = findViewById(R.id.cpv_face);
        faceCircleProcessView.setProgress(80);
        faceCircleProcessView.setCenterColor("#1927adff");


    }
}

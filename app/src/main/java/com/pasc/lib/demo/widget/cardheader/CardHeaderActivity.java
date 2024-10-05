package com.pasc.lib.demo.widget.cardheader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;

@Route(path = "/Demo/Containers/PaCardHeaderView")
public class CardHeaderActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_card_header);
  }
}

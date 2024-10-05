package com.pasc.lib.demo.widget.input;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.input.InputEditTextView;
@Route(path = "/Demo/Widgets/InputEditTextView")
public class DemoTextInputActivity extends AppCompatActivity{
    private InputEditTextView inputEditTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_text_input);
        inputEditTextView = findViewById(R.id.edit_text_with_del);
        inputEditTextView.setOnDeleteListener(new InputEditTextView.OnDeleteListener() {
            @Override
            public void delete() {
                Toast.makeText(DemoTextInputActivity.this,"delete",Toast.LENGTH_SHORT).show();
            }
        });


    }
}

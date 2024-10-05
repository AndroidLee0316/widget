package com.pasc.lib.demo.widget.popup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.popup.OnSingleChoiceListener;
import com.pasc.lib.widget.popup.PascListPopupWindow;
import com.pasc.lib.widget.popup.PascSelectPopupWindow;

import java.util.ArrayList;
import java.util.List;
public class DemoPascListPopupWindowActivity extends AppCompatActivity {
 private PopupListAdapter popupListAdapter;
 private PascSelectPopupWindow pascSelectPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_pasc_list_popup_window);

        final ArrayList<CharSequence> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i ==1 ||i == 2){
                list.add("多行文字多行文字多行文字多行文字多行文字多行文字");
            }else {
                list.add("一行文字一行文字一行文字");
            }

        }
        popupListAdapter = new PopupListAdapter(this,list);
        popupListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Toast.makeText(DemoPascListPopupWindowActivity.this, "position=" + position, Toast.LENGTH_LONG).show();

                if(pascSelectPopupWindow != null){
                    pascSelectPopupWindow.dismiss();
                }
            }
        });

        findViewById(R.id.defaultButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<CharSequence> items = new ArrayList<CharSequence>();
                items.add("居中文本");
                items.add("这是示例超长文本");
                items.add("示例文本");
                items.add("示例文本");
                items.add("示例文本");
                items.add("示例文本");
                items.add("示例文本");
                items.add("示例文本");

                new PascListPopupWindow.Builder(DemoPascListPopupWindowActivity.this)
                        .setItems(items)
                        .setOnSingleChoiceListener(new OnSingleChoiceListener<PascListPopupWindow>() {
                            @Override
                            public void onSingleChoice(PascListPopupWindow popupWindow, int position) {
                                Toast.makeText(DemoPascListPopupWindowActivity.this, "position=" + position, Toast.LENGTH_LONG).show();
                            }
                        })
                        .build()
                        .showAsDropDown(v);
            }
        });

        findViewById(R.id.button_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               pascSelectPopupWindow = new PascSelectPopupWindow(DemoPascListPopupWindowActivity.this,
                        ViewGroup.LayoutParams.MATCH_PARENT,popupListAdapter,false);
                pascSelectPopupWindow.showCoverView();
                pascSelectPopupWindow.showAsDropDown(v);
            }
        });
    }
}

package com.pasc.lib.demo.widget.popup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.popup.PascListPopup;
import com.pasc.lib.widget.popup.PascPopup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

@Route(path = "/Demo/Others/PascPopup")
public class DemoPascPopupWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_pasc_popup_window);

        Button listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, buildDataList());
                PascListPopup.ListPopupBuilder builder = new PascListPopup.ListPopupBuilder(DemoPascPopupWindowActivity.this);
                final PascPopup pascPopup = builder.setAnimStyle(PascPopup.ANIM_GROW_FROM_CENTER)
                        .setPreferredDirection(PascPopup.DIRECTION_BOTTOM)
                        .setListAdapter(adapter)
                        .setListWidth(DensityUtils.dp2px(140))
                        .setListMaxHeight(DensityUtils.dp2px(180))
                        .setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                listButton.setText("显示列表浮层");
                            }
                        })
                        .create();
                pascPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pascPopup.dismiss();
                        Toast.makeText(getActivity(), "Item" + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                });
                pascPopup.show(v);
                listButton.setText("隐藏列表浮层");
            }
        });


        Button normalButton = findViewById(R.id.defaultButton);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PascPopup.PopupBuilder builder = new PascPopup.PopupBuilder(getActivity());
                builder.setContentView(buildTextView())
                        .setAnimStyle(PascPopup.ANIM_GROW_FROM_CENTER)
                        .setPreferredDirection(PascPopup.DIRECTION_TOP)
                        .setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                normalButton.setText("显示普通浮层");
                            }
                        })
                        .create()
                        .show(v);
                normalButton.setText("隐藏普通浮层");

            }
        });
    }

    private List<String> buildDataList() {
        String[] listItems = new String[]{
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);
        return data;
    }

    private Activity getActivity() {
        return this;
    }


    @NonNull
    private TextView buildTextView() {
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(DensityUtils.dip2px(this, 250), ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setLineSpacing(DensityUtils.dp2px(4), 1.0f);
        int padding = DensityUtils.dp2px(20);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("PascPopup 可以设置其位置以及显示和隐藏的动画");
        textView.setTextColor(Color.parseColor("#333333"));
        return textView;
    }
}

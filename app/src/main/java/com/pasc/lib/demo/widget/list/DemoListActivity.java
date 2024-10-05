package com.pasc.lib.demo.widget.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.list.PaExpandableListView;
import com.pasc.lib.widget.list.PaRecyclerView;
import com.pasc.lib.widget.list.bean.MultiIem;
import com.pasc.lib.widget.list.bean.MultiItemSub;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Containers/PaRecyclerView")
public class DemoListActivity extends AppCompatActivity {
    private PaRecyclerView paRecyclerView;
    private PaExpandableListView expandableListView;
    private List<String> groupList;
    private List<List<String>> childList;

    String TAG = "asfa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);

        paRecyclerView = findViewById(R.id.pa_list);

        paRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "--------------------------------------");
                if(!paRecyclerView.canScrollVertically(1)){
                    Log.i(TAG, "direction 1: true");
                }
            }
        });

        paRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int ii = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int is = 0;
                        break;
                    case MotionEvent.ACTION_UP:
                        int sd = 0;
                    case MotionEvent.ACTION_CANCEL:
                        int ds = 0;

                }


                return false;
            }
        });
        expandableListView = findViewById(R.id.expand_list);
        //添加数据
        final ArrayList<CharSequence> singleList=new ArrayList<>();
        for (int i = 0; i < 35; i++) {

            singleList.add("标题文字标题文字标题文字标题文字标题文");

        }

        final ArrayList<MultiIem> multiList2 = new ArrayList<>();

        for (int i = 0; i < 35; i++) {
            MultiIem multiIem =new MultiIem();
            multiIem.setTitle("平安金融中心地下停车场"+i);
            MultiItemSub multiItemSub1 = new MultiItemSub();
            multiItemSub1.setLeftText("305m"+i);
            multiItemSub1.setRightText("深圳市福田区福华路399号"+i);

            multiIem.setItemSub1(multiItemSub1);
            multiList2.add(multiIem);
        }

        final ArrayList<MultiIem> multiList3 = new ArrayList<>();

        for (int i = 0; i < 35; i++) {
            MultiIem multiIem =new MultiIem();
            multiIem.setTitle("平安金融中心地下停车场"+i);
            MultiItemSub multiItemSub1 = new MultiItemSub();
            multiItemSub1.setLeftText("305m"+i);
            multiItemSub1.setRightText("深圳市福田区福华路399号"+i);

            MultiItemSub multiItemSub2 = new MultiItemSub();
            multiItemSub2.setLeftText("剩余车位12个"+i);
            multiItemSub2.setRightText("首小时3元"+i);

            multiIem.setItemSub1(multiItemSub1);
            multiIem.setItemSub2(multiItemSub2);
            multiList3.add(multiIem);
        }


        findViewById(R.id.list_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paRecyclerView.setVisibility(View.VISIBLE);
                expandableListView.setVisibility(View.GONE);
                paRecyclerView.setSingleData(singleList, 0, new PaRecyclerView.OnCatalogChangedListener() {
                    @Override
                    public void onItemChanged(int position) {
                        Toast.makeText(DemoListActivity.this,position+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.list_expand_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paRecyclerView.setVisibility(View.GONE);
                expandableListView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.list_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paRecyclerView.setVisibility(View.VISIBLE);
                expandableListView.setVisibility(View.GONE);
                paRecyclerView.setMultiData(multiList2, new PaRecyclerView.OnCatalogChangedListener() {
                    @Override
                    public void onItemChanged(int position) {
                        Toast.makeText(DemoListActivity.this,position+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.list_item3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paRecyclerView.setVisibility(View.VISIBLE);
                expandableListView.setVisibility(View.GONE);
                paRecyclerView.setMultiData(multiList3, new PaRecyclerView.OnCatalogChangedListener() {
                    @Override
                    public void onItemChanged(int position) {
                        Toast.makeText(DemoListActivity.this,position+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        addData("标题文字标题文字",new String[]{"内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容","内容文字内容文字内容文字内容文字内容文字内容文字内容文字内容"});
        expandableListView.setExpandableData(groupList, childList, new PaExpandableListView.OnGroupExpandedListener() {
            @Override
            public void onGroupExpanded(int groupPosition,int childPosition) {
                Toast.makeText(DemoListActivity.this,"item:"+groupPosition+"sub:"+childPosition,Toast.LENGTH_SHORT).show();
            }
        });





    }
    /**
     * 用来添加数据的方法
     */
    private void addData(String group, String[] friend) {
        groupList.add(group);
        //每一个item打开又是一个不同的list集合
        List<String> childItem = new ArrayList<>();
        for (int i = 0; i < friend.length; i++) {
            childItem.add(friend[i]);
        }
        childList.add(childItem);
    }

}

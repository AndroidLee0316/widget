package com.pasc.lib.demo.widget.swiperefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.demo.widget.swiperefresh.adapter.RecyclerAdapter;
import com.pasc.lib.widget.swiperefresh.PaSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/Demo/Widgets/PaSwipeRefreshLayout")
public class DemoSwipeRefreshActivity extends AppCompatActivity {

    private PaSwipeRefreshLayout swipeRefreshLayout;
//    private  SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_swipe_refresh);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(myAdapter);
        initDatas();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });



        final View childTips = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.swipe_head_tips, null);
//        progressBar =  child.findViewById(R.id.pb_view);
//        textView =  child.findViewById(R.id.text_view);
//        textView.setText("下拉刷新1");
//        imageView =  child.findViewById(R.id.image_view);
//        imageView.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.VISIBLE);
//        swipeRefreshLayout.setHeaderView(child);
//        swipeRefreshLayout.setHeaderImg(R.drawable.ic_loading_white_gray);
//        swipeRefreshLayout.setPullRefreshEnable(true);
//        swipeRefreshLayout.setPushLoadMoreEnable(true);
        swipeRefreshLayout.setOnPushLoadMoreListener(new PaSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setLoadMore(false);
//
                    }
                }, 2000);
            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
        swipeRefreshLayout.setOnPullRefreshListener(new PaSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
//                textView.setText("正在刷新");
//                imageView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setTips(childTips);
//                        swipeRefreshLayout.setRefreshing(false);
//                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onPullEnable(boolean enable) {
//                textView.setText(enable ? "松开刷新" : "下拉刷新");
//                imageView.setVisibility(View.VISIBLE);
//                imageView.setRotation(enable ? 180 : 0);
            }
        });

    }

    private void initDatas() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            list.add("列表数据 " + i);
        }
        myAdapter.addAll(list, 0);
    }

}

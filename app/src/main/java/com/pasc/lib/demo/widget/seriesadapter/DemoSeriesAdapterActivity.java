package com.pasc.lib.demo.widget.seriesadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pasc.lib.demo.R;
import com.pasc.lib.widget.seriesadapter.base.ISeriesPresenter;
import com.pasc.lib.widget.seriesadapter.base.ItemModel;
import com.pasc.lib.widget.seriesadapter.base.SeriesAdapter;
import com.pasc.lib.widget.seriesadapter.base.SeriesPresenter;

import java.util.ArrayList;

public class DemoSeriesAdapterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeriesAdapter siteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_serie);
        recyclerView = findViewById(R.id.recycler_view);
        ArrayList<ItemModel> titleModels =new ArrayList<>();
        titleModels.add(new TitleModel("模板1"));
        titleModels.add(new TitleModel("模板2"));
        titleModels.add(new TitleModel("模板3"));
        titleModels.add(new TitleModel("模板4"));
        titleModels.add(new TitleModel("模板5"));


        ISeriesPresenter adapterPresenter =
                new SeriesPresenter.Builder()
                        .addWorker(new TitleModel.TitleWorker())
                        .build();
        siteAdapter = new SeriesAdapter(adapterPresenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(siteAdapter);
        siteAdapter.getItemModels().clear();
        siteAdapter.getItemModels().addAll(titleModels);
        siteAdapter.notifyDataSetChanged();

    }
}

package com.pasc.lib.demo.widget.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.toolbar.PascSearchBar;
@Route(path = "/Demo/Containers/PascSearchBar")
public class SearchBarActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_bar);

    final TextView resultView = findViewById(R.id.result_view);
    PascSearchBar searchBarNotEdit = findViewById(R.id.search_bar_edit_not);

    PascSearchBar searchBar = findViewById(R.id.search_bar);
    searchBar.setCloseListener(new PascSearchBar.OnCloseListener() {
      @Override public void onClose(View closeButton) {
        Toast.makeText(SearchBarActivity.this, "关闭", Toast.LENGTH_SHORT).show();
      }
    });
    searchBar.setQueryTextListener(new PascSearchBar.OnQueryTextListener() {
      @Override public void onQueryTextChange(String newText) {
        resultView.setText(newText);
      }
    });
    searchBar.setSearchClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(SearchBarActivity.this, "点击搜索按钮", Toast.LENGTH_SHORT).show();
      }
    });
    searchBarNotEdit.setCenterSearchClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(SearchBarActivity.this, "跳转搜索界面", Toast.LENGTH_SHORT).show();
      }
    });

  }
}

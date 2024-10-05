package com.pasc.lib.demo.widget.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.dialog.OnCategoryItemSelectListener;
import com.pasc.lib.widget.dialog.OnConfirmListener;
import com.pasc.lib.widget.dialog.OnSelectedCategoryClickListener;
import com.pasc.lib.widget.dialog.categoryselection.CategorySelectionDialogFragment;
import java.util.ArrayList;

@Route(path = "/Demo/Dialogs/CategorySelectionDialogFragment")
public class DemoCategorySelectionDialogActivity extends AppCompatActivity {

    private CategorySelectionDialogFragment categorySelectionDialogFragment;
    ArrayList<Item> items1;
    ArrayList<Item> items2;
    ArrayList<Item> items3;
    ArrayList<Item> curItems;



    public static class Item implements CategorySelectionDialogFragment.ICategoryItem{

        private CharSequence categoryName;

        public Item(CharSequence categoryName) {
            this.categoryName = categoryName;
        }
        @Override
        public CharSequence getCategoryName() {
            return categoryName;
        }
    }

    Button categoryButton;
    ArrayList<CharSequence> items;
    StringBuffer stringBuffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_category_selection_dialog);
        categoryButton = findViewById(R.id.defaultButton);

        items1 = new ArrayList<Item>();
        items1.add(new Item("农村农业"));
        items1.add(new Item("国土资源"));
        items1.add(new Item("城乡建设"));
        items1.add(new Item("劳动和社会保障"));
        items1.add(new Item("卫生计生"));
        items1.add(new Item("教育问题"));
        items1.add(new Item("民政"));
        items1.add(new Item("政法"));
        items1.add(new Item("经济管理"));

        items2 = new ArrayList<Item>();
        items2.add(new Item("村务管理"));
        items2.add(new Item("土地承包经营"));
        items2.add(new Item("扶贫开发"));
        items2.add(new Item("国有土地上房屋征收与补偿"));
        items2.add(new Item("集体土地上房屋拆迁与补偿"));
        items2.add(new Item("城镇职工社会保险"));
        items2.add(new Item("城镇居民社会保险"));

        items3 = new ArrayList<Item>();
        items3.add(new Item("集体资产管理"));
        items3.add(new Item("集体财务公开"));
        items3.add(new Item("村务公开"));
        items3.add(new Item("村级债务"));
        items3.add(new Item("土地承包"));
        items3.add(new Item("土地流转"));
        items3.add(new Item("扶贫开发资金使用管理"));

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items != null ){
                    if(items.size() != 0){
                        curItems =  items3;
                    }
                }else {
                    curItems =  items1;
                }
              categorySelectionDialogFragment =  new CategorySelectionDialogFragment.Builder()
                        .setTitle("请选择问题分类")
                        .setCategoryList(items1)
                        .setCategoryList(curItems)
                        .setSelectedCategoryItems(items)
                        .setOnCategoryItemSelectListener(new OnCategoryItemSelectListener<CategorySelectionDialogFragment>() {
                          @Override
                          public void onCategoryItemSelect(CategorySelectionDialogFragment dialogFragment, int position) {
                             if(dialogFragment.getCurLevel() == 1){
                                  categorySelectionDialogFragment.setCategoryList(items2);
                              }else if(dialogFragment.getCurLevel() == 2){
                                  categorySelectionDialogFragment.setCategoryList(items3);
                              }

                          }
                      })
                      .setOnSelectedCategoryClickListener(new OnSelectedCategoryClickListener<CategorySelectionDialogFragment>() {
                          @Override
                          public void onSelectedCategory(CategorySelectionDialogFragment dialogFragment, int position) {
                              if(position == 0){
                                  categorySelectionDialogFragment.setCategoryList(items1);
                              }else if(position == 1){
                                  categorySelectionDialogFragment.setCategoryList(items2);
                              }else if(position == 2){
                                  categorySelectionDialogFragment.setCategoryList(items3);
                              }

                          }
                      })
                        .setOnConfirmListener(new OnConfirmListener<CategorySelectionDialogFragment>() {
                            @Override
                            public void onConfirm(CategorySelectionDialogFragment dialogFragment) {
                                items =dialogFragment.getSelectedCategoryItems();
                                int itemsSize = items.size();
                                stringBuffer = new StringBuffer();
                               for (int i=0;i<itemsSize;i++){
                                   if(i != itemsSize-1){
                                       stringBuffer.append(items.get(i)+"-");
                                   }else {
                                       stringBuffer.append(items.get(i));
                                   }
                               }
                                dialogFragment.dismiss();

                                categoryButton.setText(stringBuffer.toString());

                            }
                        })
                        .build();
//                categorySelectionDialogFragment.setOnCategoryItemSelectListener( );
                categorySelectionDialogFragment.show(getSupportFragmentManager(), "categorySelectionDialogFragment");
            }
        });
    }

//    @Override
//    public void getCategoryName(int curLevel,int position) {
//        if( curLevel ==0 ){
//            categorySelectionDialogFragment.setCategoryList(items1);
//        } else if(curLevel ==1 ){
//            categorySelectionDialogFragment.setCategoryList(items2);
//        }else if(curLevel ==2){
//            categorySelectionDialogFragment.setCategoryList(items3);
//        }
//    }
}
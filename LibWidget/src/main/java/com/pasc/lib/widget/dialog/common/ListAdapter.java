package com.pasc.lib.widget.dialog.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pasc.lib.widget.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    List<String> list;
    Context context;
    int type = 0;
  public ListAdapter(List<String> mList,Context mContext,int mType){
      this.list = mList;
      this.context =mContext;
      type =mType;

  }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dialog_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RadioButton radioButton;
        CheckBox checkId;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_title);
            checkId =  itemView.findViewById(R.id.check_id);
        }
    }
}

package com.pasc.lib.demo.widget.swiperefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pasc.lib.demo.R;
import com.pasc.lib.demo.widget.swiperefresh.viewholder.BaseViewHolder;
import com.pasc.lib.demo.widget.swiperefresh.viewholder.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {


	private Context mContext;
	private List<String> mDataSet;

	public ListAdapter(Context context,List<String> data) {
		mContext = context;
		mDataSet = data;
	}


	@Override
	public int getCount() {
		return mDataSet.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataSet.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_child, null);
		TextView tv = view.findViewById(R.id.text);
		tv.setText(mDataSet.get(position));
		return view;
	}

	public void setDataSet(List<String> mDataSet) {
		this.mDataSet = mDataSet;
		notifyDataSetChanged();
	}
}

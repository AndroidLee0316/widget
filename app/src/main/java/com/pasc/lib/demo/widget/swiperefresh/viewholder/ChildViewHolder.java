package com.pasc.lib.demo.widget.swiperefresh.viewholder;
import android.view.View;
import android.widget.TextView;

import com.pasc.lib.demo.R;

public class ChildViewHolder extends BaseViewHolder {

	public TextView text;

	public ChildViewHolder(View itemView) {
		super(itemView);
		text = (TextView) itemView.findViewById(R.id.text);
	}

	public void bindView(String str, int position) {
		text.setText(str);
	}

}

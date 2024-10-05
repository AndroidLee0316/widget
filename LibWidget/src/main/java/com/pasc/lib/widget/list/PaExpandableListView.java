package com.pasc.lib.widget.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasc.lib.widget.R;

import java.util.List;

public class PaExpandableListView extends ExpandableListView {

    private Context mContext;

    private List<String> mGroupList;
    private List<List<String>> mChildList;

    private int mGroupSize;
    private int mGroupColor;

    private int mChildSize;
    private int mChildColor;

    private OnGroupExpandedListener mOnGroupExpandedListener;

    public interface OnGroupExpandedListener {

        void onGroupExpanded(int groupPosition,int childPosition);
    }

    public PaExpandableListView(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public PaExpandableListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
       setDivider(null);
       setGroupIndicator(null);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PaExpandableListView);

        mGroupSize = (int) a.getDimension(R.styleable.PaExpandableListView_groupTextSize, 0);
        mGroupColor = a.getColor(R.styleable.PaExpandableListView_groupTextColor,
                getResources().getColor(R.color.pasc_primary_text));

        mChildSize = (int) a.getDimension(R.styleable.PaExpandableListView_childTextSize, 0);
        mChildColor = a.getColor(R.styleable.PaExpandableListView_childTextColor,
                getResources().getColor(R.color.pasc_primary_text));

        a.recycle();
    }


    public void setExpandableData(List<String> groupList, List<List<String>> childList, OnGroupExpandedListener onGroupExpandedListener){
        mOnGroupExpandedListener = onGroupExpandedListener;
        mGroupList = groupList;
        mChildList = childList;
        IndicatorExpandableListAdapter adapter = new
                IndicatorExpandableListAdapter(mGroupList,mChildList);
        setAdapter(adapter);
    }

    class IndicatorExpandableListAdapter extends BaseExpandableListAdapter {

       private List<String> groupList;
       private List<List<String>> childList;

        //                用于存放Indicator的集合
        private SparseArray<ImageView> mIndicators;

        public IndicatorExpandableListAdapter(List<String> mGroupList, List<List<String>> mChildList) {
            this.groupList = mGroupList;
            this.childList = mChildList;
            mIndicators = new SparseArray<>();
        }


        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return getGroup(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        public void setOnGroupExpandedListener(OnGroupExpandedListener onGroupExpandedListener) {
            mOnGroupExpandedListener = onGroupExpandedListener;
        }
        //            根据分组的展开闭合状态设置指示器
        public void setIndicatorState(int groupPosition, boolean isExpanded) {
            if (isExpanded) {
                mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand_more);
            } else {
                mIndicators.get(groupPosition).setImageResource(R.drawable.ic_expand_less);
            }
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_group_indicator, parent, false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_group_indicator);
                groupViewHolder.ivIndicator = (ImageView) convertView.findViewById(R.id.iv_indicator);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            groupViewHolder.tvTitle.setText(groupList.get(groupPosition));

                    if(mGroupSize == 0){
                        groupViewHolder.tvTitle.setTextSize(17);
                    }else {
                        groupViewHolder.tvTitle.getPaint().setTextSize(mGroupSize);
                    }
                    groupViewHolder.tvTitle.setTextColor(mGroupColor);
            //      把位置和图标添加到Map
            mIndicators.put(groupPosition, groupViewHolder.ivIndicator);
            //      根据分组状态设置Indicator
            setIndicatorState(groupPosition, isExpanded);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_child, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.lineView = convertView.findViewById(R.id.view_lin);

                childViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.label_expand_child);
                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            if(childList.get(groupPosition).size()-1 == childPosition ){
                childViewHolder.lineView.setVisibility(VISIBLE);
            }else {
                childViewHolder.lineView.setVisibility(GONE);
            }
            childViewHolder.tvTitle.setText(childList.get(groupPosition).get(childPosition));
            if(mChildSize == 0){
                childViewHolder.tvTitle.setTextSize(17);
            }else {
                childViewHolder.tvTitle.getPaint().setTextSize(mChildSize);
            }
            childViewHolder.tvTitle.setTextColor(mChildColor);
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnGroupExpandedListener != null) {
                        mOnGroupExpandedListener.onGroupExpanded(groupPosition,childPosition);
                    }
                }
            });
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        @Override
        public void onGroupExpanded(int groupPosition) {


        }

    }

    private static class GroupViewHolder {
        TextView tvTitle;
        ImageView ivIndicator;
    }

    private static class ChildViewHolder {
        TextView tvTitle;
        View lineView;
    }

}

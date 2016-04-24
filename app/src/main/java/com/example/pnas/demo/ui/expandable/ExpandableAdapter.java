package com.example.pnas.demo.ui.expandable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.MyApplication;

import java.util.HashMap;
import java.util.List;

/***********
 * @author 彭浩楠
 * @date 2016/3/18
 * @describ
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {

    private HashMap<Integer, List<String>> mHashMap;

    public ExpandableAdapter(HashMap<Integer, List<String>> hashMap) {
        mHashMap = hashMap;
    }

    @Override
    public long getGroupId(int groupPosition) {
        // 父id
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // 子id
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getGroupCount() {
        // 父条目的数量
        return mHashMap == null ? 0 : mHashMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 子条目的数量
        return mHashMap.get(groupPosition) == null ? 0 : mHashMap.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(MyApplication.getContext(), R.layout.item_expandable, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_expandable_tv_group);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText("group + " + (groupPosition + 1));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(MyApplication.getContext(), R.layout.item_expandable, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_expandable_tv_group);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(mHashMap.get(groupPosition).get(childPosition));

        return convertView;
    }

    private class ViewHolder {
        TextView tv;
    }

}

package com.pnas.demo.ui.list_weipan;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.entity.listview.ListViewBean;

import java.util.ArrayList;

/***********
 * @author pans
 * @date 2016/1/5
 * @describ
 */
public class ListViewActivity extends ListActivity {

    private ArrayList<ListViewBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        initData();
    }

    private void initData() {

        mData = new ArrayList<>();

        for (int x = 0; x < 20; x++) {
            ListViewBean bean = new ListViewBean();
            bean.date = "日期 " + x;
            bean.time = "时间 " + x;
            bean.name = "名字 " + x;
            bean.status = "状态 " + x;
            mData.add(bean);
        }

        setListAdapter(new ListViewAdapter());
    }


    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 10;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ListViewActivity.this, R.layout.item_vacation_details, null);
                holder.topView = convertView.findViewById(R.id.item_wage_view_top);
                holder.middleView = convertView.findViewById(R.id.item_wage_view);

                holder.tvDate = ((TextView) convertView.findViewById(R.id.item_vacation_details_date));
                holder.tvTime = ((TextView) convertView.findViewById(R.id.item_vacation_details_time));
                holder.ivEmpImage = ((ImageView) convertView.findViewById(R.id.item_vacation_details_empimage));
                holder.tvEmpName = ((TextView) convertView.findViewById(R.id.item_vacation_details_empname));
                holder.tvStatus = ((TextView) convertView.findViewById(R.id.item_vacation_details_status));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                holder.topView.setVisibility(View.GONE);
                holder.middleView.setBackground(getResources().getDrawable(R.drawable.shape_wage_point_select));
            } else {
                holder.topView.setVisibility(View.VISIBLE);
                holder.middleView.setBackground(getResources().getDrawable(R.drawable.shape_wage_point_normal));
            }

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvDate;
        TextView tvTime;

        ImageView ivEmpImage;
        TextView tvEmpName;
        TextView tvStatus;

        View topView;
        View middleView;
    }
}

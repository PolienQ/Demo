package com.example.pnas.demo.ui.ptrlistview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;
import com.example.pnas.demo.base.MyApplication;
import com.example.pnas.demo.view.PullToRefresh.PtrClassicFrameLayout;

import java.util.ArrayList;


/***********
 * @author 彭浩楠
 * @date 2016/1/9
 * @describ
 */
public class PullToRefreshActivity extends BaseActivity {

    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private ListView mListView;
    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mPtrClassicFrameLayout = ((PtrClassicFrameLayout) findViewById(R.id.ptr_header_list_view_frame));

        mListView = ((ListView) findViewById(R.id.ptr_list_view));
    }

    private void initData() {

        mData = new ArrayList<>();

        for (int x = 0; x < 30; x++) {
            mData.add("条目 " + x);
        }

        PtrAdapter adapter = new PtrAdapter();
        mListView.setAdapter(adapter);

    }

    private void initEvent() {

    }

    private class PtrAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
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
                convertView = View.inflate(MyApplication.getContext(), R.layout.item_ptr_list_view, null);
                holder.tvText = ((TextView) convertView.findViewById(R.id.item_ptr_tv_text));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String text = mData.get(position);

            holder.tvText.setText(text);

            return convertView;
        }
    }

    private class ViewHolder {

        TextView tvText;

    }
}

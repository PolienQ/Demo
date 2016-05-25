package com.pnas.demo.ui.ptrlistview;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.base.MyApplication;

import java.util.ArrayList;


/***********
 * @author 彭浩楠
 * @date 2016/1/9
 * @describ
 */
public class PullToRefreshActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private ArrayList<String> mData;
    private PtrAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mSwipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.ptr_header_list_view_frame));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red_01, R.color.blue_01, android.R.color.holo_orange_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        SystemClock.sleep(2000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData();
                            }
                        });
                    }
                }).start();

            }

        });

        mListView = ((ListView) findViewById(R.id.ptr_list_view));
    }

    private void initData() {

        mData = new ArrayList<>();

        for (int x = 0; x < 10; x++) {
            mData.add("条目 " + x);
        }

        mAdapter = new PtrAdapter();
        mListView.setAdapter(mAdapter);

    }

    private void initEvent() {

    }

    private void refreshData() {

        mData.clear();
        for (int x = 0; x < 10; x++) {
            mData.add("刷新的条目 " + x);
        }

        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        showToast("刷新成功");

    }

    private class PtrAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData == null ? null : mData.get(position);
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
                convertView = View.inflate(MyApplication.getInstance(), R.layout.item_ptr_list_view, null);
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

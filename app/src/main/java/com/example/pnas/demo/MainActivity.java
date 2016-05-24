package com.example.pnas.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pnas.demo.base.BaseActivity;
import com.example.pnas.demo.base.MyApplication;
import com.example.pnas.demo.ui.anmi.AnimationActivity;
import com.example.pnas.demo.ui.area.AreaCodeActivity;
import com.example.pnas.demo.ui.city.CityActivity;
import com.example.pnas.demo.ui.list_weipan.ListViewActivity;
import com.example.pnas.demo.ui.list_weipan.WeiPanActivity;
import com.example.pnas.demo.ui.ptrlistview.PullToRefreshActivity;
import com.example.pnas.demo.ui.recycler.RecyclerViewPagerActivity;
import com.example.pnas.demo.ui.recyclerview.RecyclerViewActivity;
import com.example.pnas.demo.ui.scan.ScanActivity;
import com.example.pnas.demo.ui.scan2.Scan2Activity;
import com.example.pnas.demo.ui.shadow.ShadowActivity;
import com.example.pnas.demo.ui.share.ShareActivity;
import com.example.pnas.demo.ui.timer.TimerActivity;
import com.example.pnas.demo.ui.year.YearTabActivity;
import com.example.pnas.demo.view.LineGridView;

public class MainActivity extends BaseActivity {

    private LineGridView mGridView;

    private String[] mStr = {"ListView", "year", "recycler", "ptr", "timer", "RecyclerView",
            "anmi", "shadow", "share", "扫描二维码", "扫描二维码2", "省市区", "城市", "微盘"};

    private Class[] mActivity = {ListViewActivity.class, YearTabActivity.class, RecyclerViewPagerActivity.class,
            PullToRefreshActivity.class, TimerActivity.class, RecyclerViewActivity.class, AnimationActivity.class,
            ShadowActivity.class, ShareActivity.class, ScanActivity.class, Scan2Activity.class,
            AreaCodeActivity.class, CityActivity.class, WeiPanActivity.class};
    private double exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次返回键退出程序");
                exitTime = System.currentTimeMillis();
                return true;
            }
            exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {

        mGridView = ((LineGridView) findViewById(R.id.main_grid_view));

    }

    private void initData() {

        mGridView.setAdapter(new ListViewAdapter());

    }

    private void initEvent() {

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoActivity(mActivity[position]);
            }
        });

    }

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStr.length == 0 ? 0 : mStr.length;
        }

        @Override
        public Object getItem(int position) {
            return mStr.length == 0 ? null : mStr[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv = new TextView(MyApplication.getInstance());
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(20);
                tv.setTextColor(Color.BLACK);
                tv.setPadding(0, 30, 0, 30);
            } else {
                tv = (TextView) convertView;
            }

            tv.setText(mStr[position]);

            return tv;
        }
    }

    public void gotoActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

}

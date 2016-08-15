package com.pnas.demo;

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

import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.ui.anmi.AnimationActivity;
import com.pnas.demo.ui.area.AreaCodeActivity;
import com.pnas.demo.ui.city.CityActivity;
import com.pnas.demo.ui.db.DbActivity;
import com.pnas.demo.ui.download.DownloadActivity;
import com.pnas.demo.ui.interview.InterviewActivity;
import com.pnas.demo.ui.launcher.LauncherActivity;
import com.pnas.demo.ui.list_weipan.ListViewActivity;
import com.pnas.demo.ui.memory.MemoryActivity;
import com.pnas.demo.ui.notification.NotificationActivity;
import com.pnas.demo.ui.photo.PhotoActivity;
import com.pnas.demo.ui.ptrlistview.PullToRefreshActivity;
import com.pnas.demo.ui.recyclerview.RecyclerViewActivity;
import com.pnas.demo.ui.scan.ScanActivity;
import com.pnas.demo.ui.shadow.ShadowActivity;
import com.pnas.demo.ui.share.ShareActivity;
import com.pnas.demo.ui.timer.TimerActivity;
import com.pnas.demo.ui.video.VideoActivity;
import com.pnas.demo.ui.web.WebActivity;
import com.pnas.demo.ui.year.YearTabActivity;
import com.pnas.demo.view.LineGridView;

public class MainActivity extends BaseActivity {

    private LineGridView mGridView;

    private String[] mStr = {"TimeLine", "year", "launcher", "ptr", "timer", "RecyclerView",
            "anmi", "shadow", "share", "扫描二维码", "Notification", "省市区", "城市", /*"微盘",*/
            "photo", "download", "db", "memory", "interview", "web", "video"};

    private Class[] mActivity = {ListViewActivity.class, YearTabActivity.class, LauncherActivity.class,
            PullToRefreshActivity.class, TimerActivity.class, RecyclerViewActivity.class,
            AnimationActivity.class, ShadowActivity.class, ShareActivity.class, ScanActivity.class,
            NotificationActivity.class, AreaCodeActivity.class, CityActivity.class,
            /*WeiPanActivity.class,*/ PhotoActivity.class, DownloadActivity.class, DbActivity.class,
            MemoryActivity.class, InterviewActivity.class, WebActivity.class, VideoActivity.class};
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
                tv.setTypeface(MyApplication.getTypeface());
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

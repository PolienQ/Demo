package com.example.pnas.demo.ui.timer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;
import com.example.pnas.demo.base.MyApplication;

/***********
 * @author 彭浩楠
 * @date 2016/1/20
 * @describ
 */
public class TimerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvCount;
    private TextView mTvPresent;
    private Runnable mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        mTvCount = ((TextView) findViewById(R.id.timer_tv_count));

        mTvPresent = ((TextView) findViewById(R.id.timer_tv_present));

    }

    private void initData() {

        final Handler handler = MyApplication.getHandler();

        mThread = new Runnable() {
            @Override
            public void run() {
                if (MyApplication.count != 0) {
                    mTvCount.setText(MyApplication.count + "");
                    MyApplication.count--;
                    handler.postDelayed(this, 1000);
                } else {
                    mTvCount.setText("发送验证码");
                    MyApplication.count = 60;
                }
            }
        };
        handler.postDelayed(mThread, 1000);
    }

    private void initEvent() {
        mTvPresent.setOnClickListener(this);
        mTvCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_tv_count:
                MyApplication.getHandler().post(mThread);
                break;

            case R.id.timer_tv_present:
                startActivity(new Intent(MyApplication.getContext(), DemoActivity.class));

                break;
        }

    }
}

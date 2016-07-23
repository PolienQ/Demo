package com.pnas.demo.ui.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class EventBusActivity extends BaseActivity {

    @BindView(R.id.event_bus_tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {

        mTvTitle.setText("标题");

    }

    private void initEvent() {

    }

    @OnClick(R.id.btn_event_bus_demo1)
    void onEventBusDemo1Click(View view) {

        showToast("点击");
    }
}

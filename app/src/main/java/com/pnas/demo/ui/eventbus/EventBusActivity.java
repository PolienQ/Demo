package com.pnas.demo.ui.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/***********
 * @author 彭浩楠
 * @date 2016/5/26
 * @describ
 */
@ContentView(R.layout.activity_event_bus)
public class EventBusActivity extends BaseActivity {

    @ViewInject(R.id.event_bus_tv_title)
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

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

    @Event(R.id.btn_event_bus_demo1)
    private void onEventBusDemo1Click(View view) {

        showToast("点击");
    }
}

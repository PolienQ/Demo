package com.pnas.demo.ui.memory;

import android.os.Bundle;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class LeakCanaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {

    }
}

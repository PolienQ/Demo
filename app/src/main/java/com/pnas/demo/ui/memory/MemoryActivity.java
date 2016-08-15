package com.pnas.demo.ui.memory;

import android.os.Bundle;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class MemoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }

    @OnClick(R.id.check_btn_leakCanary)
    void onClickLeakCanary(View view) {

        presentController(LeakCanaryActivity.class);

    }

    @OnClick(R.id.check_btn_lru)
    void onClickLru() {


    }

    @OnClick(R.id.check_btn_disk)
    void onClickDisk() {

    }

}

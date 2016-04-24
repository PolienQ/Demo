package com.example.pnas.demo.ui.city;

import android.os.Bundle;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;

/***********
 * @author 彭浩楠
 * @date 2016/4/23
 * @describ
 */
public class CityActivity extends BaseActivity {
    private CityPicker cityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

        cityPicker = (CityPicker) findViewById(R.id.citypicker);

    }

    private void initData() {

    }

    private void initEvent() {

    }

}

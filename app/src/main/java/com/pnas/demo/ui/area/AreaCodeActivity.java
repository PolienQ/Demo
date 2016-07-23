package com.pnas.demo.ui.area;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.utils.ToolUtils;

/***********
 * @author pans
 * @date 2016/4/23
 * @describ 省市区
 */
public class AreaCodeActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;
    private TextView mTvProvince;
    private TextView mTvCity;
    private TextView mTvDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_code);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

        mButton = ((Button) findViewById(R.id.area_code_btn));
        mTvProvince = ((TextView) findViewById(R.id.area_code_tv_province));
        mTvCity = ((TextView) findViewById(R.id.area_code_tv_city));
        mTvDistrict = ((TextView) findViewById(R.id.area_code_tv_district));

    }

    private void initData() {

    }

    private void initEvent() {

        mButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.area_code_btn:
                //点击弹出省市区选择框
                new AreaCodePopup(this).showAtLocation(findViewById(R.id.area_code_btn),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                ToolUtils.setWindowAlpha(this, 0.7f);
                break;


        }

    }
}

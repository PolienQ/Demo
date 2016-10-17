package com.pnas.demo.ui.download;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.ui.download.dagger2.Dagger2Activity;
import com.pnas.demo.ui.download.okhttp.OkHttpActivity;
import com.pnas.demo.ui.download.retrofit.RetrofitActivity;
import com.pnas.demo.ui.download.rx.RxAndroidActivity;
import com.pnas.demo.ui.download.rx.RxjavaActivity;

/***********
 * @author pans
 * @date 2016/5/26
 * @describ
 */
public class DownloadActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnCheck;
    private Button mBtnGlide;
    private Button mBtnOkHttp;
    private Button mBtnRetrofit;
    private Button mBtnRxJava;
    private Button mBtnRxAndroid;
    private Button mBtnDagger2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        mBtnCheck = ((Button) findViewById(R.id.download_btn_check));
        mBtnGlide = ((Button) findViewById(R.id.download_btn_glide));
        mBtnOkHttp = ((Button) findViewById(R.id.download_btn_ok_http));
        mBtnRetrofit = ((Button) findViewById(R.id.download_btn_retrofit));

        mBtnRxJava = ((Button) findViewById(R.id.download_btn_rx_java));
        mBtnRxAndroid = ((Button) findViewById(R.id.download_btn_rx_android));
        mBtnDagger2 = ((Button) findViewById(R.id.download_btn_dagger2));

    }

    private void initData() {

    }

    private void initEvent() {

        mBtnCheck.setOnClickListener(this);
        mBtnGlide.setOnClickListener(this);
        mBtnOkHttp.setOnClickListener(this);
        mBtnRetrofit.setOnClickListener(this);

        mBtnRxJava.setOnClickListener(this);
        mBtnRxAndroid.setOnClickListener(this);
        mBtnDagger2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.download_btn_check:


                break;

            case R.id.download_btn_glide:
                startActivity(new Intent(this, GlideActivity.class));
                break;

            case R.id.download_btn_ok_http:
                presentController(OkHttpActivity.class);
                break;

            case R.id.download_btn_retrofit:
                presentController(RetrofitActivity.class);
                break;

            case R.id.download_btn_rx_java:
                presentController(RxjavaActivity.class);
                break;
            case R.id.download_btn_rx_android:
                presentController(RxAndroidActivity.class);
                break;
            case R.id.download_btn_dagger2:
                presentController(Dagger2Activity.class);
                break;
        }

    }
}

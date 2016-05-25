package com.pnas.demo.ui.scan2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

/***********
 * @author 彭浩楠
 * @date 2016/4/21
 * @describ
 */
public class Scan2Activity extends BaseActivity {

    private final static int SCANNIN_GREQUEST_CODE = 1;
    /**
     * 显示扫描结果
     */
    private TextView mTextView;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan2);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);

        //点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
        //扫描完了之后调到该界面
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Scan2Activity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });

    }

    private void initData() {

    }

    private void initEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    //显示扫描到的内容
                    mTextView.setText(data.getStringExtra("result"));
                    //显示
                    mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
                }
                break;
        }
    }
}

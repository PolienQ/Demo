package com.pnas.demo.ui.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.ui.scan.zxing.android.CaptureActivity;
import com.pnas.demo.ui.scan.zxing.encode.CodeCreator;
import com.pnas.demo.utils.FileUtils;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;

/***********
 * @author 彭浩楠
 * @date 2016/4/21
 * @describ http://www.cnblogs.com/exmyth/p/5142600.html 参考
 */
public class ScanActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SCAN = 0x0000;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    private Button creator, scaning;
    private EditText mEtNumber;
    private ImageView mIvIcon;
    private EditText mEtCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        log(FileUtils.getPicClipDir());

        initView();
        initData();
        initEvent();
    }

    private void initView() {

        mEtCreator = ((EditText) findViewById(R.id.scan_et_creator));
        creator = ((Button) findViewById(R.id.scan_btn_QR_creator));
        scaning = ((Button) findViewById(R.id.scan_btn_QR_scaning));
        mEtNumber = ((EditText) findViewById(R.id.scan_et_number));
        mIvIcon = ((ImageView) findViewById(R.id.scan_iv_icon));

    }

    private void initData() {

    }

    private void initEvent() {

        creator.setOnClickListener(this);
        scaning.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_btn_QR_creator:

                String url = mEtCreator.getText().toString();
                try {
                    Bitmap bitmap = CodeCreator.createQRCode(url, true);

                    /** 保存方法 */
                    File f = new File(FileUtils.getPicClipDir(), "11.png");
                    try {
                        FileOutputStream out = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
                        out.flush();
                        out.close();
                        Toast.makeText(ScanActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mIvIcon.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.scan_btn_QR_scaning:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                mEtNumber.setText("解码结果： \n" + content);
                mIvIcon.setImageBitmap(bitmap);
            }
        }
    }

}

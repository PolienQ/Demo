package com.pnas.demo.ui.memory;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;

import com.jakewharton.disklrucache.DiskLruCache;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.utils.FileUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    @OnClick(R.id.memory_btn_leakCanary)
    void onClickLeakCanary(View view) {

        presentController(LeakCanaryActivity.class);

    }

    @OnClick(R.id.memory_btn_lru)
    void onClickLru() {

        final int memory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = memory / 8;
        LruCache<String, Bitmap> stringBitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getWidth() + value.getHeight();
            }
        };

    }

    @OnClick(R.id.memory_btn_disk)
    void onClickDisk() {

        File file = new File(FileUtils.getPicClipDir());
        int appVersion;
        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            appVersion = 1;
        }
        try {
            DiskLruCache stringBitmapLruCache = DiskLruCache.open(file, appVersion, 1, 10 * 1024 * 1024);

            // 写入到本地
            DiskLruCache.Editor editor = stringBitmapLruCache.edit("key");
            byte[] bytes = new byte[1024];
            int length;
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedOutputStream outputStream = new BufferedOutputStream(editor.newOutputStream(0), 8 * 1024);
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.memory_btn_mem)
    void clickMem() {

        presentController(MemListActivity.class);

    }

}

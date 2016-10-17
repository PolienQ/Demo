package com.pnas.demo.ui.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.constacts.IConstant;
import com.pnas.demo.utils.BitmapUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.io.FileSystem;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/***********
 * @author pans
 * @date 2016/6/24
 * @describ
 */
public class GlideActivity extends BaseActivity implements IConstant {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        initView();
        initData();
        initEvent();

    }

    private void initView() {

        mImageView1 = ((ImageView) findViewById(R.id.glide_iv1));
        mImageView2 = ((ImageView) findViewById(R.id.glide_iv2));
        mImageView3 = ((ImageView) findViewById(R.id.glide_iv3));

    }

    private void initData() {


        Glide.with(this).load(picPath)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(mImageView1) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(MyApplication.getInstance().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });

        Glide.with(this).load(picPath)
                .transform(new CircleTransform(MyApplication.getInstance()))   // 变成圆形
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // 缓存不同大小尺寸的图片
//                .override(100, 100) // 缩放
//                .centerCrop() // 图片太大时显示图片的中间位置
                .into(mImageView2);


        Request request = new Request.Builder()
//                .url("https://114.80.227.143/Employee/201605/19/1734884-1463640280923.jpg")
                .url(picPath)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                File file = new File(memPath);
                if (!file.exists() && file.mkdirs()) {
                    return;
                }
                file = new File(file, "GlidePic.jpg");

                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapUtils.readBitmap(inputStream);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

                /*BufferedSource picSource = response.body().source();
                byte[] b = new byte[2048];
                int len;
                if (!file.exists()) {
                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    while ((len = picSource.read(b)) != -1) {
                        sink.write(b, 0, len);
                        sink.flush();
                    }
                    sink.close();
                    log(file.exists() ? file.getAbsolutePath() + " - 长度: " + file.length() : "图片不存在");
                }

                InputStream fileSource = Okio.buffer(Okio.source(file)).inputStream();
                if (fileSource.available() == 0) {
                    log("输入流为空");
                    return;
                }

                final Bitmap bitmap = BitmapUtils.readBitmap(file.getAbsolutePath());
                fileSource.close();*/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView3.setImageBitmap(bitmap);
                    }
                });
            }
        });

    }

    private void initEvent() {

    }

    public class MyBitmapImageViewTarget extends BitmapImageViewTarget {

        public MyBitmapImageViewTarget(ImageView view) {
            super(view);
        }

        @Override
        protected void setResource(Bitmap resource) {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(MyApplication.getInstance().getResources(), resource);
            circularBitmapDrawable.setCircular(true);
            getView().setImageDrawable(circularBitmapDrawable);
        }
    }

    public static class CircleTransform extends BitmapTransformation {

        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            if (toTransform == null) return null;

            int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
            int x = (toTransform.getWidth() - size) / 2;
            int y = (toTransform.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 配置图片的参数
     */
    public class GlideConfiguration implements GlideModule {

        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            // Apply options to the builder here.
//            builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        }

        @Override
        public void registerComponents(Context context, Glide glide) {
            // register ModelLoaders here.
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

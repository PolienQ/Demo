package com.example.pnas.demo.ui.anmi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/***********
 * @author 彭浩楠
 * @date 2016/2/26
 * @describ
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;
    private Button mBtnFile;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {

        mButton = ((Button) findViewById(R.id.animation_btn));
        mBtnFile = ((Button) findViewById(R.id.animation_btn_file));
        mImageView = ((ImageView) findViewById(R.id.animation_iv));

        mButton.setOnClickListener(this);
        mBtnFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.animation_btn:

                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setInterpolator(new BounceInterpolator());

                mButton.startAnimation(scaleAnimation);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                String s = "2015-05-01";
                try {
                    long time = df.parse(s + " 00:00").getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (time - currentTimeMillis > 0) {
                        showToast(time + "  未过期");
                    } else {
                        showToast("过期");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.animation_btn_file:
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                showToast(absolutePath);
//                Bitmap bitmap = readBitmap(absolutePath + "/wmi/HRO_IMG/pic/clip/1734884-1462426290517.jpg");
                Bitmap bitmap = readBitmap(absolutePath + "/wmi/HRO_IMG/pic/clip/1734882-1461722516148.jpg");
//                Bitmap bitmap = BitmapFactory.decodeFile(absolutePath + "/wmi/HRO_IMG/pic/clip/1734882-1461722516148.jpg");
                mImageView.setImageBitmap(bitmap);

                break;
        }

    }

    public static Bitmap readBitmap(String uriString) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = computeSampleSize(opt, -1, 128 * 128);
        //这里一定要将其设置回false，因为之前我们将其设置成了true
        Bitmap bitmap = null;
        Uri uri = Uri.parse(uriString);
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(uriString), 2 * 1024);
            bitmap = BitmapFactory.decodeStream(input, null, opt);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}

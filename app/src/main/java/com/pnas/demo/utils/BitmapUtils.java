package com.pnas.demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.pnas.demo.base.MyApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*****************
 * @author xiaozongkang
 * @date 2016/1/11
 * @describ
 ******************/
public class BitmapUtils {

    /**
     * 读取图片
     *
     * @param context
     * @return
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
//        InputStream is = context.getResources().openRawResource(resId);
//        return BitmapFactory.decodeStream(is,null,opt);
        return BitmapFactory.decodeResource(context.getResources(), resId, opt);
    }

    /**
     * 读取图片
     *
     * @param uri
     * @return
     */
    public static Bitmap readBitmap(Uri uri) {

        Bitmap bitmap = null;
        InputStream input = null;
        try {
            input = MyApplication.getInstance().getContentResolver().openInputStream(uri);
            bitmap = readBitmap(input);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("图片Uri不正确");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input = null;
            }
        }
        return bitmap;
    }

    /**
     * 读取图片
     *
     * @param uriOrPath uri字符串或实际路径
     * @return
     */
    public static Bitmap readBitmap(String uriOrPath) {

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();

        if (!uriOrPath.startsWith(absolutePath)) {
            return readBitmap(Uri.parse(uriOrPath));
        } else {
            Bitmap bitmap = null;
            InputStream input = null;
            try {
                input = new BufferedInputStream(new FileInputStream(uriOrPath));
                bitmap = readBitmap(input);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.d("图片路径不正确");
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input = null;
                }
            }

            return bitmap;
        }

    }

    /**
     * 读取图片
     *
     * @param input
     * @return
     */
    public static Bitmap readBitmap(InputStream input) {

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, opt);
        LogUtil.d("height = " + opt.outHeight + " , width = " + opt.outWidth);
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            opt.inPurgeable = true;
            opt.inInputShareable = true;
        }
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = computeSampleSize(opt, -1, 128 * 128);
        //这里一定要将其设置回false，因为之前我们将其设置成了true
        return BitmapFactory.decodeStream(input, null, opt);

    }

    public static Bitmap imageZoom(Uri uri) {
        return imageZoom(500, readBitmap(uri));
    }

    public static Bitmap imageZoom(String path) {
        return imageZoom(500, readBitmap(path));
    }

    /**
     * 压缩图片到指定大小
     *
     * @param maxSize
     * @param bitMap
     */
    public static Bitmap imageZoom(double maxSize, Bitmap bitMap) {
        //图片允许最大空间   单位：KB
//        maxSize =400.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
                    bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 填充ImageView
     *
     * @param bitmap
     * @return
     */
    public static Bitmap zoomImage(Bitmap bitmap, View imageView) {
        return zoomImage(bitmap, imageView.getWidth(), imageView.getHeight());
    }

    /**
     * 计算压缩比例
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
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

    /**
     * 计算压缩比例
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
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

    /**
     * view 转化成 Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 图片变灰
     *
     * @param context
     * @param drawable_id
     * @return
     */
    public static Drawable makeDrawableGray(Context context, int drawable_id) {
        Drawable mDrawable = context.getResources().getDrawable(drawable_id);
        //Make this drawable mutable.
        //A mutable drawable is guaranteed to not share its state with any other drawable.
        mDrawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        mDrawable.setColorFilter(cf);
        return mDrawable;
    }

    /**
     * 图片合成
     *
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap synthesisBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }

        src = imageZoom(50, src);
        watermark = imageZoom(50, watermark);
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        //create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h + wh, Bitmap.Config.RGB_565);//创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        //draw src into
        cv.drawBitmap(src, 0, 0, null);//在 0，0坐标开始画入src
        //draw watermark into
        cv.drawBitmap(watermark, 0, h, null);//在src的右下角画入水印
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存

        //store
        cv.restore();//存储
        return newb;
    }

    public static void saveBitmap(Bitmap bitmap) {
        saveBitmap(bitmap, null);
    }

    public static void saveBitmap(Bitmap bitmap, File imageFile) {
        if (imageFile == null) {
            imageFile = FileUtils.getImageFile();
        }
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

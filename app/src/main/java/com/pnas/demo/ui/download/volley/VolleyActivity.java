package com.pnas.demo.ui.download.volley;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.constacts.IConstant;
import com.pnas.demo.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********
 * @author pans
 * @date 2016/11/1
 * @describ
 */
public class VolleyActivity extends BaseActivity {

    @BindView(R.id.volley_iv)
    ImageView mVolleyIv;
    @BindView(R.id.volley_network_image_view)
    NetworkImageView mNetworkImageView;

    private RequestQueue mRequestQueue;
    String url = "https://github.com/hongyangAndroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        ButterKnife.bind(this);

        mRequestQueue = Volley.newRequestQueue(this);

        /**
         * 第三种图片加载方式
         */
        mNetworkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        mNetworkImageView.setErrorImageResId(R.mipmap.ic_launcher);
        mNetworkImageView.setImageUrl(IConstant.PIC_URL,
                new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

                    int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 4);
                    LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(maxSize) {
                        @Override
                        protected int sizeOf(String key, Bitmap value) {
                            return value.getHeight() + value.getWidth() / 1024;
                        }
                    };

                    @Override
                    public Bitmap getBitmap(String s) {
                        return mLruCache.get(s);
                    }

                    @Override
                    public void putBitmap(String s, Bitmap bitmap) {
                        mLruCache.put(s, bitmap);
                    }
                }));
    }

    public void clickGet(View view) {

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        ToastUtil.showLongToast(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        mRequestQueue.add(request);

    }

    public void clickPost(View view) {

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        log(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }) {
            /**
             * 复写方法,传入POST请求的key和value
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };
        Request<String> stringRequest = mRequestQueue.add(request);
        stringRequest.cancel();

    }

    public void clickJson(View view) {

        HashMap<String, String> map = new HashMap<>();
//        map.put("key", "value");
        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        log(jsonObject.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        log(volleyError.getMessage());
                    }
                });
        mRequestQueue.add(jsonObjectRequest);

    }

    public void clickGetImage(View view) {

        /**
         * 中间的设置图片的最大宽高,0则不处理
         */
        ImageRequest imageRequest = new ImageRequest(IConstant.PIC_URL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        mVolleyIv.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mVolleyIv.setImageResource(R.mipmap.ic_launcher);
                    }
                });
        mRequestQueue.add(imageRequest);

    }

    public void clickImageLoader(View view) {

        ImageLoader imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            /**
             * 缓存
             */
            LruCache<String, Bitmap> mBitmapLruCache = new LruCache<String, Bitmap>(
                    (int) (Runtime.getRuntime().maxMemory() / 1024 / 4)) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getHeight() + bitmap.getWidth() / 1024;
                }
            };

            @Override
            public Bitmap getBitmap(String s) {
                return mBitmapLruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                mBitmapLruCache.put(s, bitmap);
            }
        });

        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mVolleyIv.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                mVolleyIv.setImageBitmap(imageContainer.getBitmap());
            }
        };

        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", /* this */listener);

    }

    public void clickXml(View view) {

    }


}

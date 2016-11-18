package com.pnas.demo.ui.download.okhttp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.entity.listview.ListViewBean;
import com.pnas.demo.utils.BitmapUtils;
import com.pnas.demo.utils.ToastUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***********
 * @author pans
 * @date 2016/6/24
 * @describ
 */
public class OkHttpActivity extends BaseActivity {

    @BindView(R.id.ok_http_iv)
    ImageView mOkHttpIv;
    @BindView(R.id.ok_http_tv)
    TextView mOkHttpTv;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);

        mGson = new Gson();

        initView();
        initData();

    }

    private void initView() {

    }

    private void initData() {


    }

    /**
     * get请求
     */
    public void clickGet(View view) {

        Uri.Builder builder = Uri.parse("https://github.com/hongyangAndroid").buildUpon();
        Uri uri = builder.appendQueryParameter("111", "222").appendQueryParameter("aaa", "bbb").build();
        showToast("get请求添加参数后 : " + uri.toString());

        //创建一个Request,GET请求
        Request request = new Request.Builder()
                .cacheControl(CacheControl.FORCE_NETWORK)   // 不缓存, FORCE_CACHE 防止使用网络
                .url("https://github.com/hongyangAndroid")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showLongToast("返回内容 : " + string);
                    }
                });

//                Type type = new TypeToken<ListViewBean>() {
//                }.getType();
//                ListViewBean listViewBean = mGson.fromJson(response.body().charStream(), type);
//
//                // 获取缓存
//                Response cacheResponse = response.cacheResponse();
//                listViewBean = mGson.fromJson(cacheResponse.body().charStream(), type);

            }
        });

    }

    /**
     * post请求
     */
    public void clickPost(View view) {


    }

    /**
     * 字符串post请求
     */
    public void clickString(View view) {

    }

    /**
     * 表单请求
     */
    public void clickForm(View view) {

        RequestBody requestBody = noFileFormBody();

        Request request = new Request.Builder().put(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    /**
     * 创建表单请求体 方法1 不传文件
     */
    private RequestBody noFileFormBody() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("111", "222");
        hashMap.put("aaa", "bbb");

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : hashMap.keySet()) {
            builder.add(key, hashMap.get(key));
        }
        return builder.build();
    }

    /**
     * 创建多种数据的请求体
     */
    private RequestBody getMultipartBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 文件下载
     */
    public void clickDownload(View view) {

    }

    /**
     * 获取图片
     */
    public void clickGetImage(View view) {

        int type = 1;

        final OkHttpClient okHttpClient = mOkHttpClient.newBuilder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .readTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000, TimeUnit.SECONDS)
                .build();

        final Request request = new Request.Builder()
                .url("http://images.csdn.net/20150817/1.jpg")
                .tag(this)
                .build();

        // execute 执行网络请求 耗时操作
        // enqueue 加入到请求队列中
        switch (type) {
            case 1:

                Call call = okHttpClient.newCall(request);
                call.enqueue(new MyCallBack() {
                    @Override
                    public void onSuccess(Call call, Response response) {
                        Bitmap bitmap = BitmapUtils.readBitmap(response.body().byteStream());
                        mOkHttpIv.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Call call, String msg, Object tag) {
                        showToast(msg);
                    }
                });
                break;
            case 2:

                ExecutorService executorService = okHttpClient.dispatcher().executorService();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        Response response;

                        try {
                            response = okHttpClient.newCall(request).execute();
                        } catch (IOException e) {
//                    e.printStackTrace();
                            showToast("请求取消");
                            return;
                        }

                        if (response.isSuccessful()) {
                            final Bitmap bitmap = BitmapUtils.readBitmap(response.body().byteStream());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mOkHttpIv.setImageBitmap(bitmap);
                                }
                            });
                        } else {
                            showToast("图片读取失败");
                        }
                    }
                });

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

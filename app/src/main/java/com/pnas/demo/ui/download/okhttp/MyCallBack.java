package com.pnas.demo.ui.download.okhttp;

import android.os.Handler;

import com.pnas.demo.base.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/***********
 * @author 彭浩楠
 * @date 2016/7/1
 * @describ
 */
public abstract class MyCallBack implements Callback {

    private final Handler mHandler;

    public MyCallBack() {
        mHandler = MyApplication.getHandler();
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        sendFailResultCallback(call, e.getMessage(), call.request().tag());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (call.isCanceled()) {
            onFailure(call, new IOException("请求取消"));
            return;
        }
        sendSuccessResultCallback(call, response, call.request().tag());
    }

    public void sendFailResultCallback(final Call call, final String msg, final Object tag) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onError(call, msg, tag);
                onAfter(tag);
            }
        });
    }

    public void sendSuccessResultCallback(final Call call, final Response response, final Object tag) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(call, response);
                onAfter(tag);
            }
        });
    }

    public abstract void onSuccess(Call call, Response response);

    public abstract void onError(Call call, String msg, Object tag);

    public void onAfter(Object tag) {

    }

}

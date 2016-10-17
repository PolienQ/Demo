package com.pnas.demo.ui.download.retrofit;

import android.os.Bundle;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.entity.retrofit.NewsDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/***********
 * @author pans
 * @date 2016/7/4
 * @describ
 */
public class RetrofitActivity extends BaseActivity {

    private RequestService mRequestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        initRetrofit();
        initView();
        initData();
    }

    /**
     * - Base URL: 总是以 / 结尾
     * - @Url: 不要以 / 开头
     */
    private void initRetrofit() {
        // http://news-at.zhihu.com/api/4/story/8536302
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .client(mOkHttpClient)
//                .callFactory(mOkHttpClient)   // 添加其他的网络请求
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRequestService = retrofit.create(RequestService.class);
    }

    private void initView() {


    }

    private void initData() {

    }

    public void clickGet(View view) {
//        mRequestService.getDemo1()
        /*mRequestService.getDemo2("story", 8536302)
                .enqueue(new Callback<NewsDetail>() {
                    @Override
                    public void onResponse(Call<NewsDetail> call, Response<NewsDetail> response) {
                        showToast("请求成功");
                    }

                    @Override
                    public void onFailure(Call<NewsDetail> call, Throwable t) {

                    }
                });*/

        mRequestService.getNewsDetail(8536302)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showToast("Loading");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {
                        showToast("完成,结束");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("请求失败");
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        showToast("请求成功");
                    }
                });
    }

    public void clickPost(View view) {

    }

    public void clickString(View view) {

    }

    public void clickGetImage(View view) {

    }

    public void clickForm(View view) {

    }

    public void clickDownload(View view) {

    }
}

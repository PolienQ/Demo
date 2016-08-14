package com.pnas.demo.ui.download.dagger2.component;

import com.google.gson.Gson;
import com.pnas.demo.ui.download.dagger2.Dagger2Activity;
import com.pnas.demo.ui.download.dagger2.module.NetModule;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
//@Component(modules = {NetModule.class})
public interface NetComponent {

    void inject(Dagger2Activity dagger2Activity);

    Cache provideOkHttpCache();

    Gson provideGson();

    OkHttpClient provideOkHttpClient(Cache cache);

    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient);

}

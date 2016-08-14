package com.pnas.demo.ui.download.dagger2.module;

import android.os.Environment;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.ui.download.okhttp.LoggerInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
@Module
public class NetModule {

    private String baseUrl;

    public NetModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        Default behaviour....
//        {"myField":"value1","myAnotherField":"value2"}
//
//        Fields with lower case and dashes...
//        {"my-field":"value1","my-another-field":"value2"}
//
//        Fields with upper case and dashes...
//        {"My Field":"value1","My Another Field":"value2"}
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache() {
        int cache = 10 * 1024 * 1024;
        return new Cache(new File(MyApplication.getInstance().getCacheDir(), "HttpCache"), cache);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor(""))
                // 添加证书和缓存等其他设置
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

}

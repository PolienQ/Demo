package com.pnas.demo.ui.download.dagger2.module;

import com.pnas.demo.ui.download.okhttp.OkHttpUtils;
import com.pnas.demo.utils.ToolUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
@Module
public class ActivityModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return OkHttpUtils.getOkHttpClient();
    }

}

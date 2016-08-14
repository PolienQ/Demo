package com.pnas.demo.ui.download.dagger2.module;

import com.pnas.demo.base.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
@Module
public class AppModule {

    MyApplication mApplication;

    public AppModule(MyApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    MyApplication getApplication() {
        return mApplication;
    }

}

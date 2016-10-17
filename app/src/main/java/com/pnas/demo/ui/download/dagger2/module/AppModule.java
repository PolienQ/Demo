package com.pnas.demo.ui.download.dagger2.module;

import android.content.Context;

import com.pnas.demo.R;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.ui.download.dagger2.TextColor;
import com.pnas.demo.ui.download.dagger2.TextSize;

import javax.inject.Named;
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
    Context getContext() {
        return mApplication;
    }

    @Singleton
    @TextSize
    @Provides
    int getTextSize() {
        return 60;
    }

    @Singleton
    @TextColor
    @Provides
    int getTextColor(Context context) {
        return context.getResources().getColor(R.color.black_01);
    }

}

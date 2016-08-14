package com.pnas.demo.ui.download.dagger2.component;

import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.ui.download.dagger2.module.ActivityModule;

import dagger.Component;
import okhttp3.OkHttpClient;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ Activity基类的Component
 */
//@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

    OkHttpClient provideOkHttpClient();

}

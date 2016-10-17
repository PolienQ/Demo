package com.pnas.demo.ui.download.dagger2.component;

import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.ui.download.dagger2.module.AppModule;
import com.pnas.demo.ui.download.dagger2.module.NetModule;
import com.pnas.demo.ui.download.dagger2.srope.NetScope;

import javax.inject.Singleton;

import dagger.Component;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
@NetScope
@Component(/*dependencies = AppComponent.class,*/ modules = NetModule.class)
public interface NetComponent {

    void inject(BaseActivity baseActivity);

}

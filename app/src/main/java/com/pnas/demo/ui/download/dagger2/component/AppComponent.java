package com.pnas.demo.ui.download.dagger2.component;

import com.pnas.demo.ui.download.dagger2.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ Application的Component
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

}

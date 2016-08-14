package com.pnas.demo.ui.download.dagger2.component;

import android.widget.TextView;

import com.pnas.demo.ui.download.dagger2.Dagger2Activity;
import com.pnas.demo.ui.download.dagger2.module.Dagger2Module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ Activity的Component
 */
@Singleton
@Component(dependencies = AppComponent.class, modules = {Dagger2Module.class})
public interface Dagger2Component {

    //对MainActivity进行依赖注入
    void inject(Dagger2Activity dagger2Activity);

    @Named("text_view")
    TextView provideTextView();

    @Named("big_text_view")
    TextView provideBigTextView();

}

package com.pnas.demo.ui.download.dagger2.component;

import com.pnas.demo.ui.download.DownloadActivity;
import com.pnas.demo.ui.download.dagger2.Dagger2Activity;
import com.pnas.demo.ui.download.dagger2.module.AppModule;
import com.pnas.demo.ui.download.dagger2.module.Dagger2Module;
import com.pnas.demo.ui.download.dagger2.srope.NetScope;
import com.pnas.demo.ui.download.dagger2.srope.PreActivity;

import javax.inject.Singleton;

import dagger.Component;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ Activity的Component,@Singleton和@Scope必须有一个
 */
@Singleton
@Component(modules = {Dagger2Module.class, AppModule.class})
public interface Dagger2Component {

    //对MainActivity进行依赖注入 ; 标明被注入的参数
    void inject(Dagger2Activity dagger2Activity);

    void inject(DownloadActivity dagger2Activity);

/*   @Named("text_view")
    TextView provideTextView();

    @Named("big_text_view")
    TextView provideTextView(Context context);*/

}

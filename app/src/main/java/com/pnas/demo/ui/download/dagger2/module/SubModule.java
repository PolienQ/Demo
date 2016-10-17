package com.pnas.demo.ui.download.dagger2.module;

import com.pnas.demo.ui.download.dagger2.srope.PreActivity;

import dagger.Module;
import dagger.Provides;

/***********
 * @author pans
 * @date 2016/8/16
 * @describ
 */
@Module
public class SubModule {

    @Provides
    String getString() {
        return "123";
    }

}

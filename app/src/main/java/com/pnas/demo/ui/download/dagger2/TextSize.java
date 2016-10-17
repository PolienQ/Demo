package com.pnas.demo.ui.download.dagger2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/***********
 * @author pans
 * @date 2016/8/16
 * @describ
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface TextSize {
}

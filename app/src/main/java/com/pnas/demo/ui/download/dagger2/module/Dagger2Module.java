package com.pnas.demo.ui.download.dagger2.module;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnas.demo.base.MyApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ 初始化的实例, 相当于一个简单的工厂模式.把第三方的类库存放在Module, 让Component把本类中的对象注入到被 Injector 注解的地方
 * 提供实体类的类
 */
@Module
public class Dagger2Module {

    private Context mContext;

    public Dagger2Module(Context context) {
        mContext = context;
    }

    @Provides
    @Named("text_view")
    @Singleton
    TextView provideTextView() {
        TextView textView = new TextView(mContext);
        textView.setText("Dagger2的TextView");
        textView.setTextSize(40);
        return textView;
    }

    @Provides
    @Named("big_text_view")
    @Singleton
    TextView provideBigTextView() {
        TextView textView = new TextView(mContext);
        textView.setText("Dagger2的TextView2");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.topMargin = 60;
        textView.setLayoutParams(params);
        textView.setTextSize(50);
        return textView;
    }

}

package com.pnas.demo.ui.download.dagger2.module;

import android.content.Context;
import android.widget.TextView;

import com.pnas.demo.ui.download.dagger2.TextColor;
import com.pnas.demo.ui.download.dagger2.TextSize;

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
    TextView provideBigTextView(Context context, @TextSize int size, @TextColor int color) {
        TextView textView = new TextView(context);
        textView.setText("Dagger2的TextView2");
        textView.setPadding(0, 60, 0, 0);
        textView.setTextSize(size);
        textView.setTextColor(color);
        return textView;
    }

}

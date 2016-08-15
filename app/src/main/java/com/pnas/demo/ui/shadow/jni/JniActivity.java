package com.pnas.demo.ui.shadow.jni;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

/***********
 * @author pans
 * @date 2016/8/3
 * @describ 1, 在local.properties中设置ndk路径;在gradle.properties设置android.useDeprecatedNdk=true
 * ndk是本地开发工具集,快速开发C或C++的动态库,通过修改mk文件创建出so文件(动态链接库),并能自动将so和java应用一起打包成apk.
 * 编码:
 * 2,写好native关键字的方法,使用javah命令生成.h文件,
 * 在com文件夹前一级目录下 javah -jni com.losileeya.getapkinfo.JNIUtils 执行完后在该目录生成
 * 3,把.h头文件放到jni文件夹中,创建xxx_jni.c文件,根据.h头文件的方法名实现具体逻辑(参考ndk的jni.h头文件内的方法,里面的方法会调用C)
 * 编译:
 * 4,创建.mk文件,指导编译的配置文件,
 * <p/>
 * native方法->使用javah生成.h->创建.c(#include生成的头文件)->创建.mk文件->使用ndk-build.cmd创建.so文件
 *
 * 如果不能include头文件,删除jni,重新new 一个 JNI FOLDER
 */
public class JniActivity extends BaseActivity {

    private TextView mTvPackName;
    private TextView mTvSignature;

    private String str = JniUtils.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        initView();
    }

    private void initView() {


        MediaPlayer mediaPlayer = new MediaPlayer();
        // start方法就是调用native方法
        mediaPlayer.start();

        mTvPackName = ((TextView) findViewById(R.id.jni_tv_packname));
//        mTvPackName.setText(JniUtils.getPackname(this));
        mTvSignature = ((TextView) findViewById(R.id.jni_tv_signature));
//        mTvSignature.setText(JniUtils.getSignature(this));
        findViewById(R.id.jni_btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(str);
            }
        });

        findViewById(R.id.jni_btn_set_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JniUtils.set(str);
            }
        });
    }

}
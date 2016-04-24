package com.example.pnas.demo.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.pnas.demo.utils.LogUtil;
import com.umeng.socialize.PlatformConfig;

/***********
 * @author 彭浩楠
 * @date 2015/12/31
 * @describ
 */
public class MyApplication extends Application {

    private static Context mContext;
    private static Handler mHandler = new Handler();
    public static int count = 60;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        LogUtil.isDebug = true;

        //微信 appid appsecret
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("417253825", "43ecd04059c5d1f941a53bc13fd95b82");

    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }
}

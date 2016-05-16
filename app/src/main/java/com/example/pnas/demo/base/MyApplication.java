package com.example.pnas.demo.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.pnas.demo.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/***********
 * @author 彭浩楠
 * @date 2015/12/31
 * @describ
 */
public class MyApplication extends Application {

    private static MyApplication mContext;
    private static Handler mHandler = new Handler();
    public static int count = 60;
    public List<Activity> activityManager; // 管理Activity栈


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        LogUtil.isDebug = true;

        // 管理Activity栈
        activityManager = new ArrayList<>();

        // 激光推送
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);  // 调试模式

        //微信 appid appsecret
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("417253825", "43ecd04059c5d1f941a53bc13fd95b82");

        // UMAnalyticsConfig(Context context, String appkey, String channelId, EScenarioType eType,Boolean isCrashEnable)
//        构造意义：
//        String appkey:官方申请的Appkey
//        String channel: 渠道号
//        EScenarioType eType: 场景模式，包含统计、游戏、统计盒子、游戏盒子
//        Boolean isCrashEnable: 可选初始化. 是否开启crash模式
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this, "5718a5cee0f55a388d0022bf", "google");
        MobclickAgent.startWithConfigure(config);

    }

    public static MyApplication getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }
}

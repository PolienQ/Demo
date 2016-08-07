package com.pnas.demo.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Handler;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.pnas.demo.utils.HttpsUtils;
import com.pnas.demo.utils.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;

/***********
 * @author pans
 * @date 2015/12/31
 * @describ
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private static Handler mHandler = new Handler();
    private static ExecutorService executorService;
    public static int count = 60;
    public List<Activity> activityManager; // 管理Activity栈
    private static Typeface mTypeface;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        LogUtil.isDebug = true;

        // 管理Activity栈
        activityManager = new ArrayList<>();

        // 激光推送
        /*JPushInterface.init(this);
        JPushInterface.setDebugMode(true);  // 调试模式*/

        //微信 appid appsecret
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
//        PlatformConfig.setSinaWeibo("417253825", "43ecd04059c5d1f941a53bc13fd95b82");

        // UMAnalyticsConfig(Context context, String appkey, String channelId, EScenarioType eType,Boolean isCrashEnable)
//        构造意义：
//        String appkey:官方申请的Appkey
//        String channel: 渠道号
//        EScenarioType eType: 场景模式，包含统计、游戏、统计盒子、游戏盒子
//        Boolean isCrashEnable: 可选初始化. 是否开启crash模式
//        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this, "5718a5cee0f55a388d0022bf", "google");
//        MobclickAgent.startWithConfigure(config);

        // QQbugly
        CrashReport.initCrashReport(getApplicationContext(), "900030821", false);

    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static synchronized ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>());
        }
        return executorService;
    }

    public static Typeface getTypeface() {
        if (mTypeface == null) {
            synchronized (MyApplication.class) {
                if (mTypeface == null) {
                    mTypeface = Typeface.createFromAsset(getInstance().getAssets(), "fonts/chs/RuiZiYunZiKuXingCaoTiGBK.TTF");
                }
            }
        }
        return mTypeface;
    }

}

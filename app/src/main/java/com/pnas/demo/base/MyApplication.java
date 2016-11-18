package com.pnas.demo.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;

import com.pnas.demo.service.LeakUploadService;
import com.pnas.demo.ui.download.dagger2.component.AppComponent;
import com.pnas.demo.ui.download.dagger2.component.DaggerAppComponent;
import com.pnas.demo.ui.download.dagger2.module.AppModule;
import com.pnas.demo.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***********
 * @author pans
 * @date 2015/12/31
 * @describ
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static MyApplication instance;
    private static Handler mHandler = new Handler();
    private static ExecutorService executorService;
    public static int count = 60;
    public List<Activity> activityManager = new ArrayList<>(); // 管理Activity栈
    private static Typeface mTypeface;
    private RefWatcher mRefWatcher;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        LogUtil.isDebug = true;

        // 捕获未捕获异常
//        Thread.setDefaultUncaughtExceptionHandler(this);

        // 激光推送
        /*JPushInterface.init(this);
        JPushInterface.setDebugMode(true);  // 调试模式*/

        umengInit();

        // QQbugly
//        CrashReport.initCrashReport(getApplicationContext(), "900030821", false);

        // LeakCanary初始化
        mRefWatcher = LeakCanary.install(this, LeakUploadService.class);
        // release版本让其无效
//        mRefWatcher = RefWatcher.DISABLED;

    }

    /**
     * 友盟sdk初始化
     */
    private void umengInit() {

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

    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static synchronized ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(3, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
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

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    public static AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(getInstance()))
                    .build();
        }
        return mAppComponent;
    }

    /**
     * 捕获crash的异常信息
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                System.out.println(Thread.currentThread());
                Toast.makeText(getApplicationContext(), "thread=" + thread.getId() +
                        "ex=" + ex.toString(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        SystemClock.sleep(3000);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

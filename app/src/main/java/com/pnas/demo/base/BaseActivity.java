package com.pnas.demo.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Window;

import com.pnas.demo.utils.LogUtil;
import com.pnas.demo.utils.ToastUtil;
import com.pnas.demo.utils.ToolUtils;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 没有标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        MyApplication.getInstance().activityManager.add(this);

    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        // 友盟统计同步
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计同步
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().activityManager.remove(this);

    }

    /******
     * 显示提示框
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showShortToast(msg);
    }

    /***********
     * 日志打印
     * 需要 设置自定义的 TAG 时，重写该方法就可以了
     *
     * @param msg
     */
    public void log(String msg) {
        LogUtil.d(msg);
    }

    /*******
     * 把像素转换成当前屏幕的dip值
     *
     * @param size 像素值
     * @return
     */
    public int pxToDip(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics());
    }

    /*******
     * 把dip转换成当前屏幕的像素值
     *
     * @param dp
     * @return
     */
    private int dipToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    // 判断网络是否可用
    public boolean isNetworkAvailable() {
        return ToolUtils.isNetworkAvailable(this.getApplicationContext());
    }

    // 退出应用
    public void exit() {
        // 关闭栈内所有的Activity
        for (Activity activity : MyApplication.getInstance().activityManager) {
            activity.finish();
        }

        // 友盟同步结束程序线程
        MobclickAgent.onKillProcess(this);

        // 释放相关的引用资源，比如单例类

    }

    /************
     * 跳转
     *
     * @param cls
     */
    public void presentController(Class cls) {
        presentController(cls, null);
    }

    /************
     * 跳转
     *
     * @param cls
     * @param data
     */
    public void presentController(Class cls, Intent data) {
        Intent intent = new Intent(this, cls);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);

        // 第一个参数：进入的
        // 第二个参数：退出的
//        this.overridePendingTransition(R.anim.inside_translate, R.anim.outside_translate);
    }

}

package com.example.pnas.demo.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Window;

import com.example.pnas.demo.utils.LogUtil;
import com.example.pnas.demo.utils.ToastUtil;
import com.example.pnas.demo.utils.ToolUtils;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 没有标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);

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
    }


    /******
     * 显示提示框
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showShortToast(this, msg);
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

}

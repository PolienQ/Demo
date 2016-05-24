package com.example.pnas.demo.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.pnas.demo.base.MyApplication;

/***********
 * @author Ian
 * @date 2015-12-14 11:31
 * @describ 提示框管理类
 */
public class ToastUtil {

    public static void showShortToast(String msg) {
        showShortToast(MyApplication.getInstance(), msg);
    }

    private static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String msg) {
        showLongToast(MyApplication.getInstance(), msg);
    }

    private static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}

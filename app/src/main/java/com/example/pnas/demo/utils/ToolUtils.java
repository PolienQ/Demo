package com.example.pnas.demo.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***********
 * @author Ian
 * @date 2015-12-14 11:31
 * @describ 工具方法管理类
 */
public class ToolUtils {


    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {

                for (int i = 0; i < networkInfo.length; i++) {

                    LogUtil.d("NETWORK", i + "===状态===" + networkInfo[i].getState());
                    LogUtil.d("NETWORK", i + "===类型===" + networkInfo[i].getTypeName());

                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //设置手机屏幕亮度，

    /**
     * 设置手机屏幕亮度
     *
     * @param mActivity,values
     */
    public static void setWindowAlpha(Activity mActivity, float values) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = values;
        mActivity.getWindow().setAttributes(params);
    }

    /**
     * 把 钱 的值 加上,号和小数补全
     *
     * @return 每个月的钱 6,300.00
     */
    public static String formatMoney(String money) {

        StringBuilder builder = new StringBuilder();
        builder.append(money);
        // 获取小数点的角标
        int pointIndex = money.lastIndexOf('.');
        // 判断小数点位置进行补0
        if (pointIndex == -1) {
            builder.append(".00");
            // 重新获取小数点的角标
            pointIndex = builder.toString().lastIndexOf('.');
        } else if (money.substring(pointIndex).length() == 2) {
            // 只有1位小数补0
            builder.append("0");
        } else if (money.substring(pointIndex).length() > 3) {
            // 多于两位小数去掉后面的小数
            builder.delete(pointIndex + 3, builder.length());
        }

        // 初始化x为小数点前一位的角标
        int x = builder.length() - builder.substring(pointIndex).length();
        while (x > 3) {
            x -= 3;
            builder.insert(x, ",");
        }
        // 把 -, 替换为 -
        money = builder.toString();
        if (money.contains("-,")) {
            money = money.replace("-,", "-");
        }

        return money;
    }


    /**
     * 检查字符串是否是邮箱
     *
     * @param strEmail
     * @return 合法：true,不合法：false
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 检查字符串是否是手机号
     *
     * @param phoneNumber
     * @return 合法：true,不合法：false
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    /*************
     * 创建文件夹
     *
     * @param file_name 文件夹名
     * @return 返回创建文件件的路径
     */
    public static String createSDCardPath(String file_name) {
        String file = null;
        // 查看外部储存卡状态是否有外部扩展卡的目录
        if (Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())) {
            // 所在保存在的目录位置
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + file_name;

            LogUtil.d("TOOL", file);

            File path = new File(file);
            // 查看些目录 是否存在，不存在时就创建
            if (!path.exists()) {
                path.mkdirs();
            }
        }
        return file;
    }


    /*************
     * 格式化时间
     *
     * @param time        时间
     * @param format_form 时间格式
     * @return
     */
    public static String formatDate(long time, String format_form) {
        SimpleDateFormat format = new SimpleDateFormat(format_form);

        String name = format.format(time);

        return name;
    }


    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.CHINA);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /*********
     * 判断是否字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {

        if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }

        return true;
    }


    /************
     * 获取设备ID，如果没有就生成一个UUID
     *
     * @param context
     * @return 设备ID 或者 UUID
     */
    public static String getDeviceId(Context context) {
        String deviceId;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        deviceId = tm.getDeviceId();

        if (TextUtils.isEmpty(deviceId)) {
            UUID uuid = UUID.randomUUID();
            deviceId = uuid.toString();
            LogUtil.d("TAG_deviceId", "deviceId->" + deviceId);
        }

        LogUtil.d("TAG_deviceId", "deviceId->" + deviceId);

        return deviceId;
    }

    /************
     * 获取设备名
     *
     * @return 设备名
     */
    public static String getDeviceName() {
        return new Build().MODEL;
    }


    /************
     * 把图片转换成字符串
     *
     * @param bitmap
     * @return 字符串
     */
    public static String convertBitmapToString(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        //初始化一个流对象
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        byte[] buf = output.toByteArray();//转换成功了


        String result = Base64.encodeToString(buf, Base64.DEFAULT);

        return result;
    }

    /************
     * 将日期转化为周几格式
     *
     * @param strDate "2012-12-12"
     * @return 字符串 周一
     */
    public static String getWeekDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /************
     * 将日期只保留月和日
     *
     * @param strDate "2012-12-12"
     * @return 字符串 "12-12"
     */
    public static String changeWeekDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        String str = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
            str = (date.getMonth() + 1) + "-" + date.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /************
     * 比较两个时期大大小
     *
     * @param date1 "2012-12-12 12:12" date2 "2012-12-12 12:12"
     * @return 字符串 "12-12"
     */
    public static boolean compareDate(String date1, String data2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(data2);
            long diff = d1.getTime() - d2.getTime();
            if (diff > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /************
     * 将时间日期往后推1个小时
     *
     * @param date "2012-12-12 12:12" minutes：小时
     * @return 字符串 "12-12"
     */
    public static String pushBackTime(String date, int hours) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = null;
        try {
            Date d = df.parse(date);
            long diff = d.getTime();
            long minu = hours * 60 * 60 * 1000;
            str = df.format(diff + minu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 格式化银行卡号
     *
     * @param cardSno
     * @return
     */
    public static String formatCardNumber(String cardSno) {
        /*StringBuilder builder = new StringBuilder();
        builder.append(cardSno.substring(0, 4));
        builder.append(" **** **** ");
        builder.append(cardSno.substring(cardSno.length() - (cardSno.length() == 16 ? 4 : 3), cardSno.length()));
        return builder.toString();*/
        return cardSno.substring(0, 4) + " **** **** " + cardSno.substring(cardSno.length() -
                (cardSno.length() == 16 ? 4 : 3), cardSno.length());
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(currentFocus.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}


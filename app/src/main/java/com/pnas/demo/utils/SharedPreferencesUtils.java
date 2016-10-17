package com.pnas.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pnas.demo.base.MyApplication;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 *
 * @author xiaanming
 */
public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "Demo_SP";
    private static SharedPreferences sp = MyApplication.getInstance().
            getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static boolean setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();


        switch (type) {
            case "String":
                editor.putString(key, (String) object);
                break;
            case "Integer":
                editor.putInt(key, (Integer) object);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) object);
                break;
            case "Float":
                editor.putFloat(key, (Float) object);
                break;
            case "Long":
                editor.putLong(key, (Long) object);
                break;
        }

        return editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(String key, Object defaultObject) {

        String type = defaultObject.getClass().getSimpleName();

        switch (type) {
            case "String":
                return sp.getString(key, (String) defaultObject);
            case "Integer":
                return sp.getInt(key, (Integer) defaultObject);
            case "Boolean":
                return sp.getBoolean(key, (Boolean) defaultObject);
            case "Float":
                return sp.getFloat(key, (Float) defaultObject);
            case "Long":
                return sp.getLong(key, (Long) defaultObject);
            default:
                return null;
        }

    }

    public static void clear() {
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }
}

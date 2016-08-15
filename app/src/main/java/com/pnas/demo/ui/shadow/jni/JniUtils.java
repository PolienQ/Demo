package com.pnas.demo.ui.shadow.jni;

/***********
 * @author pans
 * @date 2016/8/9
 * @describ
 */
public class JniUtils {

    /**
     * 加载so库或jni库
     */
    static {
        System.loadLibrary("test-jni");
    }

    public static native String get();

    public static native void set(String str);

}

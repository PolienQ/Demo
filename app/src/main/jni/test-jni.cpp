#include <jni.h>
#include <stdio.h>
#include "com_pnas_demo_ui_shadow_jni_JniUtils.h"

/**
 * 实现本地方法对应的的c函数名:Java_包名_类名_本地方法名
 * JNIEnv *env 代表java环境，提供各种函数
 * jobject obj 调用本地方法类的对象
 */
JNIEXPORT jstring JNICALL Java_com_pnas_demo_ui_shadow_jni_JniUtils_get(JNIEnv *env, jclass thiz) {

    char buf[] = "invoke get in c++";//c字符数组保存字符串
    printf("invoke get in c++\n");
    //buf : char *

    //env : JNIEnv * <=> struct JNINativeInterface* *
    // (**env).NewStringUTF();
    // (*env)->NewStringUTf();

    //实现把c提供char *指向的字符串转换为java中String对象
    //jstring     (*NewStringUTF)(JNIEnv*, const char*);
    return env->NewStringUTF(buf);

}

JNIEXPORT void JNICALL Java_com_pnas_demo_ui_shadow_jni_JniUtils_set(JNIEnv *env,jclass thiz, jstring string){

    printf("invoke set in c++\n");
    char* str = (char*)env->GetStringUTFChars(string,NULL);
    printf("%s\n",str);
    char buf[] = "invoke set in c++";//c字符数组保存字符串
    env->ReleaseStringUTFChars(string,buf);

}
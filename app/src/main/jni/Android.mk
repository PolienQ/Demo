LOCAL_PATH := $(call my-dir)    # 指定路径为当前路径

include $(CLEAR_VARS)   # 清除LOCAL_XXX(以前编译过的LOCAL_XXX的数据会造成干扰),除了LOCAL_PATH

LOCAL_MODULE := test-jni         # 指定最终生成的动态库名,生成的结果为 lib(参数).so = libtext-jni.so
LOCAL_SRC_FILES := test-jni.cpp    # 指定生成目标所用到的源文件

include $(BUILD_SHARED_LIBRARY) # 指定最终生成动态库(NDK生成为so文件)
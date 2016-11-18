package com.pnas.demo.service;

import android.util.Log;

import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;

import java.io.File;

/***********
 * @author pans
 * @date 2016/11/14
 * @describ 使用LeakCanary上传内存泄漏log,在LeakCanary初始化时添加这个service
 */
public class LeakUploadService extends DisplayLeakService {

    @Override
    protected void afterDefaultHandling(HeapDump heapDump, AnalysisResult result, String leakInfo) {
        // 如果没找到泄漏 或 该对象排除泄漏
        if (!result.leakFound || result.excludedLeak) {
            return;
        }
        // 如果找到泄漏 或 没被排除泄漏
        uploadLeakBlocking(heapDump.heapDumpFile, leakInfo);
    }

    /**
     * 上传给服务器
     *
     * @param heapDumpFile 泄漏记录文件
     * @param leakInfo     泄漏信息
     */
    private void uploadLeakBlocking(File heapDumpFile, String leakInfo) {
        Log.d("LeakUploadService", leakInfo);
    }
}

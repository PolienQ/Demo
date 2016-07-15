package com.pnas.demo.ui.download;

import okhttp3.MediaType;

/***********
 * @author 彭浩楠
 * @date 2016/7/1
 * @describ
 */
public interface MediaTypes {

    //.*（ 二进制流，不知道下载文件类型）
    MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    // 字符串 或 其他请求
    MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    // 文档?
    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
}

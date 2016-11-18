package com.pnas.demo.constacts;

import android.os.Environment;

/***********
 * @author pans
 * @date 2016/4/21
 * @describ
 */
public interface IConstant {

    String TOMCAT_HTTP = "http://192.168.99.239:8080/";
    String TOMCAT_HTTPS = "https://192.168.99.239:8443/";

    String memPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pans";

    String PIC_URL = "http://nuuneoi.com/uploads/source/playstore/cover.jpg";
    String XML_URL = "http://flash.weather.com.cn/wmaps/xml/china.xml";

    String FILE_IMAGE_PATH = "Demo";

    String UMENG_SOCIAL_SERVICE = "";

    int MSG_FROM_CLIENT = 1029384756;
    int MSG_FROM_SERVICE = 564738291;

}

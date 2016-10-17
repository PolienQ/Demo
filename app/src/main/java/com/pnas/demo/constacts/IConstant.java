package com.pnas.demo.constacts;

import android.os.Environment;

/***********
 * @author pans
 * @date 2016/4/21
 * @describ
 */
public interface IConstant {

    String memPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pans";

    String picPath = "http://nuuneoi.com/uploads/source/playstore/cover.jpg";

    String FILE_IMAGE_PATH = "Demo";

    String UMENG_SOCIAL_SERVICE = "";

    int MSG_FROM_CLIENT = 1029384756;
    int MSG_FROM_SERVICE = 564738291;

}

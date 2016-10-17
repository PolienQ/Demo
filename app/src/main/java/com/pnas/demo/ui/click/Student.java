package com.pnas.demo.ui.click;

import com.pnas.demo.utils.LogUtil;

/***********
 * @author pans
 * @date 2016/10/16
 * @describ
 */
public class Student implements Teacher.MessageObserver {

    @Override
    public void onTeacherMessage(String msg) {
        LogUtil.d(this.getClass().getSimpleName() + "--" + msg);
    }

}

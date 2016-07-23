package com.pnas.demo.ui.timer;

import android.os.CountDownTimer;
import android.widget.Button;

/***********
 * @author pans
 * @date 2016/1/20
 * @describ
 */
public class TimerDemo extends CountDownTimer {

    private Button mSendBtn;

    public TimerDemo(long millisInFuture, long countDownInterval, Button sendBtn) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        mSendBtn = sendBtn;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        mSendBtn.setText("发送验证码");
        mSendBtn.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        mSendBtn.setClickable(false);
        mSendBtn.setText("重新发送 " + millisUntilFinished / 1000);
    }

}

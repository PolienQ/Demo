package com.pnas.demo.ui.shadow.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.pnas.demo.constacts.IConstant;
import com.pnas.demo.utils.LogUtil;

/***********
 * @author pans
 * @date 2016/8/18
 * @describ 使用Messenger进行IPC通信, 底层封装了AIDL;Messenger构造函数的Handler对象负责处理接收到的信息
 * Activity的bindService传入Connection后,会在连接成功回调方法,
 * 回调方法会获得onBind方法返回的Messenger.getBinder方法返回的IBinder对象
 * onServiceConnected方法里把本Service的Messenger返回的IBinder对象创建一个Messenger对象,使用该对象可以发送信息,然后在本Service的Handler处理
 * <p/>
 * handler从Message获得的Messenger是Activity,使用它发送信息可以把信息发到发送的地方
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IConstant.MSG_FROM_CLIENT:
                    // 从发送过来的Message对象中获取Messenger对象,使用该对象把信息发送回去
                    Messenger client = msg.replyTo;
                    LogUtil.d(msg.getData().getString("msg"));
                    Message message = Message.obtain(null, IConstant.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "服务收到消息");
                    message.setData(bundle);
                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        return null;
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

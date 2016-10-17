package com.pnas.demo.ui.shadow.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.pnas.demo.IMyAidlInterface;
import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.constacts.IConstant;
import com.pnas.demo.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/***********
 * @author pans
 * @date 2016/8/3
 * @describ
 */
public class AIDLActivity extends BaseActivity {

    // 创建对象用于接收Service发送回来的数据
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IConstant.MSG_FROM_SERVICE:
                    LogUtil.d("receive msg from service: " + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    /**
     * MessengerService连接的回调对象
     */
    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);
            // 设置客户端的Message信息
            Message message = Message.obtain(null, IConstant.MSG_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "客户端发送的消息");
            message.setData(bundle);
            message.replyTo = mGetReplyMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.aidl_btn1)
    void onClickBtn1() {

        Intent intent = new Intent(this, MessengerService.class);
        // 设置action,启动对应action的服务
//        Intent intent = new Intent("com.pnas.demo.intentservice.action.Messenger");
//        intent.setPackage(getPackageName());    //兼容Android 5.0
        bindService(intent, mMessengerConnection, Context.BIND_AUTO_CREATE);

    }

    private IMyAidlInterface iService;

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iService = IMyAidlInterface.Stub.asInterface(service);

            try {
                String str = iService.getStr();

                int anInt = iService.getInt();


            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iService = null;
        }
    };

    @OnClick(R.id.aidl_btn2)
    void onClickBtn2() {

        final Intent intent = new Intent();
        intent.setClassName(this, "com.pnas.demo.ui.shadow.aidl.AIDLService");
        intent.setPackage("com.pnas.demo.ui.shadow.aidl");
        intent.setAction("com.pnas.demo.ui.shadow.aidl.AIDLService");
        bindService(intent, conn, BIND_AUTO_CREATE);



    }

    @OnClick(R.id.aidl_btn3)
    void onClickBtn3() {

        try {
            showToast(iService.getStr());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.aidl_btn4)
    void onClickBtn4() {
        try {
            showToast("hashcode = " + iService.getInt());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mMessengerConnection);
        unbindService(conn);
    }
}

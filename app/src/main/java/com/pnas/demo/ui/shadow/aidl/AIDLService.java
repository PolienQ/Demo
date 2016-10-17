package com.pnas.demo.ui.shadow.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.pnas.demo.IMyAidlInterface;

/***********
 * @author pans
 * @date 2016/8/17
 * @describ
 */
public class AIDLService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    /**
     * 设置完AIDL文件后重新build一下,再继承接口实现方法
     */
    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getStr() throws RemoteException {
            return "AIDLService";
        }

        @Override
        public int getInt() throws RemoteException {
            return hashCode();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        // 设置前台
        startForeground(startId, null);

        /**
         * 这个整形可以有四个返回值：start_sticky、start_no_sticky、START_REDELIVER_INTENT、START_STICKY_COMPATIBILITY。
         * 它们的含义分别是：
         * 1):START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，
         * 但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，
         * 所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。
         * 如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
         * 2):START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，
         * 服务被异常kill掉，系统不会自动重启该服务
         * 3):START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，
         * 服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
         * 4):START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
         */
        return Service.START_STICKY;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
//        super.onDestroy();
        // 取消前台
        stopForeground(true);
    }
}

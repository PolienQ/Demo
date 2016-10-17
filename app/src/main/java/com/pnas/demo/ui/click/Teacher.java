package com.pnas.demo.ui.click;

import com.pnas.demo.utils.LogUtil;

import java.util.ArrayList;

/***********
 * @author pans
 * @date 2016/10/16
 * @describ
 */
public class Teacher {

    public void publishMessage(String msg) {
        LogUtil.d("开始通知观察者");
        notifyObservers(msg);
    }

    // 定义接口
    public interface MessageObserver {
        void onTeacherMessage(String msg);
    }

    // 存放观察者对象
    private ArrayList<MessageObserver> observers = new ArrayList<>();

    public synchronized void addObserver(MessageObserver observer) {
        if (observer == null) {
            throw new NullPointerException("观察者为空");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public synchronized void removeObserver(MessageObserver observer) {
        if (observer == null) {
            throw new NullPointerException("观察者为空");
        }
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public synchronized void notifyObservers(String msg) {
        for (MessageObserver observer : observers) {
            observer.onTeacherMessage(msg);
        }
    }

}

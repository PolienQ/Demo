package com.pnas.demo.ui.download.rx;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
public class RxjavaActivity extends BaseActivity {

    // 被观察者,订阅 观察者 后会执行Call里面的方法,方法参数是传入的观察者
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }

    });

    Observable JustObservable = Observable.just("Hello", "Hi", "Aloha");

    String[] words = {"Hello", "Hi", "Aloha"};
    Observable FromObservable = Observable.from(words);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.rx_java_btn1)
    void onClickBtn1() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                log("Item: " + s);
            }

            @Override
            public void onCompleted() {
                log("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                log("Error!");
            }
        };

        Subscription subscription = observable.subscribe(observer);
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @OnClick(R.id.rx_java_btn2)
    void onClickBtn2() {
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                showToast("start!  执行前先被调用(当前线程)");
            }

            @Override
            public void onNext(String s) {
                log("Item: " + s);
            }

            @Override
            public void onCompleted() {
                log("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                log("Error!");
            }

        };

        Subscription subscribe = JustObservable.doOnSubscribe(new Action0() {
            @Override
            public void call() {
                showToast("doOnSubscribe 执行前的准备工作(主线程)");
            }
        }).subscribe(subscriber);

        // 如果不需要观察就取消引用
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @OnClick(R.id.rx_java_btn3)
    void onClickBtn3() {

        Action1<String> onNext = new Action1<String>() {

            @Override
            public void call(String s) {
                log("Item: " + s);
            }

        };

        Action0 onComplete = new Action0() {
            @Override
            public void call() {
                log("Completed!");
            }
        };

        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log("Error!");
            }
        };

        Subscription subscribe = FromObservable.subscribe(onNext, onError, onComplete);

        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }

    }

    /**
     * 设置子线程执行,主线程回调
     */
    @OnClick(R.id.rx_java_btn4)
    void onClickBtn4() {
        // Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）
        // Schedulers.computation(): 计算所使用的 Scheduler。

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // 耗时操作
                        subscriber.onNext("1");
                        SystemClock.sleep(4000);
                        subscriber.onNext("2");
                        SystemClock.sleep(4000);
                        subscriber.onNext("3");
                        SystemClock.sleep(4000);
                        subscriber.onNext("4");
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // 主线程回调
                        showToast("Number = " + s);
                    }
                });

    }

    /**
     * 变换,把回调方法的参数进行操作转换
     */
    @OnClick(R.id.rx_java_btn5)
    void onClickBtn5() {

        Observable
//                .just("1")
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        log("被观察者OnSubscribe的线程为 : " + Thread.currentThread().getName());
                        subscriber.onNext("1");
                    }
                })
                .map(new Func1<String, Integer>() { //对上面的onNext的形式参数进行转换
                    @Override
                    public Integer call(String s) {
                        log("map的线程为 : " + Thread.currentThread().getName());
                        return Integer.valueOf(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("onNext的线程为 : " + Thread.currentThread().getName());
                        showToast("Number = " + integer);
                    }
                });

    }

    /**
     * flatMap 需要返回一个Observable<T>被观察者对象,使用被观察者Observable的方法进行返回,
     * 如果用的是just或from会执行传入参数里面的每一个元素的回调onNext方法,
     *
     * @param btn btn对象
     */
    @OnClick(R.id.rx_java_btn6)
    void onClickBtn6(Button btn) {

        // flatMap() 的原理是这样的：1. 使用传入的事件对象创建一个 Observable 对象；
        // 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
        // 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，
        // 而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法。

        class Student {
            public String name;
            public List<String> subject;

            public Student(String name, List<String> subject) {
                this.name = name;
                this.subject = subject;
            }
        }

        ArrayList<String> list = new ArrayList<>();
        list.add("数学");
        list.add("语文");
        Student[] students = {new Student("张三", list), new Student("李四", list)};

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        log("flatMap的线程为 : " + Thread.currentThread().getName());
                        // 把集合(迭代器)作为参数,返回一个被观察者对象,在回调中把集合每一个元素作为参数执行onNext方法
                        return Observable.from(student.subject);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log("onNext的线程为 : " + Thread.currentThread().getName());
                        showToast("遍历的学科 : " + s);
                    }
                });

    }

    @OnClick(R.id.rx_java_btn7)
    void onClickBtn7() {

        // 对多个被观察者进行外置变换

        class LiftAllTransformer implements Observable.Transformer<String, Integer> {
            @Override
            public Observable<Integer> call(Observable<String> observable) {
                return observable.map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String string) {
                        return string.hashCode();
                    }
                });
            }
        }

        LiftAllTransformer transformer = new LiftAllTransformer();
        observable.compose(transformer).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                showToast("observable - onNext == " + i);
            }
        });

        JustObservable.compose(transformer).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                showToast("JustObservable - onNext -- " + i);
            }
        });

    }

    @OnClick(R.id.rx_java_btn8)
    void onClickBtn8() {

        JustObservable
                .map(new Func1<String, char[]>() {
                    @Override
                    public char[] call(String s) {
                        log("first map currentThread == " + Thread.currentThread().getName());
                        return s.toCharArray();
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Func1<char[], String>() {
                    @Override
                    public String call(char[] chars) {
                        log("second map currentThread == " + Thread.currentThread().getName());
                        return new String(chars);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        showToast(s);
                    }
                });

    }

    @OnClick(R.id.rx_java_btn9)
    void onClickBtn9() {

    }
}

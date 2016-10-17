package com.pnas.demo.ui.click;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.HttpUrl;

/***********
 * @author pans
 * @date 2016/8/30
 * @describ
 */
public class ClickActivity extends BaseActivity {

    private FragmentManager mManager;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        ButterKnife.bind(this);

        mManager = getSupportFragmentManager();
    }

    @OnClick(R.id.click_btn_observer)
    void clickObserver() {

        Teacher teacher = new Teacher();
        Student student = new Student();

        teacher.addObserver(student);

        teacher.publishMessage("通知所有观察者");

    }

    @OnClick(R.id.click_btn_proxy)
    void clickProxy() {

        final ArrayList<String> arrayList = new ArrayList<>();

        Collection proxyList = (Collection) Proxy.newProxyInstance(
                Collection.class.getClassLoader(), new Class[]{Collection.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(arrayList, args);
                    }
                });

        proxyList.add("Object1");
        proxyList.add("Object2");
        proxyList.add("Object3");
        showToast(proxyList.toString());

    }

    @OnClick(R.id.click_btn_fragment)
    void clickFragment() {
        FragmentTransaction beginTransaction = mManager.beginTransaction();
//        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        beginTransaction.setCustomAnimations(R.anim.dialog_comment_enter, R.anim.dialog_comment_exit);
        Fragment fragment = mManager.findFragmentByTag("fragment" + count);
        if (fragment != null) {
            beginTransaction.hide(fragment);
        }
        beginTransaction.add(R.id.click_frame, ClickFragment.newInstance(count++), "fragment" + count);
        beginTransaction.addToBackStack("fragment" + count);
        beginTransaction.commit();

    }

    @OnClick(R.id.click_btn_clear)
    void clickClear() {

        while (mManager.popBackStackImmediate()) {

        }
        count = 0;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        count--;
    }
}

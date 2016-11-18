package com.pnas.demo.ui.art;

import android.os.Bundle;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********
 * @author pans
 * @date 2016/10/20
 * @describ
 */
public class ViewActivity extends BaseActivity {

    @BindView(R.id.art_view)
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }
}

package com.pnas.demo.ui.download.dagger2;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.ui.download.dagger2.component.DaggerDagger2Component;
import com.pnas.demo.ui.download.dagger2.module.Dagger2Module;

import javax.inject.Inject;
import javax.inject.Named;

/***********
 * @author pans
 * @date 2016/8/8
 * @describ
 */
public class Dagger2Activity extends BaseActivity {

    private LinearLayout mLinearLayout;

    @Inject
    @Named("text_view")
    TextView mTextView;

    @Inject
    @Named("big_text_view")
    TextView mBigTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);

        DaggerDagger2Component.builder()
                .dagger2Module(new Dagger2Module(this))
                .build().inject(this);

        initView();

    }

    private void initView() {

        mLinearLayout = ((LinearLayout) findViewById(R.id.dagger2_container));
        mLinearLayout.addView(mTextView);

        mLinearLayout.addView(mBigTextView);

    }

}

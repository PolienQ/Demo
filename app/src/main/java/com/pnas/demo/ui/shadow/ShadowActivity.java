package com.pnas.demo.ui.shadow;

import android.os.Bundle;
import android.view.View;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

/***********
 * @author pans
 * @date 2016/3/18
 * @describ
 */
public class ShadowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);

        initView();
    }

    private void initView() {
        
    }

    public void clickJni(View view) {

        presentController(JniActivity.class);

    }

    public void clickAIDL(View view) {

        presentController(AIDLActivity.class);

    }
}

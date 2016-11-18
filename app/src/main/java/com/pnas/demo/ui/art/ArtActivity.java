package com.pnas.demo.ui.art;

import android.os.Bundle;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/***********
 * @author pans
 * @date 2016/10/20
 * @describ
 */
public class ArtActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art);
        ButterKnife.bind(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @OnClick(R.id.art_btn_view)
    void clickView() {

        presentController(ViewActivity.class);

    }

}

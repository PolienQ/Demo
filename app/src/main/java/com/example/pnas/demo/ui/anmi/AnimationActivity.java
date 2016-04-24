package com.example.pnas.demo.ui.anmi;

import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;

/***********
 * @author 彭浩楠
 * @date 2016/2/26
 * @describ
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {

        mButton = ((Button) findViewById(R.id.animation_btn));

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.animation_btn:

                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setInterpolator(new BounceInterpolator());

                mButton.startAnimation(scaleAnimation);

                break;
        }

    }
}

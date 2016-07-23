package com.pnas.demo.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.Log;

/***********
 * @author pans
 * @date 2016/4/20
 * @describ
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initView();
        initData();
        initEvent();

    }

    private void initView() {
        mButton = ((Button) findViewById(R.id.share_btn));

        findViewById(R.id.share_btn_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentController(AuthActivity.class);
            }
        });
    }

    private void initData() {

    }

    private void initEvent() {
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        UMImage image = new UMImage(ShareActivity.this, "http://www.umeng.com/images/pic/social/integrated_3.png");
        UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
        music.setTitle("This is music title");
        music.setThumb(new UMImage(ShareActivity.this, "http://www.umeng.com/images/pic/social/chart_1.png"));
        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
        String url = "http://www.umeng.com";

        switch (v.getId()) {
            case R.id.share_btn:

                /**shareboard  need the platform all you want and callbacklistener,then open it**/
                new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.EMAIL)
                        .withText("来自友盟分享面板")
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .open();

                break;
        }

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}

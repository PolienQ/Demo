package com.example.pnas.demo.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pnas.demo.R;
import com.example.pnas.demo.base.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.view.UMFriendListener;

import java.util.Map;

/***********
 * @author 彭浩楠
 * @date 2016/5/17
 * @describ
 */
public class AuthActivity extends BaseActivity {

    private UMShareAPI mShareAPI = null;

    /**
     * 点击 开始授权
     *
     * @param view
     */
    public void onClickAuth(View view) {
        SHARE_MEDIA platform = null;
        if (view.getId() == R.id.app_auth_sina) {
            platform = SHARE_MEDIA.SINA;
        } else if (view.getId() == R.id.app_auth_qq) {
            platform = SHARE_MEDIA.QQ;
        } else if (view.getId() == R.id.app_auth_weixin) {
            platform = SHARE_MEDIA.WEIXIN;
        }
        /**begin invoke umeng api**/

        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }

    /**
     * 点击消除对应的授权信息
     *
     * @param view
     */
    public void onClickDeletAuth(View view) {

        SHARE_MEDIA platform = null;
        if (view.getId() == R.id.app_del_auth_sina) {
            platform = SHARE_MEDIA.SINA;
        } else if (view.getId() == R.id.app_del_auth_qq) {
            platform = SHARE_MEDIA.QQ;
        } else if (view.getId() == R.id.app_del_auth_weixin) {
            platform = SHARE_MEDIA.WEIXIN;
        }
        /**begin invoke umeng api**/
        mShareAPI.deleteOauth(this, platform, umdelAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        /** init auth api**/
        mShareAPI = UMShareAPI.get(this);

        initView();
        initData();
        initEvent();
    }

    private void initView() {

    }

    private void initData() {

    }

    private void initEvent() {

    }

    /**
     * 获取新浪好友列表
     *
     * @param view
     */
    public void getFriendbyClick(View view) {

        mShareAPI.getFriend(this, SHARE_MEDIA.SINA, umGetfriendListener);

    }

    /**
     * auth callback interface
     * 授权结果回调
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed(授权成功)", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail(授权失败)", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel(取消授权)", Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * delauth callback interface
     * 清除授权结果回调
     **/
    private UMAuthListener umdelAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "delete Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "delete Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "delete Authorize cancel", Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * 新浪微博--获取好友列表的回调
     */
    private UMFriendListener umGetfriendListener = new UMFriendListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, Object> data) {
            if (data != null) {
                Toast.makeText(getApplicationContext(), data.get("json").toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("auth", "on activity re 3");
    }

}

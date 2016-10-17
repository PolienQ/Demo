package com.pnas.demo.ui.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;

import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********
 * @author pans
 * @date 2016/8/15
 * @describ 与JS进行交互:1,在WebView的 WebViewClient 中shouldOverrideUrlLoading方法对url字符串判断协议进行操作
 * 2,WebView.addJavascriptInterface(this, "AndroidFunction"),使用@JavascriptInterface注解方法
 * 3,WebView.addJavascriptInterface(new myJavaScriptInterface(), "AndroidFunction"),API大于等于17要加上注解
 * -->4,mWebView.loadUrl("javascript:wave()");调用js中的wave()方法
 * <p/>
 * setWebViewClient设置验证,加载状态回调,拦截
 * setWebChromeClient设置进度条,标题,图标
 */
public class WebActivity extends BaseActivity {

    private static final String fileName[] = {"JSDemo.html"};
    private static final String base = "file:///android_asset/html/";
    private String mTitle;

    @BindView(R.id.web_btn_container)
    LinearLayout btnContainer;

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        boolean flag = getIntent().getBooleanExtra("flag", false);
        if (flag) {
            initWebView();
        }

    }

    private void initWebView() {
        mWebView.setVisibility(View.VISIBLE);
        btnContainer.setVisibility(View.GONE);

        int number = getIntent().getIntExtra("number", 0);

        initWebSettings();
        initWebClient();

//        mWebView.addJavascriptInterface(this, "AndroidFunction");
        mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "AndroidFunction");
        mWebView.loadUrl(base + fileName[number]);
    }

    // 初始化WebViewSettings
    private void initWebSettings() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
    }

    private void initWebClient() {
        // 限制在WebView中打开网页，而不用默认浏览器
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                // 忽略SSL验证
                handler.proceed();
//                handler.cancel();
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 进行拦截URL
//                return isIntercept(view, url);

                // 设置好定义好的拦截协议
                if (url.contains("wfy:")) {
                    String data = url.split("wfy:")[1];

                    data = decodeURL(data);
                    // 根据协议后面的字符串操作
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mTitle = view.getTitle();

                log("TITLE=" + mTitle);

//                if (isShow) {
//                    // 更新新标题
//                    updateTitle(title);
//                }
            }
        });

        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

//                log("Progress:" + newProgress);
                // 这里将textView换成你的progress来设置进度
                /*if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                if (newProgress == 90) {
                    view.loadUrl("javascript:$('#header').remove();");
                }

                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                    view.loadUrl("javascript:$('#header').remove();");
                }

                progressBar.setProgress(newProgress);
                progressBar.postInvalidate();*/
            }

        });
    }

    /********
     * URL解码
     *
     * @param data
     * @return
     */

    protected String decodeURL(String data) {
        String result = null;
        try {
            result = URLDecoder.decode(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /*********
     * URL编码
     *
     * @param data
     * @return
     */
    protected String encodeURL(String data) {
        String result = null;
        try {
            result = URLEncoder.encode(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @JavascriptInterface
    public String getUserInfo() {

        return "1234";
    }

    final class MyJavaScriptInterface {

        // 如果target 大于等于API 17，则需要加上如下注解
        @JavascriptInterface
        public String getUserInfo() {

            return "1234";
        }

    }

    private void presentController(int number) {
        Intent intent = new Intent();
        intent.putExtra("flag", true);
        intent.putExtra("number", number);
        presentController(WebActivity.class, intent);
    }

    @OnClick(R.id.web_btn1)
    void onClickBtn1() {
        presentController(0);
    }

    @OnClick(R.id.web_btn2)
    void onClickBtn2() {
        presentController(1);
    }

}

package com.pnas.demo.ui.download.https;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.constacts.IConstant;
import com.pnas.demo.utils.HttpsUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/***********
 * @author pans
 * @date 2016/11/12
 * @describ 在application中添加证书
 * 证书可以浏览器上导出，导时选择X509，导出结果的xxx.cer
 */
public class HttpsActivity extends BaseActivity {

    @BindView(R.id.https_tv_status)
    TextView mTvStatus;
    @BindView(R.id.https_tv_content)
    TextView mTvContent;

    private OkHttpClient mOkHttpClient;

    // 证书数据
    private static List<byte[]> CERTIFICATES_DATA = new ArrayList<>();
    private InputStream mBksFile;
    private InputStream[] mCertificates;
    private HttpsUtils.SSLParams mSslParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        ButterKnife.bind(this);

        initCerAndBks();
        initOkHttp();
    }

    private void setStatus(String content) {
        mTvStatus.setText(content);
    }

    /**
     * 在application中初始化获取证书
     */
    private void initCerAndBks() {

        try {
            // 获取所有的证书名
            String[] certses = getAssets().list("certs");
            if (certses != null) {
                int num = 0;
                mCertificates = new InputStream[certses.length];
                for (String cert : certses) {
                    log("cert  = " + cert);
                    mCertificates[num++] = getAssets().open("certs/" + cert);
//                    InputStream is = getAssets().open("certs/" + cert);
                    // 将证书读取出来,放在配置中的byte[]中
//                    addCertificate(is);
                }
            }

            // 获取所有的bks签名
            String[] bkses = getAssets().list("bks");
            if (certses != null) {
                for (String bks : bkses) {
                    mBksFile = getAssets().open("bks/" + bks);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存证书到集合中
     *
     * @param is
     */
    private void addCertificate(InputStream is) {

        log("#addCertificate inputStream = " + is);
        if (is != null) {

            try {

                // 把读取到的多段证书数据放到集合中
                int ava = 0;  //数据可读长度
                int len = 0;  //数据总长度
                ArrayList<byte[]> data = new ArrayList<>();
                while ((ava = is.available()) > 0) {
                    byte[] buffer = new byte[ava];
                    is.read(buffer);
                    data.add(buffer);
                    len += ava;
                }

                // 把集合中多段的字节数组合并到一个数组中,存到证书集合中
                byte[] buffer = new byte[len];
                int dstPos = 0;
                for (byte[] bytes : data) {
                    int length = bytes.length;
                    // 复制数组 ( 源数组 , 源数组起始位 , 目标数组 , 目标数组起始位 , 复制的长度 )
                    System.arraycopy(bytes, 0, buffer, dstPos, length);
                    dstPos += length;
                }

                CERTIFICATES_DATA.add(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void initOkHttp() {

        /*// 添加证书
        ArrayList<InputStream> isList = new ArrayList<>();

        InputStream[] certificates = null;

        if (CERTIFICATES_DATA != null && !CERTIFICATES_DATA.isEmpty()) {
            certificates = new ByteArrayInputStream[CERTIFICATES_DATA.size()];
            for (int x = 0; x < CERTIFICATES_DATA.size(); x++) {
//                isList.add(new ByteArrayInputStream(bytes));
                try {
                    certificates[x].read(CERTIFICATES_DATA.get(x));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

        try {

            mSslParams = HttpsUtils.getSslSocketFactory(mCertificates, mBksFile, "android");

            mOkHttpClient = new OkHttpClient.Builder()
                    // 添加证书和签名
                    .sslSocketFactory(mSslParams.sSLSocketFactory, mSslParams.trustManager)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.https_btn_get)
    public void onClickGet() {

        setStatus("get开始");

        Request request = new Request.Builder()
                .get()
                .url(IConstant.TOMCAT_HTTPS + "test/myhtml.html")
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                showToast("请求失败");
                log("okhttp异常信息: \r\n" + e.getMessage());

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                showToast("请求成功");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvContent.setText(response.toString());
                    }
                });

            }
        });

        setStatus("无连接");

    }

    @OnClick(R.id.https_btn_http)
    public void onClickHttp() {

        setStatus("http测试开始");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(IConstant.TOMCAT_HTTP + "test/myhtml.html");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);

                    int code = connection.getResponseCode();
                    showToast(code == 200 ? "连接成功" : "连接失败");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setStatus("无连接");
                    }
                });

            }
        }).start();
    }

    @OnClick(R.id.https_btn_https)
    public void onClickHttps() {

        setStatus("http测试开始");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(IConstant.TOMCAT_HTTPS + "test/myhtml.html");

                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                    connection.setSSLSocketFactory(mSslParams.sSLSocketFactory);
                    connection.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            log("hostname = " + hostname);
                            return "192.168.99.239".equals(hostname);
                        }
                    });

                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);

                    int code = connection.getResponseCode();
                    if (code == 200) {
                        showToast("连接成功");

                        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                        BufferedReader br = new BufferedReader(reader);
                        final StringBuilder sb = new StringBuilder();
                        String temp;
                        while ((temp = br.readLine()) != null) {
                            sb.append(temp);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvContent.setText(sb.toString());
                            }
                        });
                    } else {
                        showToast("连接失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setStatus("无连接");
                    }
                });

            }
        }).start();
    }
}

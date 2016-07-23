package com.pnas.demo.ui.download.okhttp;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.pnas.demo.base.MyApplication;
import com.pnas.demo.utils.HttpsUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/***********
 * @author pans
 * @date 2016/7/1
 * @describ
 */
public class OkHttpUtils {

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {

            // 指定缓存路径,缓存大小100Mb
            Cache cache = new Cache(new File(MyApplication.getInstance().getCacheDir(), "HttpCache"),
                    1024 * 1024 * 100);

            ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(MyApplication.getInstance()));

            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggerInterceptor(""))
                    .cookieJar(cookieJar1)
                    // 允许所有证书
                    .hostnameVerifier(new HttpsUtils.UnSafeHostnameVerifier())
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    // 注册验证器 设置https证书
                    /*.authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            System.out.println("Authenticating for response: " + response);
                            System.out.println("Challenges: " + response.challenges());
                            String credential = Credentials.basic("jesse", "password1");
                            return response.request().newBuilder()
                                    .header("Authorization", credential)
                                    .build();
                        }
                    })*/
                    .build();
        }
        return okHttpClient;

    }

    public void cancelTag(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

}

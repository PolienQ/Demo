package com.pnas.demo.ui.download.okhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/***********
 * @author pans
 * @date 2016/6/27
 * @describ
 */
public class OkhttpRequestMethods {

    private static String token = "";

    public static void request_01() {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        if (token == null /*|| alreadyHasAuthorizationHeader(originalRequest)*/) {
                            return chain.proceed(originalRequest);
                        }
                        Request authorised = originalRequest.newBuilder()
                                .header("Authorization", token)
                                .build();
                        return chain.proceed(authorised);
                    }
                })
                .build();
    }
}

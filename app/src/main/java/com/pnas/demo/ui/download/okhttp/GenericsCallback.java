package com.pnas.demo.ui.download.okhttp;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/***********
 * @author 彭浩楠
 * @date 2016/7/1
 * @describ
 */
public abstract class GenericsCallback<T> {

    Gson mGson = new Gson();

    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = mGson.fromJson(string, entityClass);
        return bean;
    }

}

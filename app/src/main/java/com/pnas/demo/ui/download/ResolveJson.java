package com.pnas.demo.ui.download;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/***********
 * @author pans
 * @date 2016/7/1
 * @describ
 */
public class ResolveJson {

    public static Object resolve(String json, Class clazz, Type... args) {
        Gson gson = new Gson();
        // 未知参数
        ParameterizedType type = type(clazz, args);
        return gson.fromJson(json, type);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}

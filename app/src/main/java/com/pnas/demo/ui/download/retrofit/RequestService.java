package com.pnas.demo.ui.download.retrofit;

import com.pnas.demo.entity.retrofit.NewsDetail;
import com.pnas.demo.entity.retrofit.NewsList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/***********
 * @author pans
 * @date 2016/6/27
 * @describ
 */
public interface RequestService {

    /**
     * 完整的url会忽略掉baseUrl
     */
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("http://news-at.zhihu.com/api/4/stories/latest")
    Observable<NewsList> getLatestNews();

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
    @GET("stories/before/{date}")
    Observable<NewsList> getBeforeNews(@Path("date") String date);

    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_LONG)
    @GET("story/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    /**
     * baseUrl + story/8536302 的get请求
     */
    @GET("story/8536302")
    Observable<NewsDetail> getDemo1();

    /**
     * baseUrl +                                 story/参数1/参数2
     * http://192.168.1.102:8080/springmvc_users/user/zhy
     */
    @GET("{attr1}/{attr2}")
    Call<NewsDetail> getDemo2(@Path("attr1") String attr1, @Path("attr2") int attr2);

    /**
     * baseUrl +                                 story/?sortby={sort}
     * http://192.168.1.102:8080/springmvc_users/user/zhy
     */
    @GET("story")
    Observable<NewsDetail> getDemo3(@Query("sortby") String sort);
}

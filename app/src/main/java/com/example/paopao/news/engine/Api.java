package com.example.paopao.news.engine;

import com.example.paopao.news.domain.NewsBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;



/**
 * Created by paopao on 2018/10/5.
 */

public interface Api {
//    @GET("toutiao/index")
//    Observer<NewsBean> getNews(@Query("key")String key,@Query("type")String type);
    @GET("toutiao/index")
    Observable<NewsBean> getNews(@Query("key")String key, @Query("type")String type);
}

package com.python.cat.studyview.net;

import com.python.cat.studyview.bean.ArticleBean;
import com.python.cat.studyview.bean.BannerBean;
import com.python.cat.studyview.bean.FriendBean;
import com.python.cat.studyview.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by cat on 2018/6/16.
 * wan android api
 */

public interface WanService {

    @GET("article/list/{id}/json")
//    @HTTP(method = "GET", path = "article/list/{id}/json") // 这种也可以
    Observable<ArticleBean> getArticles(@Path("id") int id);


    @GET("banner/json")
    Observable<BannerBean> getBanners();
//    Flowable<BannerBean> getFriends();  // retrofit 并不支持 Flowable

    @GET("friend/json")
    Observable<FriendBean> getFriends();

    /**
     * https://square.github.io/retrofit/
     * Each key-value pair is annotated with @Field containing
     * the name and the object providing the value.
     * *
     *
     * @param username user name
     * @param password password
     * @return 登陆数据
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username,
                                @Field("password") String password);


}

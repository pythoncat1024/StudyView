package com.python.cat.studyview.net;

import android.support.annotation.NonNull;

import com.python.cat.studyview.bean.ArticleBean;
import com.python.cat.studyview.bean.BannerBean;
import com.python.cat.studyview.bean.FriendBean;
import com.python.cat.studyview.bean.LoginBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @from: https://www.jianshu.com/p/308f3c54abdd
 * <p>
 * Created by cat on 2018/6/15.
 * 网络请求
 * <p>
 * http://www.wanandroid.com/article/list/0/json
 */

public class HttpRequest {

    private static final String BASE_URL = "http://www.wanandroid.com/";

    private HttpRequest() {
    }

    /**
     * 首页
     *
     * @param id page id
     */
    public static Observable<ArticleBean> article(int id) {

        Retrofit retrofit = getRetrofit();

        ArticleService service = retrofit.create(ArticleService.class);
        return service.getArticles(0)
                .subscribeOn(Schedulers.io());

    }

    /**
     * banner 内容
     */
    public static Observable<BannerBean> banner() {
        return getRetrofit().create(BannerService.class)
                .getBanners()
                .subscribeOn(Schedulers.io());

    }


    /**
     * 常用网站
     */
    public static Observable<FriendBean> friend() {
        return getRetrofit().create(FriendsService.class)
                .getBanners()
                .subscribeOn(Schedulers.io());

    }

    public static Observable<LoginBean> login(String user, String passwd) {
        return getRetrofit().create(LoginService.class)
                .login(user, passwd)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // 针对rxjava2.x
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}

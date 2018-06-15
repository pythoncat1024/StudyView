package com.python.cat.studyview.net;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.bean.ArticleBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by cat on 2018/6/15.
 * 网络请求
 * <p>
 * http://www.wanandroid.com/article/list/0/json
 */

public class HttpRequest {

    private static final String BASE_URL = "http://www.wanandroid.com/";

    private HttpRequest() {
    }

    public static Observable<ArticleBean> article(int id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // 针对rxjava2.x
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ArticleBeanInterface service = retrofit.create(ArticleBeanInterface.class);
        return service.getArticles(0)
                .subscribeOn(Schedulers.io());



    }
}

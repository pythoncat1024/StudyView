package com.python.cat.studyview.net;

import com.python.cat.studyview.bean.BannerBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by cat on 2018/6/15.
 * http://www.wanandroid.com/banner/json
 * <p>
 * 方法：GET
 * 参数：无
 */

interface BannerService {

    @GET("banner/json")
    Observable<BannerBean> getBanners();
//    Flowable<BannerBean> getBanners();  // retrofit 并不支持 Flowable
}

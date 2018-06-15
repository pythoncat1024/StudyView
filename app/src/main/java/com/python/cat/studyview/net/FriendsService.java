package com.python.cat.studyview.net;

import com.python.cat.studyview.bean.FriendBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by cat on 2018/6/15.
 * http://www.wanandroid.com/friend/json
 */

public interface FriendsService {

    @GET("friend/json")
    Observable<FriendBean> getBanners();
}

package com.python.cat.studyview.net;

import com.python.cat.studyview.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cat on 2018/6/16.
 * * http://www.wanandroid.com/user/login
 * <p>
 * 方法：POST
 * 参数：
 * username，password
 */

interface LoginService {

    /*
    不是这样玩的。。。
    @Multipart
    @POST("user/login")
    Observable<LoginBean> login(@Part("username") String user, @Part("password") String passwd);
    */


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

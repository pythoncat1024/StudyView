package com.python.cat.studyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.bean.ArticleBean;
import com.python.cat.studyview.bean.BannerBean;
import com.python.cat.studyview.bean.FriendBean;
import com.python.cat.studyview.net.HttpRequest;
import com.python.cat.studyview.utils.ToastUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        LogUtils.w("---- 去执行网络请求吧 -------");
//        requestArticles();
//        requestBanner();
        requestFriend();

    }

    private void requestBanner() {
        HttpRequest.banner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        LogUtils.w(bannerBean);
                        ToastUtils.show(getApplicationContext(), bannerBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.w("complete...");
                    }
                });
    }

    private void requestFriend() {
        HttpRequest.friend()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(FriendBean friendBean) {
                        LogUtils.w(friendBean);
                        ToastUtils.show(getApplicationContext(), friendBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.w("complete...");
                    }
                });
    }

    private void requestArticles() {
        HttpRequest.article(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        LogUtils.w(articleBean);

                        LogUtils.w("数据加载完成了，可以填充到 view 里面去了~");
                        LogUtils.w("current thread info: "
                                + Thread.currentThread().getName()
                                + ":" + Thread.currentThread().getId());

                        ToastUtils.show(getApplicationContext(), articleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e);
                    }

                    @Override
                    public void onComplete() {

                        LogUtils.w("complete.....");
                    }
                });
    }
}

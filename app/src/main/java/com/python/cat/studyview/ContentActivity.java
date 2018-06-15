package com.python.cat.studyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.bean.ArticleBean;
import com.python.cat.studyview.net.HttpRequest;
import com.python.cat.studyview.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        LogUtils.w("---- 去执行网络请求吧 -------");
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

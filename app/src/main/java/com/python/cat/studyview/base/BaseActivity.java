package com.python.cat.studyview.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.utils.Memo;

/**
 * Created by cat on 2018/6/15.
 * base activity
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getClass().getSimpleName());
        LogUtils.i(getClass().getSimpleName() + "#onCreate...");
        Memo.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(getClass().getSimpleName() + "#onDestroy...");
        Memo.remove(this);
    }


    protected AppCompatActivity getActivity() {
        if (isDestroyed() || isFinishing()) {
            return null;
        }
        return this;
    }
}

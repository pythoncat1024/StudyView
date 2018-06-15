package com.python.cat.studyview.base;

import android.app.Activity;
import android.app.Application;

import com.python.cat.studyview.utils.Memo;

/**
 * Created by cat on 2018/6/15.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity ac : Memo.getMemo()) {
            ac.finish();
        }
        Memo.clear();
    }
}

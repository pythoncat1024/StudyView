package com.python.cat.studyview;

import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;

public class RectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);
        LogUtils.w("create...");
    }
}

package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.view.BottomImage;

public class RectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);
        LogUtils.w("create...");

        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.w("next click...");
                        startActivity(new Intent(getActivity(), BottomImageActivity.class));
                    }
                });
    }
}

package com.python.cat.studyview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.view.BottomImage;
import com.python.cat.studyview.view.RectView;

public class RectActivity extends BaseActivity {

    private BottomImage bim;
    private RectView rectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);

//        bim = findViewById(R.id.bottom_img);
        rectView = findViewById(R.id.rect_view);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}

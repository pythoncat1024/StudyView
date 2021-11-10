package com.python.cat.studyview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.python.cat.studyview.base.BaseActivity;

public class WatermarkActivity extends BaseActivity {

    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);
        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), RectActivity.class));
                    }
                });

        v = findViewById(R.id.wm_img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int width = v.getWidth();
        int height = v.getHeight();
        ObjectAnimator oa = ObjectAnimator
                .ofFloat(v, "translationX", 0, 500)
                .setDuration(1000);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.start();

    }
}

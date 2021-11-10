package com.python.cat.studyview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;

/**
 * for round image view
 */
public class RoundActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        View view = findViewById(R.id.round_img);

        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "rotation", 0, 360);

        oa.setRepeatCount(-1);
        oa.setInterpolator(new LinearInterpolator());
        oa.setDuration(2000);
        oa.start();

        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.w("click 2 next");
                        Intent intent = new Intent(getActivity(),
                                WatermarkActivity.class);
                        startActivity(intent);
                    }
                });
    }


}

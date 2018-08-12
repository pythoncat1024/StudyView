package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;

/**
 * 图表界面(for: luo wei di)
 */
public class TableActivity extends BaseActivity {

    private View tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.w("next...");
                        startActivity(new Intent(getActivity(), RoundActivity.class));
                    }
                });

        tabView = findViewById(R.id.tb_view);
                tabView.animate()
                        .rotation(360)
//                        .scaleX(0.5f)
//                        .scaleY(0.5f)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
//                        .translationY(500)
                        .setDuration(1000)
                .start();

    }
}

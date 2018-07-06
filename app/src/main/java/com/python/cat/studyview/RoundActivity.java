package com.python.cat.studyview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

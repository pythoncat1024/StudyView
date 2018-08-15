package com.python.cat.studyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.apkfuns.logutils.LogUtils;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        final TimePicker tpv = findViewById(R.id.tp_view);

        final TimePicker tpv2 = findViewById(R.id.tp_view2);


        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e("---------tpv1----------");
                        printStackViews(tpv);
                        LogUtils.e("---------tpv2----------");
                        printStackViews(tpv2);

                        tpv2.setIs24HourView(!tpv2.is24HourView());
                    }
                });
    }


    private void printStackViews(View vg) {
        if (vg instanceof ViewGroup) {
            ViewGroup vgg = (ViewGroup) vg;
            for (int i = 0; i < vgg.getChildCount(); i++) {
                View child = vgg.getChildAt(i);
                printStackViews(child);
            }
        } else {
            LogUtils.i(vg);
        }
    }
}

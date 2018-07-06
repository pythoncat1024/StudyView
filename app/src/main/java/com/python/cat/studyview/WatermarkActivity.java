package com.python.cat.studyview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WatermarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);
        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }
}

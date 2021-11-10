package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.python.cat.studyview.base.BaseActivity;

public class BottomImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_image);
        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), NpActivity.class));
                    }

                });

    }
}

package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.view.NumberPickerD;

public class NpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_np);

        final String[] display = new String[]{
                "python", "java", "php", "ruby", "node", "perl", "dart"
        };
        NumberPickerD npd = findViewById(R.id.npd_view);
        npd.setMaxValue(display.length - 1);
        npd.setMinValue(0);
        final int minValue = npd.getMinValue();
        npd.setValue(display.length / 2);
        npd.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                LogUtils.e("value==" + value);
                return display[value - minValue];
            }
        });
//        npd.setSelectionDivider(new ColorDrawable(Color.YELLOW));

        npd.setWrapSelectorWheel(true); // 是否循环显示内容

        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TimeActivity.class));
                    }
                });
    }
}

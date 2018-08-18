package com.python.cat.studyview;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.python.cat.studyview.base.BaseActivity;

public class PickerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        WheelDatePicker wp = findViewById(R.id.wp_view);
        wp.setIndicator(true);
        wp.setIndicatorColor(Color.GRAY);
//        wp.setItemAlignYear(WheelPicker.ALIGN_LEFT);
//        wp.setItemAlignDay(WheelPicker.ALIGN_RIGHT);
        wp.setAtmospheric(true);
        wp.setCurved(true);
        wp.setCyclic(true);
//        wp.setItemSpace(300); // y 方向的
//        wp.setCurtain(true);
        wp.setSelectedItemTextColor(Color.BLACK);
    }

}

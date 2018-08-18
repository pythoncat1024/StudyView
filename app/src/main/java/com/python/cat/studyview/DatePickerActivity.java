package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.view.NumberPickerD;

import java.util.ArrayList;
import java.util.Calendar;

public class DatePickerActivity extends BaseActivity {

    private ViewGroup datePickerLayout;
    private NumberPickerD npYear;
    private NumberPickerD npMonth;
    private NumberPickerD npDay;
    private TextView tvCancel;
    private TextView tvFinish;
    private ViewGroup rootView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        rootView = findViewById(R.id.root_view);
        findViewById(R.id.btn_show_picker)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e("显示选择器。。。");
                        show();
                    }
                });
        initDateLayout();
        findViewById(R.id.btn_next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pi = new Intent(getActivity(), PickerActivity.class);
                        startActivity(pi);
                    }
                });

    }

    private void initDateLayout() {
        datePickerLayout = findViewById(R.id.date_layout);
        datePickerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        npYear = findViewById(R.id.np_year);
        npMonth = findViewById(R.id.np_month);
        npDay = findViewById(R.id.np_day);
        tvCancel = findViewById(R.id.date_picker_cancel);
        tvFinish = findViewById(R.id.date_picker_finish);
        datePickerLayout.setVisibility(View.GONE);
        Calendar current = Calendar.getInstance();
        String currentYear = current.get(Calendar.YEAR) + "年";
        String currentMonth = current.get(Calendar.MONTH) + "月";
        String currentDay = current.get(Calendar.DAY_OF_MONTH) + "日";
        Calendar start = Calendar.getInstance();
        start.set(2012, 0, 1, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(2020, 11, 31, 23, 59, 59);

        // year
        int lastYear = end.get(Calendar.YEAR);
        int firstYear = start.get(Calendar.YEAR);

        ArrayList<String> yearsList = new ArrayList<>();
        for (int i = firstYear; i <= lastYear; i++) {
            yearsList.add(+i + "年");
        }
        final String[] years = new String[yearsList.size()];
        yearsList.toArray(years);
        npYear.setDisplayedValues(years);
        npYear.setMaxValue(years.length - 1);
        npYear.setMinValue(0);
        npYear.setValue(yearsList.indexOf(currentYear));
        npYear.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                return years[value];
            }
        });

        // month
        ArrayList<String> monthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                monthList.add("0" + i + "月");
            } else {
                monthList.add(i + "月");
            }
        }
        final String[] months = new String[monthList.size()];
        monthList.toArray(months);
        LogUtils.e(months);
        npMonth.setDisplayedValues(months);
        npMonth.setMaxValue(months.length - 1);
        npMonth.setMinValue(0);
        npMonth.setValue(monthList.indexOf(currentYear));
        npMonth.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                return months[value];
            }
        });

        // day
        ArrayList<String> dayList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            String e;
            if (i < 10) {
                e = "0" + i + "日";
            } else {
                e = i + "日";
            }
//            e = e + "\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020";
            dayList.add(e);
        }
        final String[] days = new String[dayList.size()];
        dayList.toArray(days);
        LogUtils.e(days);
        npDay.setDisplayedValues(days);
        npDay.setMaxValue(days.length - 1);
        npDay.setMinValue(0);
        npDay.setValue(dayList.indexOf(currentYear));
        npDay.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                return days[value];
            }
        });

    }

    private void show() {
//        compile 'cn.aigestudio.wheelpicker:WheelPicker:1.1.2'
        datePickerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (datePickerLayout.getVisibility() == View.VISIBLE) {
            datePickerLayout.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}

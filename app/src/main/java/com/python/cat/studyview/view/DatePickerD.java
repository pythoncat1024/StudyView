package com.python.cat.studyview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.R;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

public class DatePickerD {

    private final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
    private final int thisMonth = Calendar.getInstance().get(Calendar.MONTH);
    private final int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private final LinearLayout datePickerLayout;
    private final NumberPickerD npYear;
    private final NumberPickerD npMonth;
    private final NumberPickerD npDay;
    private final long mStart;
    private final long mStop;

    private OnCancelListener cancelListener;
    private OnConformListener conformListener;

    private int selectedYear = thisYear;
    private int selectedMonth = thisMonth;
    private int selectedDay = thisDay;

    private LinkedHashMap<Integer, String> yearMap = new LinkedHashMap<>();
    private LinkedHashMap<Integer, String> monthMap = new LinkedHashMap<>();
    private LinkedHashMap<Integer, String> dayMap = new LinkedHashMap<>();
    private final int startY;
    private final int stopY;
    private final int startD;
    private final int stopD;
    private final int startM;
    private final int stopM;


    public LinearLayout getDatePickerLayout() {
        return datePickerLayout;
    }

    @SuppressLint("InflateParams")
    public DatePickerD(Context context, OnCancelListener ca, OnConformListener co,
                       long begin, long end) {

        this.cancelListener = ca;
        this.conformListener = co;
        this.mStart = begin;
        this.mStop = end;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) {
            throw new NullPointerException("inflater is null");
        }

        datePickerLayout = (LinearLayout) inflater
                .inflate(R.layout.datepicker_layout_default, null);


        datePickerLayout.findViewById(R.id.date_picker_cancel)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelListener != null) {
                            cancelListener.cancel(v);
                        }
                    }
                });

        datePickerLayout.findViewById(R.id.date_picker_finish)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (conformListener != null) {
                            conformListener.conform(v);
                        }
                    }
                });

        npYear = datePickerLayout.findViewById(R.id.np_year);
        npMonth = datePickerLayout.findViewById(R.id.np_month);
        npDay = datePickerLayout.findViewById(R.id.np_day);

        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(mStart);
        Calendar stop = Calendar.getInstance();
        stop.setTimeInMillis(mStop);

        startY = start.get(Calendar.YEAR);
        stopY = stop.get(Calendar.YEAR);

        startM = start.get(Calendar.MONTH);
        stopM = stop.get(Calendar.MONTH);

        startD = start.get(Calendar.DAY_OF_MONTH);
        stopD = stop.get(Calendar.DAY_OF_MONTH);
        init();
    }

    private void init() {

        npYear.setWrapSelectorWheel(false);
        npMonth.setWrapSelectorWheel(false);
        npDay.setWrapSelectorWheel(false);
        // npYear data
        initNpYear();
        initNpMonth();
        initNpDay();
    }

    private void initNpYear() {
        yearMap.clear();
        int start = startY;
        int stop = stopY;
        for (int i = start; i <= stop; i++) {
            yearMap.put(i, String.format(Locale.getDefault(), "%4d%s", i, "年"));
        }
        npYear.setMinValue(start);
        npYear.setMaxValue(stop);
        final String[] displayedValues = yearMap.values().toArray(new String[yearMap.size()]);
        npYear.setDisplayedValues(displayedValues);
        npYear.setValue(selectedYear);
//        npYear.setFormatter(new NumberPickerD.Formatter() {
//            @Override
//            public String format(int value) {
//                selectedYear = value;
//                initNpMonth();
//                return displayedValues[value - startY];
//            }
//        });

        npYear.setOnValueChangedListener(new NumberPickerD.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerD picker, int oldVal, int newVal) {
                selectedYear = newVal;
                initNpMonth();
            }
        });
    }

    private void initNpMonth() {
        monthMap.clear();
        final int start, stop;
        if (startY == stopY) {
            start = startM;
            stop = stopM;
        } else {
            // compare with thisYear
            if (startY == selectedYear) {
                // sy
                start = startM;
                stop = 11;
            } else if (stopY == selectedYear) {
                start = 0;
                stop = stopM;
            } else {
                start = 0; // 0-11
                stop = 11;
            }
        }
        // complete calculator start and stop of month
        for (int i = start; i <= stop; i++) {
            monthMap.put(i, String.format(Locale.getDefault(), "%2d%s", 1 + i, "月"));
        }
        npMonth.setMinValue(start);
        npMonth.setMaxValue(stop);
//        final String[] displayedValues = monthMap.values().toArray(new String[monthMap.size()]);
//        npMonth.setDisplayedValues(displayedValues);
        npMonth.setValue(selectedMonth);
        npMonth.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                return monthMap.get(value);
            }
        });
        npMonth.setOnValueChangedListener(new NumberPickerD.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerD picker, int oldVal, int newVal) {
                selectedMonth = newVal;
                initNpDay();
            }
        });
    }

    private void initNpDay() {
        dayMap.clear();
        final int start, stop;
        if (startY == selectedYear && startM == selectedMonth

                && stopY == startY && stopM == startM) {
            start = startD;
            stop = stopD;
        } else if (startY == selectedYear
                && startM == selectedMonth) {
            start = startD;
            stop = getDayMax(selectedYear, selectedMonth);
        } else if (stopY == selectedYear
                && stopM == selectedMonth) {
            start = 1;
            stop = stopD;
        } else {
            start = 1;
            stop = getDayMax(selectedYear, selectedMonth);
        }
        LogUtils.e("day:" + start + " , " + stop);
        for (int i = start; i <= stop; i++) {
            dayMap.put(i, String.format(Locale.getDefault(), "%2d%s", i, "日"));
        }
        npDay.setMinValue(start);
        npDay.setMaxValue(stop);
        LogUtils.v(dayMap);
//        final String[] displayedValues = dayMap.values().toArray(new String[dayMap.size()]);
//        LogUtils.d(displayedValues);
//        npDay.setDisplayedValues(displayedValues);
        npDay.setValue(selectedDay);
        npDay.setFormatter(new NumberPickerD.Formatter() {
            @Override
            public String format(int value) {
                LogUtils.v("day format value== " + value);
                return dayMap.get(value);
            }
        });
        npDay.setOnValueChangedListener(new NumberPickerD.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerD picker, int oldVal, int newVal) {
                selectedDay = newVal;
                LogUtils.d(selectedDay + " dynamic selectedDay");
            }
        });
        LogUtils.d(selectedDay + " xx selectedDay");
    }


    /**
     * @param year  0 - +++
     * @param month 0-11
     * @return [ 31] or [30] or [28] or [29]
     */
    private static int getDayMax(int year, int month) {
        month = month + 1;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 30;
        }
    }

    /**
     * @param year year
     * @return is feb has 29 days
     */
    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public interface OnCancelListener {
        void cancel(View v);
    }

    public interface OnConformListener {
        void conform(View v);
    }
}

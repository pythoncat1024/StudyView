package com.python.cat.studyview;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.utils.SizeUtils;
import com.python.cat.studyview.view.DatePickerD;
import com.python.cat.studyview.view.NumberPickerD;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdBottomPickerActivity extends BaseActivity {

    private TimePickerView pvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_bottom_picker);

        initTimePicker();


        View v = findViewById(R.id.btn_show_picker);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("click 2 show picker...");

//                        show();
//                        show2();
                pvTime.show(v);
            }
        });

        Button btnNext = findViewById(R.id.btn_next);
        btnNext
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LogUtils.e("next click..");
                        Intent di = new Intent(getActivity(), DatePickerActivity.class);
                        startActivity(di);
                    }
                });


        final NumberPickerD nd = findViewById(R.id.np_test);

        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        begin.set(2012, 10, 12);
        end.set(2020, 10, 12);
        int minValue = begin.get(Calendar.YEAR);
        nd.setMinValue(minValue);
        int maxValue = end.get(Calendar.YEAR);
        nd.setMaxValue(maxValue);
        nd.setSelectionDivider(new ColorDrawable(Color.GREEN));
        final String[] display = new String[maxValue - minValue + 1];
        for (int index = 0; index <= maxValue - minValue; index++) {
            display[index] = (index + minValue) + "年";
        }
        nd.setDisplayedValues(display);
        FrameLayout frame = findViewById(R.id.tt_f);
        final DatePickerD dpd = new DatePickerD(this, new DatePickerD.OnCancelListener() {
            @Override
            public void cancel(View v) {
                Toast.makeText(getApplicationContext(), "取消啊", Toast.LENGTH_SHORT).show();

            }
        }, new DatePickerD.OnConformListener() {
            @Override
            public void conform(View v, Calendar calendar) {
                LogUtils.e("selected: " + getSimpleTime(calendar));
                Toast.makeText(getApplicationContext(), "selected: " + getSimpleTime(calendar), Toast.LENGTH_SHORT).show();
            }
        },
                begin.getTimeInMillis(), end.getTimeInMillis());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
//        frame.addView(dpd.getDatePickerLayout(), params);

        // show popup window
        findViewById(R.id.btn_pop_show)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        LogUtils.e("click...");
                        // todo: popupWindow
                        final LinearLayout va = dpd.getDatePickerLayout();
                        PopupWindow window = new PopupWindow(getActivity());
                        window.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
                        window.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
                        window.setContentView(va);
                        // 设置PopupWindow的背景
                        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                        // 设置PopupWindow是否能响应外部点击事件
                        window.setOutsideTouchable(true);
//                        window.showAsDropDown(v);
                        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
                        window.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    }
                });
    }

    private void show() {

        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                LogUtils.e(date);
                Toast.makeText(getActivity(), date + ".", Toast.LENGTH_SHORT).show();
            }
        })
                .build();

        pvTime.show();
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(getActivity(), getTime(date), Toast.LENGTH_SHORT).show();
                LogUtils.d("onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        LogUtils.d("onTimeSelectChanged");
                    }
                })
//                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true)

                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }


    }


    private void show2() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                LogUtils.e(date);
                Toast.makeText(getActivity(), date + ".", Toast.LENGTH_SHORT).show();
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
////                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        return format.format(date);
    }

    private String getSimpleTime(Calendar date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        return String.format(Locale.getDefault(), "%4d.%2d.%2d", year, month, day);
    }

}

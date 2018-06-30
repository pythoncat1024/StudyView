package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.bean.view.Table;
import com.python.cat.studyview.utils.DynamicData;

import java.util.List;

public class TableContentView extends View {
    private int mWidth;
    private int mHeight;
    private List<Table> tableList;
    private Paint paint;
    private RectF oval;

    public TableContentView(Context context) {
        super(context);
    }

    public TableContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TableContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TableContentView(Context context,
                            @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;

        tableList = DynamicData.getViewSource();
        paint = new Paint();
        LogUtils.w(tableList);
        oval = new RectF(0, 0, mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(Math.round(1.0 * mWidth), Math.round(1.0 * mHeight));


    }
}

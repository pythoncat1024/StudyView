package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.bean.view.Table;
import com.python.cat.studyview.utils.DynamicData;

import java.util.List;

/**
 * table view
 */
public class TableView extends View {
    private int mWidth;
    private int mHeight;
    private List<Table> tableList;
    private RectF oval;
    private Paint paint;
    private int min;
    private RectF icon;
    private float diffLeft; // icon 左边距
    private float diffRight; // icon 左边距

    private float iconLength = 1230; // default

    public TableView(Context context) {
        super(context);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TableView(Context context, @Nullable AttributeSet attrs,
                     int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.mWidth = w;
        this.mHeight = h;

        min = Math.min(mWidth, mHeight);
        tableList = DynamicData.getViewSource();
        paint = new Paint();
        LogUtils.w(tableList);

        oval = new RectF(0, 0, min, min);
        icon = new RectF();

        diffLeft = min / 12;
        diffRight = diffLeft;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLeft(canvas);

        iconLength = 1f * min / 17;
        int height = (int) (iconLength / 2);
        for (Table t : tableList) {
            float left = min + diffLeft;
            float right = left + iconLength;
            float top = 0;
            float bottom = top + iconLength;
            paint.setColor(t.color);
            icon.set(left, height + top, right, height + bottom);
            canvas.drawRect(icon, paint);

            paint.setTextSize(iconLength*1.2f);
            paint.setColor(Color.BLACK);
            canvas.drawText(t.name + "\t" + (int) t.percent + "%",
                    right + diffRight, height + bottom, paint);
            height += iconLength * 3;
        }
    }

    private void drawLeft(Canvas canvas) {
        paint.setColor(Color.parseColor("#dbdbdb"));
        canvas.drawRect(oval, paint);
        int start = 180;
        for (Table t : tableList) {
            float angle = (float) t.percent / 100 * 360;
            paint.setColor(t.color);
            paint.setStrokeCap(Paint.Cap.ROUND);

            scaleOval(t.scale);
            canvas.drawArc(oval, start, angle,
                    true, paint);
            start += angle;
        }
        paint.setColor(Color.WHITE);
        canvas.drawCircle(1f * min / 2, 1f * min / 2, min * 1f / 12, paint);
        scaleOval(1); // 要加这一句，不然 onStop 后再进来就变小了
    }

    private void scaleOval(float scale) {
        oval.set(0, 0, min, min);
        float left = 1f * min * (1 - scale) / 2;
        float top = 1f * min * (1 - scale) / 2;
        float right = 1f * min * scale + left;
        float bottom = 1f * min * scale + top;
        oval.set(left, top, right, bottom);
    }

}

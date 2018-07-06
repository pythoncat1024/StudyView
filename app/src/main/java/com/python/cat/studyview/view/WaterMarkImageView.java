package com.python.cat.studyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.apkfuns.logutils.LogUtils;

public class WaterMarkImageView extends android.support.v7.widget.AppCompatImageView {
    private int mWidth;
    private int mHeight;

    private CharSequence watermark = "小野玛利亚";
    private Paint paint;
    private RectF oval;
    private float length;

    public WaterMarkImageView(Context context) {
        super(context);
    }

    public WaterMarkImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterMarkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        paint = new Paint();
        oval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String watermark = getWatermark().toString();
        paint.setTextSize(Math.min(mWidth, mHeight) / 10);
        length = paint.measureText(watermark);


        oval.set(5, mHeight - paint.getTextSize() - paint.getTextSize() / 3,
                length + 5, mHeight - paint.getTextSize() / 3);
        LogUtils.w(oval);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor("#cd4f39"));
        canvas.drawRect(oval, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(watermark, 0, mHeight - paint.getTextSize() / 2, paint);
    }

    public CharSequence getWatermark() {
        return watermark;
    }

    public void setWatermark(CharSequence watermark) {
        this.watermark = watermark;
    }
}

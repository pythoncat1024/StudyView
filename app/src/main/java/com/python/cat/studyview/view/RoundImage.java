package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.R;

public class RoundImage extends View {
    private int mWidth;
    private int mHeight;
    private int min;
    private Bitmap bitmap;
    private BitmapShader bitmapShader;
    private Paint mPaint;
    private RectF oval;
    private Drawable drawable;

    public RoundImage(Context context) {
        super(context);
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundImage(Context context, @Nullable AttributeSet attrs,
                      int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        min = Math.min(mWidth, mHeight);


        bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.xymly);

        LogUtils.w("bitmap: " + bitmap);

        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setShader(bitmapShader);

        ColorFilter lightingColorFilter = new LightingColorFilter(0xff0000, 0x000000);
        mPaint.setColorFilter(lightingColorFilter);
        oval = new RectF(0, 0, min, min);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(min / 2, min / 2, 0.8f * min / 2, mPaint);

    }
}

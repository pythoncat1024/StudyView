package com.python.cat.studyview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * TODO: document your custom view class.
 */
public class InnerButton extends android.support.v7.widget.AppCompatButton {

    public InnerButton(Context context) {
        super(context);
    }

    public InnerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
package com.python.cat.studyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * TODO: document your custom view class.
 */
public class InnerButton extends androidx.appcompat.widget.AppCompatButton {

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
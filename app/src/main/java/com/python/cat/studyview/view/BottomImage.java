package com.python.cat.studyview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.support.v7.widget.AppCompatImageView;

/**
 * from: <a href="https://blog.csdn.net/androidv/article/details/53028473">android View 跟随手指移动的7种方式</a>
 */
public class BottomImage extends AppCompatImageView {

    private Scroller mScroller;
    private int lastX;
    private int lastY;

    public BottomImage(Context context) {
        super(context);
    }

    public BottomImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScroller = new Scroller(getContext());
    }

    // 视图坐标方式
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                // 在当前left、top、right、bottom的基础上加上偏移量
                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
//                        offsetLeftAndRight(offsetX);
//                        offsetTopAndBottom(offsetY);
                break;
        }
        return true;
    }

}

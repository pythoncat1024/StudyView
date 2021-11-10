package com.python.cat.studyview.view;

import android.content.Context;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.apkfuns.logutils.LogUtils;

import java.util.Arrays;

/**
 * from: <a href="https://blog.csdn.net/androidv/article/details/53028473">android View 跟随手指移动的7种方式</a>
 */
public class BottomImage extends AppCompatImageView {

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
                // 在当前 left、top、right、bottom 的基础上加上偏移量
                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
//                        offsetLeftAndRight(offsetX);
//                        offsetTopAndBottom(offsetY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int[] xy = autoMove();
                int dx = xy[0];
                int dy = xy[1];
                LogUtils.w("up===> " + Arrays.toString(xy));
                // 在当前 left、top、right、bottom 的基础上 + 偏移量
                layout(getLeft() + dx,
                        getTop() + dy,
                        getRight() + dx,
                        getBottom() + dy);
                break;
        }
        return true;
    }


    private int[] autoMove() {
        RectF oval = getOval();
        if (oval == null) {
            LogUtils.e("不能检测到上层 view 的矩形区域，请检查代码逻辑！");
            return new int[]{0, 0};
        } else {

            int dx = 0, dy = 0;

            float left = getLeft() - oval.left;
            float right = oval.right - getRight();
            if (left > 0) {
                dx = -Math.round(left); // 这里为什么加- ,因为是要左移
            } else if (right > 0) {
                dx = Math.round(right); // 右移
            }

            float top = getTop() - oval.top;
            float bottom = oval.bottom - getBottom();
            if (top > 0) {
                dy = -Math.round(top); // 上移
            } else if (bottom > 0) {
                dy = Math.round(bottom); // 下移
            }

            return new int[]{dx, dy};
        }
    }

    /**
     * 耦合性太重，可复用性太低
     *
     * @return oval
     */
    private RectF getOval() {
        ViewParent parent = getParent();
        ViewGroup vg = (ViewGroup) parent;
        int count = vg.getChildCount();

        for (int x = 0; x < count; x++) {
            View child = vg.getChildAt(x);
            if (child instanceof RectView) {
                RectView rv = (RectView) child;
                return rv.getOval();
            }
        }
        return null;
    }

}

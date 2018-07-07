package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RectView extends BaseView {


    public static final int NONE_POINT = 0;
    public static final int LEFT_TOP = 1;
    public static final int RIGHT_TOP = 1 + 1;
    public static final int RIGHT_BOTTOM = 1 + 1 + 1;
    public static final int LEFT_BOTTOM = 1 + 1 + 1 + 1;
    private float currentX;
    private float currentY;
    private float downX;
    private float downY;
    private float lineLen;

    @IntDef({LEFT_BOTTOM, LEFT_TOP, RIGHT_BOTTOM, RIGHT_TOP})
    @Retention(RetentionPolicy.SOURCE)
    @interface TouchNear {
    }

    public static final int MOVE_ERROR = -1024;
    public static final int MOVE_H = 90;
    public static final int MOVE_V = 90 + 1;
    public static final int MOVE_VH = 90 + 1 + 1;

    @IntDef({MOVE_ERROR, MOVE_H, MOVE_V, MOVE_VH})
    @Retention(RetentionPolicy.SOURCE)
    @interface MoveDirection {
    }


    @TouchNear
    int currentNEAR = NONE_POINT;

    private Paint paint;
    private RectF oval;

    private float NEAR = 0;

    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RectView(Context context, @Nullable AttributeSet attrs,
                    int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        NEAR = Math.min(mWidth, mHeight) / 10;
        lineLen = NEAR * 0.6f;
        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        oval = new RectF();
        oval.set(0, 0, mWidth, mHeight); // first ui

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(oval, paint);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 8 lines
//        canvas.drawLines();
        // lt h
        canvas.drawLine(oval.left, oval.top, oval.left + lineLen, oval.top, paint);
        // lt v
        canvas.drawLine(oval.left, oval.top, oval.left, oval.top + lineLen, paint);
        // rt h
        canvas.drawLine(oval.right - lineLen, oval.top, oval.right, oval.top, paint);
        // rt v
        canvas.drawLine(oval.right, oval.top, oval.right, oval.top + lineLen, paint);
        // lb h
        canvas.drawLine(oval.left, oval.bottom, oval.left + lineLen, oval.bottom, paint);
        // lb v
        canvas.drawLine(oval.left, oval.bottom - lineLen, oval.left, oval.bottom, paint);
        // rb h
        canvas.drawLine(oval.right - lineLen, oval.bottom, oval.right, oval.bottom, paint);
        // rb v
        canvas.drawLine(oval.right, oval.bottom - lineLen, oval.right, oval.bottom, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.currentX = event.getX();
        this.currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();
                currentNEAR = checkNear();
                LogUtils.w("currentNEAR===> " + currentNEAR);
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentNEAR == NONE_POINT) {
                    // do move...
                    int canMove = canMove();
                    LogUtils.e("canMove? " + canMove);
                    float dx = currentX - downX;
                    float dy = currentY - downY;
                    LogUtils.w("dx=" + dx + " , dy=" + dy);
                    float newL = roundLength(oval.left + dx, mWidth);
                    float newR = roundLength(oval.right + dx, mWidth);
                    float newT = roundLength(oval.top + dy, mHeight);
                    float newB = roundLength(oval.bottom + dy, mHeight);

                    switch (canMove) {
                        case MOVE_H:
                            if (!distortionInMove(oval, newL, oval.top, newR, oval.bottom)) {
                                oval.set(newL, oval.top, newR, oval.bottom);
                            }
                            downX = currentX;
                            downY = currentY;
                            break;
                        case MOVE_V:
                            if (!distortionInMove(oval, oval.left, newT, oval.right, newB)) {
                                oval.set(oval.left, newT, oval.right, newB);
                            }
                            downX = currentX;
                            downY = currentY;
                            break;
                        case MOVE_VH:
//                            oval.inset(dx, dy);
                            if (!distortionInMove(oval, newL, newT, newR, newB)) {
                                oval.set(newL, newT, newR, newB);
                            }
                            downX = currentX;
                            downY = currentY;
                            break;
                        case MOVE_ERROR:
                            break;
                    }
                } else {
                    // do drag crop
                    currentX = roundLength(currentX, mWidth);
                    currentY = roundLength(currentY, mHeight);
                    switch (currentNEAR) {
                        case LEFT_TOP:
                            oval.set(currentX, currentY, oval.right, oval.bottom);
                            break;
                        case LEFT_BOTTOM:
                            oval.set(currentX, oval.top, oval.right, currentY);
                            break;
                        case RIGHT_TOP:
                            oval.set(oval.left, currentY, currentX, oval.bottom);
                            break;
                        case RIGHT_BOTTOM:
                            oval.set(oval.left, oval.top, currentX, currentY);
                            break;
                    }
                }
                postInvalidate(); // update ui
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * 移动的时候是否变形了
     */
    private boolean distortionInMove(RectF oval, float cL, float cT, float cR, float cB) {
        return Math.abs((cR - cL) - (oval.right - oval.left)) > 0.001
                || Math.abs((cB - cT) - (oval.bottom - oval.top)) > 0.001;
    }

    private float roundLength(float w, float max) {
        if (w < 0) {
            return 0;
        } else if (w > max) {
            return max;
        } else {
            return w;
        }
    }

    @TouchNear
    private int checkNear() {

        boolean nearLT = near(currentX, currentY, oval.left, oval.top);
        if (nearLT) {
            return LEFT_TOP;
        }
        boolean nearLB = near(currentX, currentY, oval.left, oval.bottom);
        if (nearLB) {
            return LEFT_BOTTOM;
        }
        boolean nearRT = near(currentX, currentY, oval.right, oval.top);
        if (nearRT) {
            return RIGHT_TOP;
        }
        boolean nearRB = near(currentX, currentY, oval.right, oval.bottom);
        if (nearRB) {
            return RIGHT_BOTTOM;
        }
        return NONE_POINT;
    }


    /**
     * when can move?
     * if the oval is not the max,then can move
     *
     * @return
     */
    @MoveDirection
    int canMove() {
        if (touchEdge()) {
            return MOVE_ERROR;
        }
        if (!oval.contains(currentX, currentY)) {
            return MOVE_ERROR;
        }
        if (oval.right - oval.left == mWidth
                && oval.bottom - oval.top == mHeight) {
            return MOVE_ERROR;
        } else if (oval.right - oval.left == mWidth
                && oval.bottom - oval.top != mHeight) {
            return MOVE_V;
        } else if (oval.right - oval.left != mWidth
                && oval.bottom - oval.top == mHeight) {
            return MOVE_H;
        } else {
            return MOVE_VH;
        }
    }

    /**
     * 超出边界
     *
     * @return true, false
     */
    boolean touchEdge() {
        return oval.left < 0 || oval.right > mWidth
                || oval.top < 0 || oval.bottom > mHeight;
    }

    boolean near(PointF one, PointF other) {
        float dx = Math.abs(one.x - other.x);
        float dy = Math.abs(one.y - other.y);
        return Math.pow(dx * dx + dy * dy, 0.5) <= NEAR;
    }

    boolean near(float x1, float y1, float x2, float y2) {
        float dx = Math.abs(x1 - x2);
        float dy = Math.abs(y1 - y2);
        return Math.pow(dx * dx + dy * dy, 0.5) <= NEAR;
    }
}

package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.R;
import com.python.cat.studyview.base.BaseView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class RectView extends BaseView {


    public static final int NONE_POINT = 0;
    public static final int LEFT_TOP = 1;
    public static final int RIGHT_TOP = 1 + 1;
    public static final int RIGHT_BOTTOM = 1 + 1 + 1;
    public static final int LEFT_BOTTOM = 1 + 1 + 1 + 1;
    private float currentX; // 当前手指的 x
    private float currentY; // 当前手指的 y
    private float downX; // 当前手指按下的 x
    private float downY; // 当前手指按下的 y (不一定，会改成 current 的值)

    private float bmpLeft, bmpTop, bmpRight, bmpBottom; // 图片的定位坐标
    private float lineLen; // 顶角区域的内部粗线的长度
    private float lineWidth; // 顶角区域的内部粗线的宽度
    private Path path; // 顶角的区域 path
    private Path hPath; // 顶角的区域 横向 path
    private Path vPath; // 顶角的区域 纵向 path
    private Path innerPath; // --> 配合 outer 画出外面的蒙层
    private Path outerPath; // --> 配合 inner 画出外面的蒙层

    private RectF hCrop; // 给 hPath 的
    private RectF vCrop; // 给 vPath 的
    private Matrix matrix;
    private Bitmap bitmap;
    private Point center; // view 的中心点
    private int bbw; // 图片的宽度（缩放后的）
    private int bbh; // 图片的高度（缩放后的）


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
    private RectF oval; // 选中区域的 oval
    private RectF outer; // View 的区域

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
        // init bitmap
        matrix = new Matrix();
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.xymly);
        int bw = temp.getWidth();
        int bh = temp.getHeight();
        float scale = Math.min(1f * mWidth / bw, 1f * mHeight / bh);
        LogUtils.w("scale==" + scale);
        bitmap = scaleBitmap(temp, scale);
        // compute init left, top
        bbw = bitmap.getWidth();
        bbh = bitmap.getHeight();
        center = new Point(mWidth / 2, mHeight / 2);
        Point bmpCenter = new Point(bbw / 2, bbh / 2); // 图片原来的中心点
//        matrix.postScale(0.9f, 0.9f, center.x, center.y); // 中心点参数是有用的
        matrix.postTranslate(center.x - bmpCenter.x, center.y - bmpCenter.y); // 移动到当前view 的中心
        bmpLeft = center.x - bbw / 2;
        bmpTop = center.y - bbh / 2;
        bmpRight = center.x + bbw / 2;
        bmpBottom = center.y + bbh / 2;
        // init other
        NEAR = Math.min(mWidth, mHeight) / 10;
        lineLen = NEAR * 0.6f;
        lineWidth = lineLen / 8;
        paint = new Paint();
        paint.setAntiAlias(true);
        outer = new RectF();
        oval = new RectF();
        outer.set(center.x - bbw / 2, center.y - bbh / 2,
                center.x + bbw / 2, center.y + bbh / 2); // the view area
        oval.set(outer); // first ui
        path = new Path();
        outerPath = new Path();
        innerPath = new Path();
        hPath = new Path();
        vPath = new Path();
        hCrop = new RectF();
        vCrop = new RectF();

        LogUtils.e("DIFF w==" + (center.x - bbw / 2) + " , y==" + (center.y - bbh / 2));
        LogUtils.e("DIFF 2 w==" + (center.x + bbw / 2) + " , y==" + (center.y + bbh / 2));
        LogUtils.e("DIFF 3 w==" + (mWidth) + " , y==" + (mHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        // draw bitmap
        canvas.drawBitmap(bitmap, matrix, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(center.x + bbw / 2, center.y + bbh / 2, 15, paint);
        // draw outer area
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.white_overlay));
        path.rewind();
        innerPath.rewind();
        outerPath.rewind();
        innerPath.addRect(oval, Path.Direction.CCW);
        outerPath.addRect(outer, Path.Direction.CCW);
        path.op(outerPath, innerPath, Path.Op.DIFFERENCE);
        canvas.drawPath(path, paint); // 这个在 touch 的时候很卡

        // draw oval
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(1);
        canvas.drawRect(oval, paint);


        // draw bold line path
        if (3 == 3) {
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL);
            // lt
            drawLeftTopPath(canvas);
            // rt
            drawRightTopPath(canvas);
            // rb
            drawRightBottomPath(canvas);
            // lb
            drawLeftBottomPath(canvas);
        }


        // 8 lines
//        canvas.drawLines();
        if (1 == 3) {

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
    }

    /**
     * 画出左上角的path 路径
     *
     * @param canvas 画布
     */
    private void drawLeftTopPath(Canvas canvas) {
        hCrop.set(oval.left, oval.top, oval.left + lineLen, oval.top + lineWidth);
        vCrop.set(oval.left, oval.top, oval.left + lineWidth, oval.top + lineLen);
        hPath.rewind();
        vPath.rewind();
        path.rewind();
        hPath.addRect(hCrop, Path.Direction.CCW);
        vPath.addRect(vCrop, Path.Direction.CCW);
        path.op(hPath, vPath, Path.Op.UNION);
        canvas.drawPath(path, paint);
    }

    /**
     * 画出右上角的path 路径
     *
     * @param canvas 画布
     */
    private void drawRightTopPath(Canvas canvas) {
        hCrop.set(oval.right - lineLen, oval.top, oval.right, oval.top + lineWidth);
        vCrop.set(oval.right - lineWidth, oval.top, oval.right, oval.top + lineLen);
        hPath.rewind();
        vPath.rewind();
        path.rewind();
        hPath.addRect(hCrop, Path.Direction.CCW);
        vPath.addRect(vCrop, Path.Direction.CCW);
        path.op(hPath, vPath, Path.Op.UNION);
        canvas.drawPath(path, paint);
    }


    /**
     * 画出右下角的path 路径
     *
     * @param canvas 画布
     */
    private void drawRightBottomPath(Canvas canvas) {
        hCrop.set(oval.right - lineLen, oval.bottom - lineWidth, oval.right, oval.bottom);
        vCrop.set(oval.right - lineWidth, oval.bottom - lineLen, oval.right, oval.bottom);
        hPath.rewind();
        vPath.rewind();
        path.rewind();
        hPath.addRect(hCrop, Path.Direction.CCW);
        vPath.addRect(vCrop, Path.Direction.CCW);
        path.op(hPath, vPath, Path.Op.UNION);
        canvas.drawPath(path, paint);
    }


    /**
     * 画出左下角的path 路径
     *
     * @param canvas 画布
     */
    private void drawLeftBottomPath(Canvas canvas) {
        hCrop.set(oval.left, oval.bottom - lineWidth, oval.left + lineLen, oval.bottom);
        vCrop.set(oval.left, oval.bottom - lineLen, oval.left + lineWidth, oval.bottom);
        hPath.rewind();
        vPath.rewind();
        path.rewind();
        hPath.addRect(hCrop, Path.Direction.CCW);
        vPath.addRect(vCrop, Path.Direction.CCW);
        path.op(hPath, vPath, Path.Op.UNION);
        canvas.drawPath(path, paint);
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
                if (currentNEAR == NONE_POINT) {
//                    get().setFocusable(false);
//                    get().setClickable(false);
//                    get().setEnabled(false);
                    return true; // --> 这种情况下，让下面的 view 去处理
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 2==3 故意让这里不被调用的
                if (currentNEAR == NONE_POINT) {
                    LogUtils.e("这句话不会打印的，除非去掉 down 里面的 return false");
                    // 这里不会进来了，因为上面已经return false 了，在这种情况下
                    // do move...
                    // 移动图片
                    float dx = currentX - downX;
                    float dy = currentY - downY;
                    bmpLeft += dx;
                    bmpRight += dx;
                    bmpTop += dy;
                    bmpBottom += dy;
                    matrix.postTranslate(dx, dy);
                    downX = currentX;
                    downY = currentY;
                } else {
                    // do drag crop
                    currentX = roundX(currentX);
                    currentY = roundY(currentY);
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
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                // 如果图片不是在矩形区域内，需要移动到矩形区域内部
                int[] xy = autoMove();
                int dx = xy[0];
                int dy = xy[1];
                matrix.postTranslate(dx, dy);
                bmpLeft += dx;
                bmpRight += dx;
                bmpTop += dy;
                bmpBottom += dy;
            }
            {

                // 完成这次位移之后，需要将矩形移动到 view 中间，图片也是做相应的位移
                float[] floatXY = move2center();
                LogUtils.w("移动中间位置：" + Arrays.toString(floatXY));
                float fdx = floatXY[0];
                float fdy = floatXY[1];
                // move oval
                oval.set(oval.left + fdx, oval.top + fdy,
                        oval.right + fdx, oval.bottom + fdy);
                // move bitmap
                matrix.postTranslate(fdx, fdy);
                bmpLeft += fdx;
                bmpRight += fdx;
                bmpTop += fdy;
                bmpBottom += fdy;
            }
//            {
//                // 然后是缩放，缩放图片与矩形
//                float sXY = scale2suitable();
//                matrix.postScale(sXY, sXY, center.x, center.y);
//                // todo: update bmp l,t,r,b
//
//
//                float w = (oval.right - oval.left) * sXY;
//                float h = (oval.bottom - oval.top) * sXY;
//                float left = center.x - w / 2;
//                float right = center.x + w / 2;
//                float top = center.y - h / 2;
//                float bottom = center.y + h / 2;
//                oval.set(left, top, right, bottom);
//            }
            break;
        }
        postInvalidate(); // update ui
        return true;
    }

    /**
     * * 将图片与矩形区域全部位移到 view 的中心(先不缩放，仅仅位移)
     *
     * @return 需要位移的距离： dx, dy
     */
    private float[] move2center() {
        float ovalCenterX = oval.left + (oval.right - oval.left) / 2;
        float ovalCenterY = oval.top + (oval.bottom - oval.top) / 2;

        LogUtils.e("oval center==(" + ovalCenterX + "," + ovalCenterY + ")"
                + " ####CENTER(" + center.x + "," + center.y + ")");
        return new float[]
                {
                        center.x - ovalCenterX, // x center
                        center.y - ovalCenterY // y center
                };

    }

    /**
     * 计算矩形缩放到最大时需要的缩放因子
     *
     * @return sx, sy
     */
    private float scale2suitable() {
        float h = oval.bottom - oval.top;
        float w = oval.right - oval.left;
        return Math.min(mHeight / h, mWidth / w);
    }

    /**
     * 计算 up 的时候，图片需要位移的 x,y 距离【如果图片完全在矩形底部，就不会产生位移】
     *
     * @return {x,y}
     */
    private int[] autoMove() {
        RectF oval = getOval();
        if (oval == null) {
            LogUtils.e("不能检测到上层 oval 的矩形区域，请检查代码逻辑！");
            return new int[]{0, 0};
        } else {

            int dx = 0, dy = 0;

            float originL = bmpLeft; // move 结束后的图片的坐标
            float originR = bmpRight;
            float left = originL - oval.left;
            float right = oval.right - originR;
            if (left > 0) {
                dx = -Math.round(left); // 这里为什么加- ,因为是要左移
            } else if (right > 0) {
                dx = Math.round(right); // 右移
            }

            float originT = bmpTop;
            float originB = bmpBottom;
            float top = originT - oval.top;
            float bottom = oval.bottom - originB;
            if (top > 0) {
                dy = -Math.round(top); // 上移
            } else if (bottom > 0) {
                dy = Math.round(bottom); // 下移
            }

            return new int[]{dx, dy};
        }
    }

    /**
     * 移动矩形区域，但是很卡，好奇怪
     */
    private void moveOval() {
        int canMove = canMove();
        LogUtils.e("canMove? " + canMove);
        float dx = currentX - downX;
        float dy = currentY - downY;
        LogUtils.w("dx=" + dx + " , dy=" + dy);
        float newL = roundX(oval.left + dx);
        float newR = roundX(oval.right + dx);
        float newT = roundY(oval.top + dy);
        float newB = roundY(oval.bottom + dy);

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
    }

    /**
     * 移动的时候是否变形了
     */
    private boolean distortionInMove(RectF oval, float cL, float cT, float cR, float cB) {
        return Math.abs((cR - cL) - (oval.right - oval.left)) > 0.001
                || Math.abs((cB - cT) - (oval.bottom - oval.top)) > 0.001;
    }


    /**
     * 不让 x 坐标超出 图片的范围
     *
     * @param x x
     * @return 返回 图片 x 所在的区间值
     */
    private float roundX(float x) {
        if (x < center.x - bbw / 2) {
            return center.x - bbw / 2;
        } else if (x > center.x + bbw / 2) {
            return center.x + bbw / 2;
        } else {
            return x;
        }
    }

    /**
     * 不让 y 坐标超出 图片的范围
     *
     * @param y y
     * @return 返回 图片 y 所在的区间值
     */
    private float roundY(float y) {
        if (y < center.y - bbh / 2) {
            return center.y - bbh / 2;
        } else if (y > center.y + bbh / 2) {
            return center.y + bbh / 2;
        } else {
            return y;
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
     * oval 是否可以移动
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
        if (oval.right - oval.left == bbw
                && oval.bottom - oval.top == bbh) {
            return MOVE_ERROR;
        } else if (oval.right - oval.left == bbw
                && oval.bottom - oval.top != bbh) {
            return MOVE_V;
        } else if (oval.right - oval.left != bbw
                && oval.bottom - oval.top == bbh) {
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
        return oval.left < center.x - bbw / 2 || oval.right > center.x + bbw / 2
                || oval.top < center.y - bbh / 2 || oval.bottom > center.y + bbh / 2;
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


    private View get() {
        return this;
    }

    public RectF getOval() {
        return oval;
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin 原图
     * @param scale  缩放比例
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float scale) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }
}

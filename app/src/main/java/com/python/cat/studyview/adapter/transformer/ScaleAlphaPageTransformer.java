package com.python.cat.studyview.adapter.transformer;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ScaleAlphaPageTransformer implements ViewPager.PageTransformer {
    public static final float MAX_SCALE = 1.0f;
    public static final float MIN_SCALE = 0.6f;
    public static final float MAX_ALPHA = 1.0f;
    public static final float MIN_ALPHA = 0.5f;
    public static final String TAG = ScaleAlphaPageTransformer.class.getSimpleName();

    private boolean alpha = true;
    private boolean scale = true;

    @Override
    public void transformPage(@NonNull View page, float position) {

        Log.d(TAG, "transformPage: " + position);
        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        if (scale) {
            float slope = (MAX_SCALE - MIN_SCALE) / 1;
            //一个公式
            float scaleValue = MIN_SCALE + tempScale * slope;
            page.setScaleX(scaleValue);
            page.setScaleY(scaleValue);
        }
        if (alpha) {
            //模糊
            float alope = (MAX_ALPHA - MIN_ALPHA) / 1;
            float alphaValue = MIN_ALPHA + tempScale * alope;
            page.setAlpha(alphaValue);
        }
    }

    /***
     * 设置是否模糊和改变大小
     * @param alpha
     * @param scale
     */
    public void setType(boolean alpha, boolean scale) {
        this.alpha = alpha;
        this.scale = scale;
    }
}
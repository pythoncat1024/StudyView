package com.python.cat.studyview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by cat on 2018/6/20.
 *
 */

public class CatScrollerView extends ScrollView {
    public CatScrollerView(Context context) {
        super(context);
    }

    public CatScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CatScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public CatScrollerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.adapter.ItemAdapter;
import com.python.cat.studyview.adapter.PageItemAdapter;
import com.python.cat.studyview.adapter.transformer.Zoom;
import com.python.cat.studyview.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class BannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bannner);
        findViewById(R.id.btn_next).setOnClickListener(v ->
                startActivity(new Intent(this, TableActivity.class)));
        final String[] display = new String[]{
                "python", "java", "php", "ruby", "node", "perl", "dart",
                "hello world", "hello code", "hello language"
        };
        List<String> stringList = Arrays.asList(display);
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        rv.setAdapter(new ItemAdapter(stringList));
        int pos = Integer.MAX_VALUE / 2;
        int rest = pos % stringList.size();
        pos -= rest;
        rv.scrollToPosition(pos);
        rv.post(() -> {
            int width = rv.getWidth();
            View viewUnder = rv.findChildViewUnder(width / 2, rv.getHeight() / 2);
            if (viewUnder != null) {
                int cw = viewUnder.getWidth();
                int x = (width - cw * 3) / 2;
                LogUtils.d("x===" + x);
                rv.scrollBy(-x, 0);
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                LogUtils.d("onScrolled: %s,%s", dx, dy);
                int fp = layoutManager.findFirstVisibleItemPosition();
                int lp = layoutManager.findLastVisibleItemPosition();
                int middle = (int) (((long) lp + (long) fp) / 2L);
                View view = layoutManager.findViewByPosition(middle);
                LinearLayout ll = (LinearLayout) view;
                TextView tv = ll.findViewById(R.id.mini_tv);
                LogUtils.d("%d, %d, %d #text = %s", fp, lp, middle, tv.getText());
                //                mCurrentItemOffset += dx;
                //                computeCurrentItemPos();
                //                onScrolledChangedCallback();
            }
        });
        extracted(stringList);
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    //    private void onScrolledChangedCallback() {
    //        int offset = mCurrentItemOffset - mCurrentItemPos * mOnePageWidth;
    //        float percent = (float) Math.max(Math.abs(offset) * 1.0 / mOnePageWidth, 0.0001);
    //
    //        View leftView = null;
    //        View currentView;
    //        View rightView = null;
    //        if (mCurrentItemPos > 0) {
    //            leftView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos - 1);
    //        }
    //        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos);
    //        if (mCurrentItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
    //            rightView = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentItemPos + 1);
    //        }
    //
    //        if (leftView != null) {
    //            // y = (1 - mScale)x + mScale
    //            leftView.setScaleY((1 - mScale) * percent + mScale);
    //        }
    //        if (currentView != null) {
    //            // y = (mScale - 1)x + 1
    //            currentView.setScaleY((mScale - 1) * percent + 1);
    //        }
    //        if (rightView != null) {
    //            // y = (1 - mScale)x + mScale
    //            rightView.setScaleY((1 - mScale) * percent + mScale);
    //        }
    //    }//
    private void extracted(List<String> stringList) {
        ViewPager vp = findViewById(R.id.pager);
        vp.setCurrentItem(stringList.size() / 2, false);
        vp.setAdapter(new PageItemAdapter(getSupportFragmentManager(), stringList));
        vp.setOffscreenPageLimit(3);
        //        vp.setPageTransformer(true, new ScaleAlphaPageTransformer());
        //        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.setPageTransformer(true, new Zoom());
        vp.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                20, getResources().getDisplayMetrics()));
    }
}
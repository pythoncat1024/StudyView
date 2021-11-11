package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
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
                "音乐推荐", "历史解密", "相声小品", "每日必听", "新闻资讯", "悬疑罪案", "英语美文",
                "搞笑段子", "情感赫兹", "涨知识", "热门翻唱", "专注时间", "真实故事", "影视解读", "最燃电音"
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
                TextView tv = getItemTextView((ViewGroup) viewUnder);
                LogUtils.d("----#text = %s", tv.getText());
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
                TextView tv = getItemTextView((ViewGroup) view);
                LogUtils.d("%d, %d, %d #text = %s", fp, lp, middle, tv.getText());
                View fv1 = layoutManager.findViewByPosition(middle - 1);
                View fv2 = layoutManager.findViewByPosition(middle - 2);
                View lv1 = layoutManager.findViewByPosition(middle + 1);
                View lv2 = layoutManager.findViewByPosition(middle + 2);
                view.setAlpha(1f);
                getItemTextView((ViewGroup) view).setTextSize(18);

                lv1.setAlpha(0.6f);
                getItemTextView((ViewGroup) lv1).setTextSize(16);
                fv1.setAlpha(0.6f);
                getItemTextView((ViewGroup) fv1).setTextSize(16);

                if (fv2 != null) {
                    fv2.setAlpha(0.2f);
                    getItemTextView((ViewGroup) fv2).setTextSize(16);
                }
                if (lv2 != null) {
                    lv2.setAlpha(0.2f);
                    getItemTextView((ViewGroup) lv2).setTextSize(16);
                }
                getItemTextView((ViewGroup) view).setTextColor(getResources().getColor(R.color.black, getTheme()));
                //                mCurrentItemOffset += dx;
                //                computeCurrentItemPos();
                //                onScrolledChangedCallback();
            }
        });
        extracted(stringList);
    }

    private TextView getItemTextView(ViewGroup viewUnder) {
        ViewGroup ll = viewUnder;
        return ll.findViewById(R.id.mini_tv);
    }

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
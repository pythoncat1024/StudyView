package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

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
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new LinearSnapHelper().attachToRecyclerView(rv);
        rv.setAdapter(new ItemAdapter(stringList));
        int pos = Integer.MAX_VALUE;
        int rest = pos % stringList.size();
        pos -= rest;
        rv.scrollToPosition(pos);
        extracted(stringList);
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
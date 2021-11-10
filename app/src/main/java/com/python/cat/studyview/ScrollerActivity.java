package com.python.cat.studyview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.python.cat.studyview.adapter.ContentAdapter;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.utils.DynamicData;

public class ScrollerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        ListView lv1 = findViewById(R.id.scroll_lv_first);
        ListView lv2 = findViewById(R.id.scroll_lv_second);
        ContentAdapter adapter = new ContentAdapter(getApplicationContext(),
                DynamicData.getDynamicData());
        lv1.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(lv1);
        lv2.setAdapter(adapter);
//        setListViewHeightBasedOnChildren(lv2);
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i,null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.invalidate();
    }

}

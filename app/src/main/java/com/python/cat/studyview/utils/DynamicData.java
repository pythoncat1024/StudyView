package com.python.cat.studyview.utils;

import android.graphics.Color;

import com.python.cat.studyview.bean.view.Table;
import com.python.cat.studyview.view.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cat on 2018/6/20.
 */

public class DynamicData {

    private DynamicData() {
    }

    public static List<String> getDynamicData() {

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < 55; i++) {
            arrayList.add("Item " + i);
        }

        return arrayList;
    }

    public static List<Table> getViewSource() {
        ArrayList<Table> tables = new ArrayList<>();
        Table t1 = new Table("万卷山", 25, Color.parseColor("#ee00ee"), 1f);
        Table t2 = new Table("世纪城梦想派", 20, Color.parseColor("#ff83fa"), 0.8f);
        Table t3 = new Table("首发龙湖紫寰香颂", 15, Color.parseColor("#ff1493"), 1f);
        Table t4 = new Table("银海中心", 15, Color.parseColor("#ffa500"), 0.5f);
        Table t5 = new Table("华润时光", 15, Color.parseColor("#ffd700"), 0.6f);
        Table t6 = new Table("其他", 10, Color.BLUE, 0.5f);
        Collections.addAll(tables, t1, t5, t4, t3, t2, t6);
        return tables;
    }
}

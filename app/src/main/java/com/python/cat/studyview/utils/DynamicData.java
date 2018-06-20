package com.python.cat.studyview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cat on 2018/6/20.
 *
 */

public class DynamicData {

    public static List<String> getDynamicData() {

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < 55; i++) {
            arrayList.add("Item " + i);
        }

        return arrayList;
    }
}

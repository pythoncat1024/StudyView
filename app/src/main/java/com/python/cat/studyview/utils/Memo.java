package com.python.cat.studyview.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cat on 2018/6/15.
 * <p>
 * memo activity
 */

public class Memo {

    private Memo() {
    }

    private static List<Activity> memo = new ArrayList<>();

    public static List<Activity> getMemo() {
        return Collections.unmodifiableList(memo);
    }

    public static boolean add(Activity ac) {
        return memo.add(ac);
    }

    public static boolean remove(Activity ac) {
        return memo.remove(ac);
    }

    public static void clear() {
        memo.clear();
    }

}

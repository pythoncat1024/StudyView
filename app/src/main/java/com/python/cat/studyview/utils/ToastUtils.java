package com.python.cat.studyview.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

/**
 * Created by cat on 2018/6/15.
 * toast utils
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, Object sequence) {
        cancel();
        if (toast == null) {
            toast = Toast.makeText(context,
                    sequence == null ? "null" : sequence.toString(),
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void show(Context context, @StringRes int id) {
        cancel();
        if (toast == null) {
            toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}

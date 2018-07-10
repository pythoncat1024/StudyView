package com.python.cat.studyview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.utils.StatusBarUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtils.translucentStatusBar(this, true);
        } else {
            StatusBarUtils.translucentStatusBar(this);
        }

        ImageView img = findViewById(R.id.splash_img);

        img.animate()
                .alpha(0.1f)
//                .scaleX(0.5f)
//                .scaleY(0.5f)
//                .rotation(90)
//                .translationZ(0.5f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        LogUtils.d("animate end");
                        if (getActivity()!=null){
                            startActivity(new Intent(getActivity(), TableActivity.class));
                        }
                        finish();
                    }

                })
                .start();
    }


}

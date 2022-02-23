package com.example.myapplication.popwindow;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

import com.example.myapplication.R;

public class PopWindowTest extends Activity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow_test);
        findViewById(R.id.showPop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                test1(v);
                test2(v);
            }
        });
    }

    private void test2(View v) {
        if (popupWindow != null) popupWindow.dismiss();
        int path = getResources().getDimensionPixelOffset(R.dimen.anim_padding);
        popupWindow = new PopupWindow();
        View view = View.inflate(PopWindowTest.this, R.layout.popwindow_test_2, null);
        View animView = view.findViewById(R.id.content_view);
        ViewGroup.LayoutParams layoutParams = animView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = path;
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = path;
        }
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAsDropDown(v);
        v.postDelayed(() -> {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(animView, "translationY", 0, path, 0, -path, 0);
            objectAnimator.setDuration(1000);
//            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//            objectAnimator.setInterpolator(new CycleInterpolator(2));
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.setRepeatCount(2);
            objectAnimator.start();
        }, 500);
    }

    private void test1(View v) {
        int path = 100;
        PopupWindow popupWindow = new PopupWindow();
        View view = View.inflate(PopWindowTest.this, R.layout.popwindow_test_1, null);
//        View view = LayoutInflater.from(PopWindowTest.this).inflate(R.layout.popwindow_test_1, null);
        View animView = view.findViewById(R.id.content_view);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(animView.getMeasuredHeight() + path * 2);

        popupWindow.showAsDropDown(v);
        v.postDelayed(() -> {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(animView, "translationY", 0, -path, 0, path, 0, -path);
            objectAnimator.setDuration(2500);
//                    objectAnimator.setRepeatCount(2);
            objectAnimator.start();
        }, 500);
    }
}

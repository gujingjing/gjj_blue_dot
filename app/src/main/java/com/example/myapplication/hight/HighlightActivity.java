package com.example.myapplication.hight;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class HighlightActivity extends Activity {

    private View test2;
    private View test3;
    private View test4;
    private UserHintView hintView;
    //    private EdgeOverflowHiLightView edgeOverflowHiLightView;
    private EdgeOverflowHiLightView1 edgeOverflowHiLightView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight_test);
        test2 = findViewById(R.id.test2);
        test3 = findViewById(R.id.test3);
        test4 = findViewById(R.id.test4);
        hintView = findViewById(R.id.hintView);
//        edgeOverflowHiLightView = findViewById(R.id.edgeOverflowHiLightView);
        hintView.post(() -> {
//                setHint();
            Activity activity = HighlightActivity.this;
            edgeOverflowHiLightView = new EdgeOverflowHiLightView1(activity);
            int[] loc = new int[2];
            View view = getWindow().getDecorView();
            view.getLocationInWindow(loc);
            Rect targetRect = new Rect();
            int padding = getResources().getDimensionPixelSize(R.dimen.hight_padding);
            Rect rr1 = getViewAbsRect(test3, loc[0], loc[1]);
            Rect rr2 = getViewAbsRect(test4, loc[0], loc[1]);
            targetRect.left = rr1.left - padding;
            targetRect.top = rr1.top - padding;
            targetRect.right = rr2.right + padding;
            targetRect.bottom = rr2.bottom + padding;

            edgeOverflowHiLightView.setTargetRect(targetRect);
            edgeOverflowHiLightView.show(activity);
            startAnim();

//                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//                ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
//                LinearLayout testView = new LinearLayout(activity);
//                testView.setBackgroundResource(R.color.colorProgress);
//                rootView.addView(testView, lp);
        });
    }

    public void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(edgeOverflowHiLightView, "alpha", 0, 1.0f);
        animator.setDuration(500);
        animator.start();
    }


    private Rect getViewAbsRect(View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        Rect rect = new Rect();
        rect.set(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
        return rect;
    }

    private Rect getViewAbsRect(View view, int parentX, int parentY) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        Rect rect = new Rect();
        rect.set(loc[0], loc[1], loc[0] + view.getMeasuredWidth(), loc[1] + view.getMeasuredHeight());
        rect.offset(-parentX, -parentY);
        return rect;
    }

    private void setHint() {
        hintView.setHeightLightView(test3) //设置高光显示的view
                .setHeightLightType(UserHintView.DrawHeightLightType.CUSTOM_ROUND_RECT) //设置高光类型为 自定义圆角
                .setRectRoundCustom(0, 0, 0, 0) //高光类型为自定义圆角时，四个角的round 单位dp
                .setHintBackgroundColor(Color.parseColor("#88000000")) //设置背景颜色
                //.setHeightLightType(UserHintView.DrawHeightLightType.ROUND_RECT) //设置高光类型为 圆角矩形
                //.setRectRound(5) //设置圆角矩形的角度 单位dp
                //.setHeightLightType(UserHintView.DrawHeightLightType.CIRCLE) //设置高光类型为 圆形
                //.setHeightLightType(UserHintView.DrawHeightLightType.RECT) //设置高光类型为 矩形
                .setHeightLightExpendSize(2) // 设置高光扩张大小 自定义圆角矩形无效 单位dp
                .setArrowBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow)) //设置箭头提示图片Bitmap
                .setArrowSize(10, 10) // 设置图片的大小 单位dp
                .setDrawHintDirection(UserHintView.DrawHintDirection.LEFT) // 设置提示图片的位置
                .setArrowOffsetXY(1, 1) //设置图片 X Y轴的偏移量 单位dp
                .setHintText("测试测试测\n测试测试测") //设置提示文字
                .setHintTextColor(Color.WHITE) //设置提示文字的颜色
                .setHintTextSize(17) //设置提示文字的大小 单位dp
                .setHintTextOffsetXY(10, 10) //设置提示文字的 X Y轴的偏移量 单位dp
                .create();
    }
}

package com.example.myapplication.bluedot_v3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myapplication.bluedot_v3.BadgeViewHelperV1;
import com.example.myapplication.bluedot_v3.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_v3.IBadgeV1;

public class BadgeTextViewV1 extends TextView implements IBadgeV1 {

    private EdgeBadgeViewHelper mBadgeViewHelper;

    public BadgeTextViewV1(Context context) {
        this(context, null);
    }

    public BadgeTextViewV1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeTextViewV1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mBadgeViewHelper = new BadgeViewHelperV2(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mBadgeViewHelper.onDraw(canvas);
    }

    @Override
    public void showCirclePointBadge() {
//        mBadgeViewHelper.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
//        mBadgeViewHelper.showTextBadge(badgeText);
    }

    @Override
    public void hiddenBadge() {
//        mBadgeViewHelper.hiddenBadge();
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
//        mBadgeViewHelper.showDrawable(bitmap);
    }

    @Override
    public boolean isShowBadge() {
//        return mBadgeViewHelper.isShowBadge();
        return true;
    }

    @Override
    public BadgeViewHelperV1 getBadgeViewHelper() {
        return new BadgeViewHelperV1(this,getContext(),null);
    }
}

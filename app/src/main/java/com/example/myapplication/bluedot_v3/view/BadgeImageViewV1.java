package com.example.myapplication.bluedot_v3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import com.example.myapplication.bluedot_v3.BadgeViewHelperV1;
import com.example.myapplication.bluedot_v3.IBadgeV1;

public class BadgeImageViewV1 extends ImageView implements IBadgeV1 {

    private BadgeViewHelperV1 mBadgeViewHelper;

    public BadgeImageViewV1(Context context) {
        this(context, null);
    }

    public BadgeImageViewV1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeImageViewV1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBadgeViewHelper = new BadgeViewHelperV1(this, context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHelper.drawBadge(canvas);
    }

    @Override
    public void showCirclePointBadge() {
        mBadgeViewHelper.showCirclePointBadge();
    }

    @Override
    public void showTextBadge(String badgeText) {
        mBadgeViewHelper.showTextBadge(badgeText);
    }

    @Override
    public void hiddenBadge() {
        mBadgeViewHelper.hiddenBadge();
    }

    @Override
    public void showDrawableBadge(Bitmap bitmap) {
        mBadgeViewHelper.showDrawable(bitmap);
    }

    @Override
    public boolean isShowBadge() {
        return mBadgeViewHelper.isShowBadge();
    }

    @Override
    public BadgeViewHelperV1 getBadgeViewHelper() {
        return mBadgeViewHelper;
    }

}

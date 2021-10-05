package com.example.myapplication.bluedot_5.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myapplication.bluedot_5.EdgeBadgeViewHelperInner;
import com.example.myapplication.bluedot_5.IEdgeBadgeViewOuter;

public class EdgeBadgeTextViewOuter extends TextView implements IEdgeBadgeViewOuter {

    private EdgeBadgeViewHelperInner badgeViewManager;

    public EdgeBadgeTextViewOuter(Context context) {
        this(context, null);
    }

    public EdgeBadgeTextViewOuter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeTextViewOuter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        badgeViewManager=new EdgeBadgeViewHelperInner(context,attrs,defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        badgeViewManager.onDraw(canvas);
    }

    @Override
    public int getBadgeWidth() {
        return badgeViewManager.getBadgeWidth();
    }

    @Override
    public int getBadgeHeight() {
        return badgeViewManager.getBadgeHeight();
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeNumber(int badgeNum) {
        return badgeViewManager.setBadgeNumber(badgeNum);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeText(String badgeText) {
        return badgeViewManager.setBadgeText(badgeText);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeTextSize(float size, boolean isSpValue) {
        return badgeViewManager.setBadgeTextSize(size, isSpValue);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeTextColor(int color) {
        return badgeViewManager.setBadgeTextColor(color);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeBackgroundColor(int color) {
        return badgeViewManager.setBadgeBackgroundColor(color);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeBackground(Drawable drawable) {
        return badgeViewManager.setBadgeBackground(drawable);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgePadding(float padding, boolean isDpValue) {
        return badgeViewManager.setBadgePadding(padding, isDpValue);
    }

    @Override
    public IEdgeBadgeViewOuter seMargin(float horizontalMargin, float verticalMargin, boolean isDpValue) {
        return badgeViewManager.seMargin(horizontalMargin, verticalMargin, isDpValue);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeGravity(int gravity) {
        return badgeViewManager.setBadgeGravity(gravity);
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeViewVisible(boolean visible) {
        return badgeViewManager.setBadgeViewVisible(visible);
    }
}

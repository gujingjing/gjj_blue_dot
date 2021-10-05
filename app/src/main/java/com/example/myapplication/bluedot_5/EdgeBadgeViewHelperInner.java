package com.example.myapplication.bluedot_5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.myapplication.bluedot_4.IEdgeBadgeDrawer;

public class EdgeBadgeViewHelperInner implements IEdgeBadgeViewOuter {

    protected IEdgeBadgeDrawer drawer;

    public EdgeBadgeViewHelperInner(IEdgeBadgeDrawer drawer) {
        this.drawer = drawer;
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    public void onDraw(Canvas canvas) {
        
    }

    @Override
    public int getBadgeWidth() {
        return 0;
    }

    @Override
    public int getBadgeHeight() {
        return 0;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeNumber(int badgeNum) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeText(String badgeText) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeTextSize(float size, boolean isSpValue) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeTextColor(int color) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeBackgroundColor(int color) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeBackground(Drawable drawable) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgePadding(float padding, boolean isDpValue) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter seMargin(float horizontalMargin, float verticalMargin, boolean isDpValue) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeGravity(int gravity) {
        return null;
    }

    @Override
    public IEdgeBadgeViewOuter setBadgeViewVisible(boolean visible) {
        return null;
    }
}

package com.example.myapplication.bluedot_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public interface IEdgeBadgeDrawer {

    void onDraw(Canvas canvas);

    void updateBadgeView();

    void initCustomAttrs(Context context, AttributeSet attrs, int defStyleAttr);

    int getBadgeWidth();

    int getBadgeHeight();

    IEdgeBadgeDrawer setBadgeViewType(@EdgeBadgeViewType int badgeViewType);

    IEdgeBadgeDrawer setBadgeText(String badgeText);

    IEdgeBadgeDrawer setBadgeTextColor(int color);

    IEdgeBadgeDrawer setBadgeTextSize(float size);

    IEdgeBadgeDrawer setBadgeBackgroundColor(int color);

    IEdgeBadgeDrawer setBadgeBackground(Drawable drawable);

    IEdgeBadgeDrawer setBadgePadding(int padding);

    IEdgeBadgeDrawer setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeDrawer setMargin(int horizontalMargin, int verticalMargin);
}

package com.example.myapplication.bluedot_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public interface IEdgeBadgeDrawer {

    void onDraw(Canvas canvas);

    void updateBadgeView();

    void initCustomAttr(Context context, int attr, TypedArray typedArray);

    int getBadgeWidth();

    int getBadgeHeight();

    IEdgeBadgeDrawer setBadgeViewType(@EdgeBadgeViewType int badgeViewType);

    IEdgeBadgeDrawer setBadgeText(String badgeText);

    IEdgeBadgeDrawer setBadgeTextColor(int color);

    IEdgeBadgeDrawer setBadgeTextSize(float size);

    IEdgeBadgeDrawer setBadgeBackgroundColor(int color);

    IEdgeBadgeDrawer setBadgeBackground(Drawable drawable);

    IEdgeBadgeDrawer setBadgePadding(float padding);

    IEdgeBadgeDrawer setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeDrawer setMargin(float horizontalMargin, float verticalMargin);
}

package com.example.myapplication.bluedot_4.drawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.myapplication.bluedot_4.EdgeBadgeGravity;

public interface IEdgeBadgeDrawer {

    void initCustomAttr(Context context, int attr, TypedArray typedArray);

    void onDraw(Canvas canvas);

    int getBadgeWidth();

    int getBadgeHeight();

    IEdgeBadgeDrawer updateBadgeView();

    IEdgeBadgeDrawer setBadgeNumber(int badgeNum);

    IEdgeBadgeDrawer setBadgeText(String badgeText);

    IEdgeBadgeDrawer setBadgeTextColor(int color);

    IEdgeBadgeDrawer setBadgeTextSize(float size, boolean isSpValue);

    IEdgeBadgeDrawer setBadgeBackgroundColor(int color);

    IEdgeBadgeDrawer setBadgeBackground(Drawable drawable);

    IEdgeBadgeDrawer setBadgePadding(float padding, boolean isDpValue);

    IEdgeBadgeDrawer setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeDrawer setGravityOffset(float offsetX, float offsetY, boolean isDpValue);
}

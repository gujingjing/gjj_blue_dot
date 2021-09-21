package com.example.myapplication.bluedot_v3;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 *
 */
public interface IEdgeBadgeController {
    void onDraw(Canvas canvas);

    void badgeInvalidate();

    int getBadgeWidth();

    int getBadgeHeight();

    IEdgeBadgeController setBadgeNumber(int badgeNum);

    IEdgeBadgeController setBadgeText(String badgeText);

    IEdgeBadgeController setBadgeTextColor(int color);

    IEdgeBadgeController setBadgeTextSize(float size, boolean isSpValue);

    IEdgeBadgeController setBadgeBackgroundColor(int color);

    IEdgeBadgeController setBadgeBackground(Drawable drawable);

    IEdgeBadgeController setBadgePadding(float padding, boolean isDpValue);

    IEdgeBadgeController setBadgeVisibility(boolean badgeShow);

    IEdgeBadgeController setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeController setGravityOffset(float offsetX, float offsetY, boolean isDpValue);

    String getBadgeGroupId();
}
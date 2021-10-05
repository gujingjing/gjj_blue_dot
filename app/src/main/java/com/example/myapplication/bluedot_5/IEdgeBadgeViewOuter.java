package com.example.myapplication.bluedot_5;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

import com.example.myapplication.bluedot_4.EdgeBadgeGravity;

public interface IEdgeBadgeViewOuter {
    int getBadgeWidth();

    int getBadgeHeight();

    IEdgeBadgeViewOuter setBadgeNumber(int badgeNum);

    IEdgeBadgeViewOuter setBadgeText(String badgeText);

    IEdgeBadgeViewOuter setBadgeTextSize(float size, boolean isSpValue);

    IEdgeBadgeViewOuter setBadgeTextColor(@ColorInt int color);

    IEdgeBadgeViewOuter setBadgeBackgroundColor(@ColorInt int color);

    IEdgeBadgeViewOuter setBadgeBackground(Drawable drawable);

    IEdgeBadgeViewOuter setBadgePadding(float padding, boolean isDpValue);

    IEdgeBadgeViewOuter seMargin(float horizontalMargin, float verticalMargin, boolean isDpValue);

    IEdgeBadgeViewOuter setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeViewOuter setBadgeViewVisible(boolean visible);

}

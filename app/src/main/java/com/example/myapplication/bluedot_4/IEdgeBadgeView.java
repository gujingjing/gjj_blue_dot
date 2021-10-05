package com.example.myapplication.bluedot_4;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public interface IEdgeBadgeView {
    IEdgeBadgeView setBadgeNumber(int badgeNum);

    IEdgeBadgeView setBadgeText(String badgeText);

    IEdgeBadgeView setBadgeTextSize(float size, boolean isSpValue);

    IEdgeBadgeView setBadgeTextColor(@ColorInt int color);

    IEdgeBadgeView setBadgeBackgroundColor(@ColorInt int color);

    IEdgeBadgeView setBadgeBackground(Drawable drawable);

    IEdgeBadgeView setBadgePadding(float padding, boolean isDpValue);

    IEdgeBadgeView setMargin(float horizontalMargin, float verticalMargin, boolean isDpValue);

    IEdgeBadgeView setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeView setBadgeViewVisible(boolean visible);
}

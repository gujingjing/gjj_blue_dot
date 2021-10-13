package com.example.myapplication.bluedot_4;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public interface IEdgeBadgeView {

    IEdgeBadgeView setBadgeViewType(@EdgeBadgeViewType int badgeViewType);

    IEdgeBadgeView setBadgeText(String badgeText);

    IEdgeBadgeView setBadgeTextSize(float size, boolean isSpValue);

    IEdgeBadgeView setBadgeTextColor(@ColorInt int color);

    IEdgeBadgeView setBadgeBackgroundColor(@ColorInt int color);

    IEdgeBadgeView setBadgeBackground(Drawable drawable);

    IEdgeBadgeView setBadgePadding(int padding, boolean isDpValue);

    IEdgeBadgeView setMargin(int horizontalMargin, int verticalMargin, boolean isDpValue);

    IEdgeBadgeView setBadgeGravity(@EdgeBadgeGravity int gravity);

    IEdgeBadgeView setBadgeViewVisible(boolean visible);

//    IEdgeBadgeView setBadgeViewBorderRadio(int radios, boolean isDpValue);
//
//    IEdgeBadgeView setBadgeViewBorderWidth(int width, boolean isDpValue);
//
//    IEdgeBadgeView setBadgeViewBorderColor(@ColorInt int color, boolean isDpValue);
}

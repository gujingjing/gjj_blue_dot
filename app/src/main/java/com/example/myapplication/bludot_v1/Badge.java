package com.example.myapplication.bludot_v1;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */

public interface Badge {

    Badge setBadgeNumber(int badgeNum);

    int getBadgeNumber();

    Badge setBadgeText(String badgeText);

    String getBadgeText();

    Badge setExactMode(boolean isExact);

    boolean isExactMode();

    Badge setBadgeBackgroundColor(int color);

    Badge stroke(int color, float width, boolean isDpValue);

    int getBadgeBackgroundColor();

    Badge setBadgeBackground(Drawable drawable);

    Badge setBadgeBackground(Drawable drawable, boolean clip);

    Drawable getBadgeBackground();

    Badge setBadgeTextColor(int color);

    int getBadgeTextColor();

    Badge setBadgeTextSize(float size, boolean isSpValue);

    float getBadgeTextSize(boolean isSpValue);

    Badge setBadgePadding(float padding, boolean isDpValue);

    float getBadgePadding(boolean isDpValue);

    Badge setBadgeGravity(int gravity);

    int getBadgeGravity();

    Badge setGravityOffset(float offset, boolean isDpValue);

    Badge setGravityOffset(float offsetX, float offsetY, boolean isDpValue);

    float getGravityOffsetX(boolean isDpValue);

    float getGravityOffsetY(boolean isDpValue);

    Badge bindTarget(View view);

    View getTargetView();

    void hide(boolean animate);
}

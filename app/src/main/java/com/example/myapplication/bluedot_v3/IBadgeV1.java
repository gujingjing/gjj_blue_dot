package com.example.myapplication.bluedot_v3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;

/**
 *
 */
public interface IBadgeV1 {
    /**
     * 显示圆点徽章
     */
    void showCirclePointBadge();

    /**
     * 显示文字徽章
     *
     * @param badgeText
     */
    void showTextBadge(String badgeText);

    /**
     * 隐藏徽章
     */
    void hiddenBadge();

    /**
     * 显示图像徽章
     *
     * @param bitmap
     */
    void showDrawableBadge(Bitmap bitmap);

    /**
     * 是否显示徽章
     *
     * @return
     */
    boolean isShowBadge();

    BadgeViewHelperV1 getBadgeViewHelper();

    int getWidth();

    int getHeight();

    void postInvalidate();

    ViewParent getParent();

    int getId();

    boolean getGlobalVisibleRect(Rect r);

    Context getContext();

    View getRootView();
}
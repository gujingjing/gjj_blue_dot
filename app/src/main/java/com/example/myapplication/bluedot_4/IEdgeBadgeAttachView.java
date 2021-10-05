package com.example.myapplication.bluedot_4;

import android.content.Context;

/**
 * Badge attach view property controller
 */
public interface IEdgeBadgeAttachView {
    int getWidth();

    int getHeight();

    boolean isEnabled();

    void postInvalidate();

    void requestLayout();

    Context getContext();
}

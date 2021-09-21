package com.example.myapplication.bluedot_v3;

import android.content.Context;
import android.view.ViewGroup;

public interface IEdgeBadgeView {
    String mExtraIdentifier = null;

    int getWidth();

    int getHeight();

    boolean isEnabled();

    void updateBadgeView();

    Context getContext();

    IEdgeBadgeController getBadgeController();

    String getNewItemIndicatorId();

    void onBadgeClick();

}

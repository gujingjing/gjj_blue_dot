package com.example.myapplication.bluedot_4;

public interface IEdgeBadgeView {
    IEdgeBadgeDrawer getBadgeDrawer();

    void onBadgeClick();

    void setBadgeViewVisible(boolean visible);
}

package com.example.myapplication.bluedot_4;

import com.example.myapplication.bluedot_4.drawer.IEdgeBadgeDrawer;

public interface IEdgeBadgeView {
    void onBadgeClick();

    void setBadgeViewVisible(boolean visible);

    IEdgeBadgeDrawer getBadgeDrawer();
}

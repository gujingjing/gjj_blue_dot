package com.example.myapplication.bluedot_v3;

public interface IEdgeBadgeConfig {
    IEdgeBadgeConfig setBadgeViewVisible(boolean visible);
    boolean getBadgeViewVisibility();

    String getBadgeViewId();
    IEdgeBadgeConfig setBadgeViewId(String id);
}

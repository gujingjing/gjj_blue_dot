package com.example.myapplication.bluedot_v3;

import com.example.myapplication.bluedot_4.IAttachViewController;

public class DefaultEdgeBadgeConfig implements IEdgeBadgeConfig {

    protected IAttachViewController controller;
    public DefaultEdgeBadgeConfig(IAttachViewController controller){
        this.controller=controller;
    }
    private String badgeViewId;
    private boolean visible = true;

    @Override
    public IEdgeBadgeConfig setBadgeViewVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public boolean getBadgeViewVisibility() {
        return visible;
    }

    @Override
    public String getBadgeViewId() {
        return badgeViewId;
    }

    @Override
    public IEdgeBadgeConfig setBadgeViewId(String id) {
        this.badgeViewId = id;
        return this;
    }
}

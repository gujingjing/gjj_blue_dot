package com.example.myapplication.bluedot_4.drawer;

import com.example.myapplication.bluedot_4.IEdgeBadgeAttachView;

public class EdgeAutoSizeBadgeDrawer extends EdgeBadgeDrawer {

    public EdgeAutoSizeBadgeDrawer(IEdgeBadgeAttachView viewController) {
        super(viewController);
    }

    /**
     * AutoSizeView need to  resize view, should request the api requestLayout
     */
    @Override
    public void updateBadgeView() {
        mBadgeViewController.requestLayout();
    }
}

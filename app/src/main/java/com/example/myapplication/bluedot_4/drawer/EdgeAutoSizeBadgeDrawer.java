package com.example.myapplication.bluedot_4.drawer;

import com.example.myapplication.bluedot_4.IViewController;

public class EdgeAutoSizeBadgeDrawer extends EdgeBadgeDrawer {

    public EdgeAutoSizeBadgeDrawer(IViewController viewController) {
        super(viewController);
    }

    @Override
    public IEdgeBadgeDrawer updateBadgeView() {
        mBadgeViewController.requestLayout();
        return this;
    }
}

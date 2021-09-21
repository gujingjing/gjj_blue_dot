package com.example.myapplication.bluedot_4.drawer;

import com.example.myapplication.bluedot_4.IViewController;

public class AutoSizeEdgeBadgeDrawer extends DefaultEdgeBadgeDrawer {

    public AutoSizeEdgeBadgeDrawer(IViewController viewController) {
        super(viewController);
    }

    @Override
    public IEdgeBadgeDrawer updateBadgeView() {
        mBadgeViewController.requestLayout();
        return this;
    }
}

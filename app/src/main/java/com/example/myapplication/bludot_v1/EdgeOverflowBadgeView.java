package com.example.myapplication.bludot_v1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class EdgeOverflowBadgeView extends EdgeBadgeView {

    public EdgeOverflowBadgeView(Context context) {
        super(context);
    }

    @Override
    public Badge bindTarget(View targetView) {
        if (targetView == null) {
            throw new IllegalStateException("targetView can not be null");
        }
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        ViewParent targetParent = targetView.getParent();

        if (targetParent != null && targetParent instanceof ViewGroup) {
            mTargetView = targetView;
            if (targetParent instanceof OverflowBadgeContainer) {
                ((OverflowBadgeContainer) targetParent).addView(this);
            } else {
                ViewGroup targetContainer = (ViewGroup) targetParent;
                int index = targetContainer.indexOfChild(targetView);
                ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
                targetContainer.removeView(targetView);
                final OverflowBadgeContainer badgeContainer = new OverflowBadgeContainer(getContext());
                targetContainer.addView(badgeContainer, index, targetParams);
                badgeContainer.addView(targetView);
                badgeContainer.addView(this);
            }
        } else {
            throw new IllegalStateException("targetView must have a parent");
        }
        return this;


    }

    private class OverflowBadgeContainer extends FrameLayout {

        public OverflowBadgeContainer(@NonNull Context context) {
            super(context);
        }

        public OverflowBadgeContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public OverflowBadgeContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

    }
}

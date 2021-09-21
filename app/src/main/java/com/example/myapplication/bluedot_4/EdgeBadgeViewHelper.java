package com.example.myapplication.bluedot_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.example.myapplication.R;
import com.example.myapplication.bluedot_4.drawer.IEdgeBadgeDrawer;

public class EdgeBadgeViewHelper {
    private IEdgeBadgeDrawer drawer;

    private String badgeViewId;
    private boolean visible = true;

    public EdgeBadgeViewHelper(@NonNull IEdgeBadgeDrawer drawer) {
        this.drawer = drawer;
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.badge_view);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(context, typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    public void onDraw(Canvas canvas) {
        if (!getBadgeViewVisibility()) return;
        drawer.onDraw(canvas);
    }

    public int[] getBadgeViewInfo() {
        return new int[]{drawer.getBadgeWidth(), drawer.getBadgeHeight()};
    }

    public void onBadgeClick() {
//        NewItemIndicatorManager.getInstance().onClickedById(config.getNewItemIndicatorId());
        drawer.updateBadgeView();
    }

    private void initCustomAttr(Context context, int attr, TypedArray typedArray) {
        if (attr == R.styleable.badge_view_badge_view_id) {
            badgeViewId = typedArray.getString(attr);
        } else if (attr == R.styleable.badge_view_badge_view_visible) {
            visible = typedArray.getBoolean(attr, visible);
        }
        drawer.initCustomAttr(context, attr, typedArray);
    }

    private boolean getBadgeViewVisibility() {
        return visible;
    }

    public void setBadgeViewVisible(boolean visible) {
        this.visible = visible;
        drawer.updateBadgeView();
    }
}

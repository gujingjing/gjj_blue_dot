package com.example.myapplication.bluedot_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.example.myapplication.R;
import com.example.myapplication.bludot_v1.DisplayUtil;

public class EdgeBadgeViewHelper implements IEdgeBadgeView {
    private IEdgeBadgeDrawer drawer;
    private Context context;
    private boolean visible = true;

    public EdgeBadgeViewHelper(Context context, @NonNull IEdgeBadgeDrawer drawer) {
        this.drawer = drawer;
        this.context = context;
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.badge_view);
        visible = typedArray.getBoolean(R.styleable.badge_view_badge_view_visible, visible);
        typedArray.recycle();

        drawer.initCustomAttrs(context, attrs, defStyleAttr);
    }

    public void onDraw(Canvas canvas) {
        if (!visible) return;
        drawer.onDraw(canvas);
    }

    /**
     * Return badgeView draw size info
     *      first:  width
     *      second: height
     */
    public int[] getBadgeViewSizeInfo() {
        return new int[]{drawer.getBadgeWidth(), drawer.getBadgeHeight()};
    }

    @Override
    public IEdgeBadgeView setBadgeText(String badgeText) {
        drawer.setBadgeText(badgeText);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeTextSize(float size, boolean isSpValue) {
        float badgeTextSize = isSpValue ? DisplayUtil.dp2px(context, size) : size;
        drawer.setBadgeTextSize(badgeTextSize);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeTextColor(int color) {
        drawer.setBadgeTextColor(color);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeBackgroundColor(int color) {
        drawer.setBadgeBackgroundColor(color);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeBackground(Drawable drawable) {
        drawer.setBadgeBackground(drawable);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgePadding(int padding, boolean isDpValue) {
        int badgePadding = isDpValue ? DisplayUtil.dp2px(context, padding) : padding;
        drawer.setBadgePadding(badgePadding);
        return this;
    }

    @Override
    public IEdgeBadgeView setMargin(int horizontalMargin, int verticalMargin, boolean isDpValue) {
        int hMargin = isDpValue ? DisplayUtil.dp2px(context, horizontalMargin) : horizontalMargin;
        int vMargin = isDpValue ? DisplayUtil.dp2px(context, verticalMargin) : verticalMargin;
        drawer.setMargin(hMargin, vMargin);
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeGravity(int gravity) {
        drawer.setBadgeGravity(gravity);
        return this;
    }

    public IEdgeBadgeView setBadgeViewVisible(boolean visible) {
        this.visible = visible;
        drawer.updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeView setBadgeViewType(int badgeViewType) {
        drawer.setBadgeViewType(badgeViewType);
        return this;
    }
}

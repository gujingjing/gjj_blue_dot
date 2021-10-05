package com.example.myapplication.bluedot_4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myapplication.bluedot_4.drawer.EdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_4.IEdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.IEdgeBadgeView;
import com.example.myapplication.bluedot_4.IEdgeBadgeAttachView;

public class EdgeBadgeTextView extends TextView implements IEdgeBadgeView, IEdgeBadgeAttachView {

    private EdgeBadgeViewHelper mEdgeBadgeViewHelper;
    private IEdgeBadgeDrawer drawer;

    public EdgeBadgeTextView(Context context) {
        this(context, null);
    }

    public EdgeBadgeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawer = new EdgeBadgeDrawer(this);
        mEdgeBadgeViewHelper = new EdgeBadgeViewHelper(context, drawer);
        mEdgeBadgeViewHelper.init(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEdgeBadgeViewHelper.onDraw(canvas);
    }

    @Override
    public IEdgeBadgeView setBadgeViewType(int badgeViewType) {
        return mEdgeBadgeViewHelper.setBadgeViewType(badgeViewType);
    }

    @Override
    public IEdgeBadgeView setBadgeText(String badgeText) {
        return mEdgeBadgeViewHelper.setBadgeText(badgeText);
    }

    @Override
    public IEdgeBadgeView setBadgeTextSize(float size, boolean isSpValue) {
        return mEdgeBadgeViewHelper.setBadgeTextSize(size, isSpValue);
    }

    @Override
    public IEdgeBadgeView setBadgeTextColor(int color) {
        return mEdgeBadgeViewHelper.setBadgeTextColor(color);
    }

    @Override
    public IEdgeBadgeView setBadgeBackgroundColor(int color) {
        return mEdgeBadgeViewHelper.setBadgeBackgroundColor(color);
    }

    @Override
    public IEdgeBadgeView setBadgeBackground(Drawable drawable) {
        return mEdgeBadgeViewHelper.setBadgeBackground(drawable);
    }

    @Override
    public IEdgeBadgeView setBadgePadding(float padding, boolean isDpValue) {
        return mEdgeBadgeViewHelper.setBadgePadding(padding, isDpValue);
    }

    @Override
    public IEdgeBadgeView setMargin(float horizontalMargin, float verticalMargin, boolean isDpValue) {
        return mEdgeBadgeViewHelper.setMargin(horizontalMargin, verticalMargin, isDpValue);
    }

    @Override
    public IEdgeBadgeView setBadgeGravity(int gravity) {
        return mEdgeBadgeViewHelper.setBadgeGravity(gravity);
    }

    @Override
    public IEdgeBadgeView setBadgeViewVisible(boolean visible) {
        return mEdgeBadgeViewHelper.setBadgeViewVisible(visible);
    }
}

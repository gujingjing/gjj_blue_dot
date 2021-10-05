package com.example.myapplication.bluedot_4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.bluedot_4.drawer.EdgeAutoSizeBadgeDrawer;
import com.example.myapplication.bluedot_4.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_4.IEdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.IEdgeBadgeView;
import com.example.myapplication.bluedot_4.IEdgeBadgeAttachView;

public class EdgeBadgeView extends View implements IEdgeBadgeView, IEdgeBadgeAttachView {

    private EdgeBadgeViewHelper mEdgeBadgeViewHelper;
    private IEdgeBadgeDrawer drawer;

    public EdgeBadgeView(Context context) {
        this(context, null);
    }

    public EdgeBadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawer = new EdgeAutoSizeBadgeDrawer(this);
        mEdgeBadgeViewHelper = new EdgeBadgeViewHelper(context, drawer);
        mEdgeBadgeViewHelper.init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int[] info = mEdgeBadgeViewHelper.getBadgeViewSizeInfo();
        setMeasuredDimension(info[0], info[1]);
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

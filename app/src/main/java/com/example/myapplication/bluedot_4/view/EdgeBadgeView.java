package com.example.myapplication.bluedot_4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.bluedot_4.drawer.EdgeAutoSizeBadgeDrawer;
import com.example.myapplication.bluedot_4.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_4.drawer.IEdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.IEdgeBadgeView;
import com.example.myapplication.bluedot_4.IViewController;

public class EdgeBadgeView extends View implements IEdgeBadgeView, IViewController {

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
        mEdgeBadgeViewHelper = new EdgeBadgeViewHelper(drawer);
        mEdgeBadgeViewHelper.init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int[] info = mEdgeBadgeViewHelper.getBadgeViewInfo();
        setMeasuredDimension(info[0], info[1]);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEdgeBadgeViewHelper.onDraw(canvas);
    }

    @Override
    public IEdgeBadgeDrawer getBadgeDrawer() {
        return drawer;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(v -> {
            l.onClick(v);
            onBadgeClick();
        });
    }

    @Override
    public void onBadgeClick() {
        mEdgeBadgeViewHelper.onBadgeClick();
    }

    @Override
    public void setBadgeViewVisible(boolean visible) {
        mEdgeBadgeViewHelper.setBadgeViewVisible(visible);
    }
}

package com.example.myapplication.bluedot_4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.myapplication.bluedot_4.drawer.EdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_4.drawer.IEdgeBadgeDrawer;
import com.example.myapplication.bluedot_4.IEdgeBadgeView;
import com.example.myapplication.bluedot_4.IViewController;

public class EdgeBadgeImageView extends ImageView implements IEdgeBadgeView, IViewController {
    private EdgeBadgeViewHelper mEdgeBadgeViewHelper;
    private IEdgeBadgeDrawer drawer;

    public EdgeBadgeImageView(Context context) {
        this(context, null);
    }

    public EdgeBadgeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawer = new EdgeBadgeDrawer(this);
        mEdgeBadgeViewHelper = new EdgeBadgeViewHelper(drawer);
        mEdgeBadgeViewHelper.init(context, attrs, defStyleAttr);
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

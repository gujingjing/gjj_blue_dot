package com.example.myapplication.bluedot_v2;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabWidget;

import com.example.myapplication.bludot_v1.EdgeBadgeView;

/**
 * 自定义绘制小红点（单独可以xml文件直接引用）
 * Created by liyanxi on 2016/10/17.
 * Copyright (c) 2016 www.zhengshijr.com. All rights reserved.
 */
public class BaseBadgeView extends View implements IBadgeView{

    BadgeViewUtil mBadgeView;
    public BaseBadgeView(Context context) {
        this(context,null);
    }

    public BaseBadgeView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BaseBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBadgeView = new BadgeViewUtil(this, context,attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBadgeView.onSizeChanged(w, h,true);
        //重置view的宽高
        ViewGroup.LayoutParams params=getLayoutParams();
        params.height=mBadgeView.getBadgeHeight();
        params.width=mBadgeView.getBadgeWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBadgeView.draw(canvas);
    }

    @Override
    public BadgeViewUtil setBadgeCount(int count) {
        return mBadgeView.setCount(count);
    }

    @Override
    public BadgeViewUtil setBadgeShown(boolean isShowBadge) {
        return mBadgeView.setShown(isShowBadge);
    }

    @Override
    public BadgeViewUtil setBadgeColor(int color) {
        return mBadgeView.setBackgroundColor(color);
    }

    @Override
    public BadgeViewUtil setmDefaultTopPadding(int mDefaultTopPadding) {
        return mBadgeView.setmDefaultTopPadding(mDefaultTopPadding);
    }

    @Override
    public BadgeViewUtil setmDefaultRightPadding(int mDefaultRightPadding) {
        return mBadgeView.setmDefaultRightPadding(mDefaultRightPadding);
    }

    /*
     * Attach the BadgeView to the TabWidget
     *
     * @param target the TabWidget to attach the BadgeView
     *
     * @param tabIndex index of the tab
     */
    public void setTargetView(TabWidget target, int tabIndex) {
        View tabView = target.getChildTabViewAt(tabIndex);
        setTargetView(tabView);
    }

    /*
     * Attach the BadgeView to the target view
     *
     * @param target the view to attach the BadgeView
     */
    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (target == null) {
            return;
        }

        if (target.getParent() instanceof BadgeContainer) {
            ((BadgeContainer) target.getParent()).addView(this);

        } else if (target.getParent() instanceof ViewGroup) {
            // use a new FrameLayout container for adding badge view
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);

            BadgeContainer badgeContainer = new BadgeContainer(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

            badgeContainer.setLayoutParams(parentLayoutParams);

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            target.setLayoutParams(params);

            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);

            badgeContainer.addView(this);
        } else if (target.getParent() == null) {
            Log.e(getClass().getSimpleName(), "ParentView is needed");
        }

    }

    private class BadgeContainer extends ViewGroup {

        @Override
        protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
            if (!(getParent() instanceof RelativeLayout)) {
                super.dispatchRestoreInstanceState(container);
            }
        }

        public BadgeContainer(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            View targetView = null, badgeView = null;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!(child instanceof EdgeBadgeView)) {
                    targetView = child;
                } else {
                    badgeView = child;
                }
            }
            if (targetView == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                targetView.measure(widthMeasureSpec, heightMeasureSpec);
                if (badgeView != null) {
                    badgeView.measure(MeasureSpec.makeMeasureSpec(targetView.getMeasuredWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(targetView.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
                setMeasuredDimension(targetView.getMeasuredWidth(), targetView.getMeasuredHeight());
            }
        }
    }
}

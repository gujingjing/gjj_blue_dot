package com.example.myapplication.bluedot_v3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myapplication.bluedot.NewItemIndicatorManager;
import com.example.myapplication.bluedot_v3.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_v3.IEdgeBadgeController;
import com.example.myapplication.bluedot_v3.IEdgeBadgeView;

public class EdgeBadgeTextView extends TextView implements IEdgeBadgeView {

    private EdgeBadgeViewHelper mBadgeViewHelper;

    public EdgeBadgeTextView(Context context) {
        this(context, null);
    }

    public EdgeBadgeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBadgeViewHelper = new EdgeBadgeViewHelper(this, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHelper.onDraw(canvas);
    }

    @Override
    public IEdgeBadgeController getBadgeController() {
        return mBadgeViewHelper;
    }

    @Override
    public String getNewItemIndicatorId() {
        StringBuilder sb = new StringBuilder(mBadgeViewHelper.getBadgeGroupId());
        if (!TextUtils.isEmpty(mExtraIdentifier)) {
            sb.append(":").append(mExtraIdentifier);
        }
        return sb.toString();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(v -> {
            onBadgeClick();
        });
    }

    @Override
    public void onBadgeClick() {
        NewItemIndicatorManager.getInstance().onClickedById(getNewItemIndicatorId());
        postInvalidate();
    }
}

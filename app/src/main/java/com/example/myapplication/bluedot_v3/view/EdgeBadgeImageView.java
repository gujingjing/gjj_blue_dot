package com.example.myapplication.bluedot_v3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.myapplication.bluedot.NewItemIndicatorManager;
import com.example.myapplication.bluedot_v3.EdgeBadgeViewHelper;
import com.example.myapplication.bluedot_v3.IEdgeBadgeController;
import com.example.myapplication.bluedot_v3.IEdgeBadgeView;

public class EdgeBadgeImageView extends ImageView implements IEdgeBadgeView {
    private EdgeBadgeViewHelper mBadgeViewHelper;

    public EdgeBadgeImageView(Context context) {
        this(context, null);
    }

    public EdgeBadgeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeBadgeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBadgeViewHelper = new EdgeBadgeViewHelper(this, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBadgeViewHelper.onDraw(canvas);
    }

    @Override
    public void updateBadgeView() {
        postInvalidate();
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
            l.onClick(v);
            onBadgeClick();
        });
    }

    @Override
    public void onBadgeClick() {
        NewItemIndicatorManager.getInstance().onClickedById(getNewItemIndicatorId());
        updateBadgeView();
    }
}

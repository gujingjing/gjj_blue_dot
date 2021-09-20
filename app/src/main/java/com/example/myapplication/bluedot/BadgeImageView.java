package com.example.myapplication.bluedot;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ImageView that draw circle badge on the top right corner if needed, use "id(:extraidentifier)" as new item indicator id
 */
public class BadgeImageView extends ImageView implements INewItemIndicatorView {
    private String mExtraIdentifier;

    public BadgeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setExtraIdentifier(String extraIdentifier) {
        mExtraIdentifier = extraIdentifier;
    }

    @Override
    public String getNewItemIndicatorId() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(mExtraIdentifier)) {
            sb.append(":").append(mExtraIdentifier);
        }
        return sb.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BadgeViewOnDrawHelper.onDrawBadge(canvas, this);
    }
}


package com.example.myapplication.bluedot;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.math.MathUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * An helper class helps measure and draw new item indicator on an {@link ImageView}, it requires
 * the {@link ImageView} implements {@link INewItemIndicatorView}
 * 
 * @author zhagao
 */
public class BadgeViewOnDrawHelper {

    private static volatile Paint mBadgeBackgroundPaint;

    private static void initPaint() {
        mBadgeBackgroundPaint = new Paint();
        mBadgeBackgroundPaint.setAntiAlias(true);
        mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    private static void ensurePaint() {
        if (mBadgeBackgroundPaint == null) {
            synchronized (BadgeViewOnDrawHelper.class) {
                if (mBadgeBackgroundPaint == null) {
                    initPaint();
                }
            }
        }
    }

    /**
     * Draw badge optionally when the view is configured rule to display badge
     */
    public static void onDrawBadge(@NonNull Canvas canvas, @NonNull View view) {
        if (!view.isEnabled()) {
            return;
        }
        if (!(view instanceof INewItemIndicatorView)) {
            return; //no-op when view not implemented INewItemIndicatorView
        }
        String newItemIndicatorId = ((INewItemIndicatorView) view).getNewItemIndicatorId();
        if (NewItemIndicatorManager.getInstance().shouldShowBadgeById(newItemIndicatorId)) {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            canvas.concat(view.getMatrix());

            Rect rect = new Rect();
            view.getDrawingRect(rect);

            ensurePaint();
            // dynamic set color to ensure theme compatible
            mBadgeBackgroundPaint.setColor(view.getResources().getColor(R.color.new_item_indication_dot_color));

            @INewItemIndicatorView.Corner int corner = ((INewItemIndicatorView) view).showAtCorner();
            float circleCenterX=rect.left;
            switch (corner) {
                case INewItemIndicatorView.Corner.TOP_START:
                case INewItemIndicatorView.Corner.CENTER_START:
                case INewItemIndicatorView.Corner.BOTTOM_START:
//                    circleCenterX = (ApiCompatibilityUtils.isLayoutRtl(view) ? rect.right : rect.left);
                    break;
                case INewItemIndicatorView.Corner.TOP_CENTER:
                case INewItemIndicatorView.Corner.BOTTOM_CENTER:
                    circleCenterX = rect.centerX();
                    break;
                case INewItemIndicatorView.Corner.TOP_END:
                case INewItemIndicatorView.Corner.CENTER_END:
                case INewItemIndicatorView.Corner.BOTTOM_END:
                default:
//                    circleCenterX = (ApiCompatibilityUtils.isLayoutRtl(view) ? rect.left : rect.right);
                    break;
            }
            float circleCenterY;
            switch (corner) {
                case INewItemIndicatorView.Corner.TOP_START:
                case INewItemIndicatorView.Corner.TOP_CENTER:
                case INewItemIndicatorView.Corner.TOP_END:
                default:
                    circleCenterY = rect.top;
                    break;
                case INewItemIndicatorView.Corner.CENTER_START:
                case INewItemIndicatorView.Corner.CENTER_END:
                    circleCenterY = rect.centerY();
                    break;
                case INewItemIndicatorView.Corner.BOTTOM_START:
                case INewItemIndicatorView.Corner.BOTTOM_CENTER:
                case INewItemIndicatorView.Corner.BOTTOM_END:
                    circleCenterY = rect.bottom;
                    break;
            }

            circleCenterX += view.getPaddingLeft();
            circleCenterY += view.getPaddingTop();
            float radius = view.getResources().getDimension(((INewItemIndicatorView) view).indicatorDiameterRes()) / 2;
            safeDrawCircle(canvas, circleCenterX, circleCenterY, radius, mBadgeBackgroundPaint);
            canvas.restoreToCount(saveCount);
        }
    }

    /**
     * Ensure draw circle inside the canvas.
     */
    private static void safeDrawCircle(@NonNull Canvas canvas, float centerX, float centerY,
                                       float radius, Paint paint) {
        int radiusCeil = (int) Math.ceil(radius);
        canvas.drawCircle(MathUtils.clamp(centerX, radiusCeil, canvas.getWidth() - radiusCeil),
                MathUtils.clamp(centerY, radiusCeil, canvas.getHeight() - radiusCeil), radius,
                paint);
    }
}


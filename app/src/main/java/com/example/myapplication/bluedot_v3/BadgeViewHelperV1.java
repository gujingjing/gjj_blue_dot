package com.example.myapplication.bluedot_v3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.myapplication.R;

/**
 *
 */
public class BadgeViewHelperV1 {
    private Bitmap mBitmap;
    private IBadgeV1 mBadgeable;
    private Paint mBadgePaint;
    /**
     * 徽章背景色
     */
    private int mBadgeBgColor;
    private Drawable mBadgeBackDrawable;
    /**
     * 徽章文本的颜色
     */
    private int mBadgeTextColor;
    /**
     * 徽章文本字体大小
     */
    private int mBadgeTextSize;

    private BadgeMargin badgeViewMargin;
    private BadgeMargin badgeViewPadding;

    /**
     * 徽章文本
     */
    private String mBadgeText;
    /**
     * 徽章文本所占区域大小
     */
    private Rect mBadgeNumberRect;
    /**
     * 是否显示Badge
     */
    private boolean mIsShowBadge;
    /**
     * 徽章在宿主控件中的位置
     */
    private BadgeGravity mBadgeGravity = BadgeGravity.RIGHT_TOP;
    /**
     * 整个徽章所占区域
     */
    private RectF mBadgeRectF;

    private boolean mIsShowDrawable = false;

    public BadgeViewHelperV1(IBadgeV1 badgeable, Context context, AttributeSet attrs) {
        mBadgeable = badgeable;
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
    }

    private void initDefaultAttrs(Context context) {
        mBadgeNumberRect = new Rect();
        mBadgeRectF = new RectF();
        mBadgeBgColor = Color.RED;
        mBadgeTextColor = Color.WHITE;
        mBadgeTextSize = BadgeViewUtilV1.sp2px(context, 11);

        mBadgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBadgePaint.setAntiAlias(true);
//        mBadgePaint.setStyle(Paint.Style.FILL);
        // 设置mBadgeText居中，保证mBadgeText长度为1时，文本也能居中
        mBadgePaint.setTextAlign(Paint.Align.CENTER);

        badgeViewMargin = new BadgeMargin();
        int hPadding = BadgeViewUtilV1.sp2px(context, 4);
        int vPadding = BadgeViewUtilV1.sp2px(context, 2);
        badgeViewPadding = new BadgeMargin(hPadding, vPadding, hPadding, vPadding);

        mBadgeGravity = BadgeGravity.RIGHT_TOP;
        mIsShowBadge = true;
        mBadgeText = null;
        mBitmap = null;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.badge_view);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(context, typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(Context context, int attr, TypedArray typedArray) {
        if (attr == R.styleable.badge_view_badge_background_color) {
            mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
        } else if (attr == R.styleable.badge_view_badge_background) {
            int badgeBackSource = typedArray.getResourceId(attr, -1);
            if (badgeBackSource != -1) {
                mBadgeBackDrawable = context.getResources().getDrawable(badgeBackSource);
            }
        } else if (attr == R.styleable.badge_view_badge_text_color) {
            mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
        } else if (attr == R.styleable.badge_view_badge_text_size) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr, mBadgeTextSize);
        }
//        else if (attr == R.styleable.badge_view_badge_margin_left) {
//            badgeViewMargin.leftMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_margin_top) {
//            badgeViewMargin.topMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_margin_right) {
//            badgeViewMargin.rightMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_margin_bottom) {
//            badgeViewMargin.bottomMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_padding_left) {
//            badgeViewPadding.leftMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_padding_top) {
//            badgeViewPadding.topMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_padding_right) {
//            badgeViewPadding.rightMargin = typedArray.getDimensionPixelSize(attr, 0);
//        } else if (attr == R.styleable.badge_view_badge_padding_bottom) {
//            badgeViewPadding.bottomMargin = typedArray.getDimensionPixelSize(attr, 0);
//        }
        else if (attr == R.styleable.badge_view_badge_gravity) {
            int ordinal = typedArray.getInt(attr, mBadgeGravity.ordinal());
            mBadgeGravity = BadgeGravity.values()[ordinal];
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        mBadgePaint.setTextSize(mBadgeTextSize);
    }

    public void setBadgeBackDrawable(Drawable badgeBackDrawable) {
        this.mBadgeBackDrawable = badgeBackDrawable;
        mBadgeable.postInvalidate();
    }

    public void setBadgeBgColorInt(int badgeBgColor) {
        mBadgeBgColor = badgeBgColor;
        mBadgeable.postInvalidate();
    }

    public void setBadgeTextColorInt(int badgeTextColor) {
        mBadgeTextColor = badgeTextColor;
        mBadgeable.postInvalidate();
    }

    public void setBadgeTextSizeSp(int badgetextSize) {
        if (badgetextSize >= 0) {
            mBadgeTextSize = BadgeViewUtilV1.sp2px(mBadgeable.getContext(), badgetextSize);
            mBadgePaint.setTextSize(mBadgeTextSize);
            mBadgeable.postInvalidate();
        }
    }

    public void setBadgeMarginDp(BadgeMargin margin) {
        if (margin != null) {
            badgeViewMargin = margin;
            mBadgeable.postInvalidate();
        }
    }

    public void setBadgePaddingDp(BadgeMargin padding) {
        if (padding != null) {
            badgeViewPadding = padding;
            mBadgeable.postInvalidate();
        }
    }

    public void setBadgeGravity(BadgeGravity badgeGravity) {
        mBadgeGravity = badgeGravity;
        mBadgeable.postInvalidate();
    }

    public void drawBadge(Canvas canvas) {
        drawTextBadge(canvas);
        if (mIsShowBadge) {
            drawTextBadge(canvas);
//            if (mIsShowDrawable) {
//                drawDrawableBadge(canvas);
//            } else {
//                drawTextBadge(canvas);
//            }
        }
    }

    /**
     * 绘制图像徽章
     *
     * @param canvas
     */
    private void drawDrawableBadge(Canvas canvas) {
        switch (mBadgeGravity) {
            case LEFT_TOP:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.top = badgeViewMargin.topMargin;
                break;
            case LEFT_CENTER:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.top = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            case LEFT_BOTTOM:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.top = mBadgeable.getHeight() - mBitmap.getHeight() - badgeViewMargin.bottomMargin;
                break;
            case RIGHT_TOP:
                mBadgeRectF.left = mBadgeable.getWidth() - badgeViewMargin.rightMargin - mBitmap.getWidth();
                mBadgeRectF.top = badgeViewMargin.topMargin;
                break;
            case RIGHT_CENTER:
                mBadgeRectF.left = mBadgeable.getWidth() - badgeViewMargin.rightMargin - mBitmap.getWidth();
                mBadgeRectF.top = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
                break;
            case RIGHT_BOTTOM:
                mBadgeRectF.left = mBadgeable.getWidth() - badgeViewMargin.rightMargin - mBitmap.getWidth();
                mBadgeRectF.top = mBadgeable.getHeight() - mBitmap.getHeight() - badgeViewMargin.bottomMargin;
                break;

            default:
                break;
        }
        canvas.drawBitmap(mBitmap, mBadgeRectF.left, mBadgeRectF.top, mBadgePaint);
        mBadgeRectF.right = mBadgeRectF.left + mBitmap.getWidth();
        mBadgeRectF.bottom = mBadgeRectF.top + mBitmap.getHeight();
    }

    /**
     * 绘制文字徽章
     *
     * @param canvas
     */
    private void drawTextBadge(Canvas canvas) {
        String badgeText = "";
        if (!TextUtils.isEmpty(mBadgeText)) {
            badgeText = mBadgeText;
        }
        // 获取文本宽所占宽高
        mBadgePaint.getTextBounds(badgeText, 0, badgeText.length(), mBadgeNumberRect);
        // 计算徽章背景的宽高
        int badgeHeight = mBadgeNumberRect.height() + badgeViewPadding.bottomMargin + badgeViewPadding.topMargin;
        int badgeWidth;
        // 当mBadgeText的长度为1或0时，计算出来的高度会比宽度大，此时设置宽度等于高度
        if (badgeText.length() == 1 || badgeText.length() == 0) {
            badgeWidth = badgeHeight;
        } else {
            badgeWidth = mBadgeNumberRect.width() + badgeViewPadding.leftMargin + badgeViewPadding.rightMargin;
        }

        // 计算徽章背景上下的值
        mBadgeRectF.left = badgeViewMargin.leftMargin;
        mBadgeRectF.bottom = mBadgeable.getHeight() - badgeViewMargin.bottomMargin;
        switch (mBadgeGravity) {
            case LEFT_TOP:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.top = badgeViewMargin.topMargin;
                mBadgeRectF.right = mBadgeRectF.left + badgeWidth;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case LEFT_CENTER:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.top = (mBadgeable.getHeight() - badgeHeight) / 2;
                mBadgeRectF.right = mBadgeRectF.left + badgeWidth;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case LEFT_BOTTOM:
                mBadgeRectF.left = badgeViewMargin.leftMargin;
                mBadgeRectF.bottom = mBadgeable.getHeight() - badgeViewMargin.bottomMargin;
                mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight;
                mBadgeRectF.right = mBadgeRectF.left + badgeWidth;
                break;
            case RIGHT_TOP:
                mBadgeRectF.top = badgeViewMargin.topMargin;
                mBadgeRectF.right = mBadgeable.getWidth() - badgeViewMargin.rightMargin;
                mBadgeRectF.left = mBadgeRectF.right - badgeWidth;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RIGHT_CENTER:
                mBadgeRectF.right = mBadgeable.getWidth() - badgeViewMargin.rightMargin;
                mBadgeRectF.left = mBadgeRectF.right - badgeWidth;
                mBadgeRectF.top = (mBadgeable.getHeight() - badgeHeight) / 2;
                mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
                break;
            case RIGHT_BOTTOM:
                mBadgeRectF.bottom = mBadgeable.getHeight() - badgeViewMargin.bottomMargin;
                mBadgeRectF.right = mBadgeable.getWidth() - badgeViewMargin.rightMargin;
                mBadgeRectF.left = mBadgeRectF.right - badgeWidth;
                mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight;
                break;

            default:
                break;
        }

        //绘制背景
        if (mBadgeBackDrawable != null) {
            mBadgeBackDrawable.setBounds(
                    (int) mBadgeRectF.left,
                    (int) mBadgeRectF.top,
                    (int) mBadgeRectF.right,
                    (int) mBadgeRectF.bottom);
            mBadgeBackDrawable.draw(canvas);
        } else {
            // 设置徽章背景色
            mBadgePaint.setColor(mBadgeBgColor);
            // 绘制徽章背景
            canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);
        }

        if (!TextUtils.isEmpty(mBadgeText)) {
            // 设置徽章文本颜色
            mBadgePaint.setColor(mBadgeTextColor);
            // initDefaultAttrs方法中设置了mBadgeText居中，此处的x为徽章背景的中心点y
            float x = mBadgeRectF.left + badgeWidth / 2;
            // 注意：绘制文本时的y是指文本底部，而不是文本的中间
            float y = mBadgeRectF.bottom - badgeViewPadding.bottomMargin;
            // 绘制徽章文本
            canvas.drawText(badgeText, x, y, mBadgePaint);
        }
    }

    public void showCirclePointBadge() {
        showTextBadge(null);
    }

    public void showTextBadge(String badgeText) {
        mIsShowDrawable = false;
        mBadgeText = badgeText;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    public void hiddenBadge() {
        mIsShowBadge = false;
        mBadgeable.postInvalidate();
    }

    public boolean isShowBadge() {
        return mIsShowBadge;
    }

    public void showDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mIsShowDrawable = true;
        mIsShowBadge = true;
        mBadgeable.postInvalidate();
    }

    class BadgeMargin {
        int leftMargin;
        int rightMargin;
        int topMargin;
        int bottomMargin;

        public BadgeMargin() {
        }

        public BadgeMargin(int left, int right, int top, int bottom) {
            this.leftMargin = left;
            this.rightMargin = right;
            this.topMargin = top;
            this.bottomMargin = bottom;
        }
    }


    public enum BadgeGravity {
        RIGHT_TOP,
        RIGHT_CENTER,
        RIGHT_BOTTOM,
        LEFT_TOP,
        LEFT_CENTER,
        LEFT_BOTTOM
    }
}
package com.example.myapplication.bluedot_4.drawer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.myapplication.R;
import com.example.myapplication.bludot_v1.DisplayUtil;
import com.example.myapplication.bluedot_4.EdgeBadgeGravity;
import com.example.myapplication.bluedot_4.EdgeBadgeViewType;
import com.example.myapplication.bluedot_4.IEdgeBadgeAttachView;
import com.example.myapplication.bluedot_4.IEdgeBadgeDrawer;

public class EdgeBadgeDrawer implements IEdgeBadgeDrawer {
    private int mColorBadgeText;
    private float mBadgeTextSize;

    private Drawable mDrawableBackground;
    private int mColorBackground;

    private int mHorizontalBadgePadding;
    private int mVerticalBadgePadding;

    private String mBadgeText;
    private int mBadgeGravity = EdgeBadgeGravity.TOP_END;
    private int mBadgeViewType = EdgeBadgeViewType.TYPE_DOT;
    private int mGravityOffsetX;
    private int mGravityOffsetY;
    private int mRedDotRadios;

    private RectF mBadgeTextRect;
    private RectF mBadgeBackgroundRect;
    private Paint.FontMetrics mBadgeTextFontMetrics;
    private PointF mBadgeCenter;
    private TextPaint mBadgeTextPaint;
    private Paint mBadgeBackgroundPaint;

    protected IEdgeBadgeAttachView mBadgeViewController;

    public EdgeBadgeDrawer(IEdgeBadgeAttachView viewController) {
        this.mBadgeViewController = viewController;
        init(mBadgeViewController.getContext());
    }

    private void init(Context context) {
        mBadgeTextRect = new RectF();
        mBadgeBackgroundRect = new RectF();
        mBadgeCenter = new PointF();
        mBadgeTextPaint = new TextPaint();
        mBadgeTextPaint.setAntiAlias(true);
        mBadgeTextPaint.setSubpixelText(true);
        mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mBadgeBackgroundPaint = new Paint();
        mBadgeBackgroundPaint.setAntiAlias(true);
        mBadgeBackgroundPaint.setStyle(Paint.Style.FILL);
        mColorBackground = context.getResources().getColor(R.color.new_item_indication_dot_back_color);
        mColorBadgeText = context.getResources().getColor(R.color.new_item_indication_dot_inner_text_color);
        mBadgeTextSize = DisplayUtil.dp2px(context, 11);
        mHorizontalBadgePadding = DisplayUtil.dp2px(context, 4);
        mVerticalBadgePadding = DisplayUtil.dp2px(context, 2);
        mRedDotRadios = DisplayUtil.dp2px(context, 4);
    }

    public void onDraw(@NonNull Canvas canvas) {
        if (!mBadgeViewController.isEnabled()) return;
        resetPaints();
        measureText();
        findBadgeCenter();
        if (mBadgeViewType == EdgeBadgeViewType.TYPE_DOT) {
            drawDot(canvas, mBadgeCenter, getBadgeCircleRadius());
        } else {
            drawText(canvas, mBadgeCenter, getBadgeCircleRadius());
        }
    }

    private void drawDot(Canvas canvas, PointF center, float radius) {
        if (center.x == -1000 && center.y == -1000) {
            return;
        }
        mBadgeBackgroundRect.left = center.x - (int) radius;
        mBadgeBackgroundRect.top = center.y - (int) radius;
        mBadgeBackgroundRect.right = center.x + (int) radius;
        mBadgeBackgroundRect.bottom = center.y + (int) radius;
        canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundPaint);

    }

    @Override
    public int getBadgeWidth() {
        float radios = getBadgeCircleRadius();
        return (int) (mHorizontalBadgePadding * 2 + mGravityOffsetX * 2 + Math.max(Math.max(mBadgeTextRect.width(), mBadgeTextRect.height()), radios));
    }

    @Override
    public int getBadgeHeight() {
        float radios = getBadgeCircleRadius();
        return (int) (mVerticalBadgePadding * 2 + mGravityOffsetY * 2 + Math.max(mBadgeTextRect.height(), radios));
    }

    @Override
    public IEdgeBadgeDrawer setBadgeViewType(int badgeViewType) {
        mBadgeViewType = badgeViewType;
        updateBadgeView();
        return this;
    }

    @Override
    public void updateBadgeView() {
        mBadgeViewController.postInvalidate();
    }

    @Override
    public IEdgeBadgeDrawer setBadgeText(String badgeText) {
        mBadgeText = badgeText;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgeTextColor(int color) {
        mColorBadgeText = color;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgeTextSize(float size) {
        mBadgeTextSize = size;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgeBackgroundColor(int color) {
        mColorBackground = color;
        if (mColorBackground == Color.TRANSPARENT) {
            mBadgeTextPaint.setXfermode(null);
        } else {
            mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgeBackground(Drawable drawable) {
        mDrawableBackground = drawable;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgePadding(int padding) {
        mHorizontalBadgePadding = padding;
        mVerticalBadgePadding = padding;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setBadgeGravity(@EdgeBadgeGravity int gravity) {
        mBadgeGravity = gravity;
        updateBadgeView();
        return this;
    }

    @Override
    public IEdgeBadgeDrawer setMargin(int horizontalMargin, int verticalMargin) {
        mGravityOffsetX = horizontalMargin;
        mGravityOffsetY = verticalMargin;
        updateBadgeView();
        return this;
    }

    private void resetPaints() {
        mBadgeBackgroundPaint.setColor(mColorBackground);
        mBadgeTextPaint.setColor(mColorBadgeText);
        mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void drawText(Canvas canvas, PointF center, float radius) {
        if (center.x == -1000 && center.y == -1000) {
            return;
        }
        if (TextUtils.isEmpty(mBadgeText)) return;
        if (mBadgeText.length() == 1) {
            mBadgeBackgroundRect.left = center.x - (int) radius;
            mBadgeBackgroundRect.top = center.y - (int) radius;
            mBadgeBackgroundRect.right = center.x + (int) radius;
            mBadgeBackgroundRect.bottom = center.y + (int) radius;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawCircle(center.x, center.y, radius, mBadgeBackgroundPaint);
            }
        } else {
            mBadgeBackgroundRect.left = center.x - (mBadgeTextRect.width() / 2f + mHorizontalBadgePadding);
            mBadgeBackgroundRect.top = center.y - (mBadgeTextRect.height() / 2f + mVerticalBadgePadding);
            mBadgeBackgroundRect.right = center.x + (mBadgeTextRect.width() / 2f + mHorizontalBadgePadding);
            mBadgeBackgroundRect.bottom = center.y + (mBadgeTextRect.height() / 2f + mVerticalBadgePadding);
            radius = mBadgeBackgroundRect.height() / 2f;
            if (mDrawableBackground != null) {
                drawBadgeBackground(canvas);
            } else {
                canvas.drawRoundRect(mBadgeBackgroundRect, radius, radius, mBadgeBackgroundPaint);
            }
        }
        float y = (mBadgeBackgroundRect.bottom + mBadgeBackgroundRect.top
                - mBadgeTextFontMetrics.bottom - mBadgeTextFontMetrics.top) / 2f;
        canvas.drawText(mBadgeText, center.x, y, mBadgeTextPaint);
    }

    private void findBadgeCenter() {
        int mWidth = mBadgeViewController.getWidth();
        int mHeight = mBadgeViewController.getHeight();
        float rectWidth = Math.max(mBadgeTextRect.height(), mBadgeTextRect.width());
        float rectHeight = mBadgeTextRect.height();

        switch (mBadgeGravity) {
            case EdgeBadgeGravity.TOP_START:
                mBadgeCenter.x = mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f;
                break;
            case EdgeBadgeGravity.BOTTOM_START:
                mBadgeCenter.x = mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f);
                break;
            case EdgeBadgeGravity.TOP_END:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f;
                break;
            case EdgeBadgeGravity.BOTTOM_END:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f);
                break;
            case EdgeBadgeGravity.CENTER_CENTER:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case EdgeBadgeGravity.TOP_CENTER:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f;
                break;
            case EdgeBadgeGravity.BOTTOM_CENTER:
                mBadgeCenter.x = mWidth / 2f;
                mBadgeCenter.y = mHeight - (mGravityOffsetY + mVerticalBadgePadding + rectHeight / 2f);
                break;
            case EdgeBadgeGravity.CENTER_START:
                mBadgeCenter.x = mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f;
                mBadgeCenter.y = mHeight / 2f;
                break;
            case EdgeBadgeGravity.CENTER_END:
                mBadgeCenter.x = mWidth - (mGravityOffsetX + mHorizontalBadgePadding + rectWidth / 2f);
                mBadgeCenter.y = mHeight / 2f;
                break;
        }
    }

    @Override
    public void initCustomAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.badge_view);
        int colorBackground = typedArray.getColor(R.styleable.badge_view_badge_background_color, -1);
        if (colorBackground != -1) {
            mColorBackground = colorBackground;
        }
        int badgeBackSource = typedArray.getResourceId(R.styleable.badge_view_badge_background, -1);
        if (badgeBackSource != -1) {
            mDrawableBackground = context.getResources().getDrawable(badgeBackSource);
        }
        mColorBadgeText = typedArray.getColor(R.styleable.badge_view_badge_text_color, mColorBadgeText);
        mBadgeTextSize = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_text_size, (int) mBadgeTextSize);
        mHorizontalBadgePadding = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_horizontal_padding, mHorizontalBadgePadding);
        mVerticalBadgePadding = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_vertical_padding, mVerticalBadgePadding);
        mGravityOffsetX = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_horizontal_margin, mGravityOffsetX);
        mGravityOffsetY = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_vertical_margin, mGravityOffsetY);
        mBadgeGravity = typedArray.getInt(R.styleable.badge_view_badge_gravity, mBadgeGravity);
        mBadgeText = typedArray.getString(R.styleable.badge_view_badge_text);
        mRedDotRadios = typedArray.getDimensionPixelSize(R.styleable.badge_view_badge_red_dot_radio, mRedDotRadios);
        mBadgeViewType = typedArray.getInt(R.styleable.badge_view_badge_type, mBadgeViewType);

        typedArray.recycle();

        measureText();
    }

    private void drawBadgeBackground(Canvas canvas) {
        mBadgeBackgroundPaint.setShadowLayer(0, 0, 0, 0);
        int left = (int) mBadgeBackgroundRect.left;
        int top = (int) mBadgeBackgroundRect.top;
        int right = (int) mBadgeBackgroundRect.right;
        int bottom = (int) mBadgeBackgroundRect.bottom;
        mDrawableBackground.setBounds(left, top, right, bottom);
        mDrawableBackground.draw(canvas);
    }

    private void measureText() {
        mBadgeTextRect.left = 0;
        mBadgeTextRect.top = 0;
        if (mBadgeViewType == EdgeBadgeViewType.TYPE_DOT) {
            mBadgeTextRect.right = mRedDotRadios;
            mBadgeTextRect.bottom = mRedDotRadios;
        } else if (mBadgeText != null) {
            mBadgeTextPaint.setTextSize(mBadgeTextSize);
            mBadgeTextRect.right = mBadgeTextPaint.measureText(mBadgeText);
            mBadgeTextFontMetrics = mBadgeTextPaint.getFontMetrics();
            mBadgeTextRect.bottom = mBadgeTextFontMetrics.descent - mBadgeTextFontMetrics.ascent;
        }
    }

    private float getBadgeCircleRadius() {
        if (mBadgeViewType == EdgeBadgeViewType.TYPE_DOT) {
            return mRedDotRadios;
        } else if (TextUtils.isEmpty(mBadgeText) || mBadgeText.length() == 1) {
            return mBadgeTextRect.height() > mBadgeTextRect.width() ?
                    mBadgeTextRect.height() / 2f + mVerticalBadgePadding * 0.5f :
                    mBadgeTextRect.width() / 2f + mHorizontalBadgePadding * 0.5f;
        } else {
            return mBadgeBackgroundRect.height() / 2f;
        }
    }
}

package com.example.myapplication.bluedot_v3;

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
import android.util.Log;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.bludot_v1.DisplayUtil;
import com.example.myapplication.bluedot.NewItemIndicatorManager;

public class EdgeBadgeViewHelper implements IEdgeBadgeController {
    private int mColorBadgeText;
    private float mBadgeTextSize;

    private Drawable mDrawableBackground;
    private int mColorBackground;

    private float mHorizontalBadgePadding;
    private float mVerticalBadgePadding;

    private int mBadgeNumber;
    private String mBadgeText;
    private int mBadgeGravity = EdgeBadgeGravity.TOP_END;
    private float mGravityOffsetX;
    private float mGravityOffsetY;
    private int mRedDotRadios;
    private boolean autoUpdateAttachViewSize = false;
    private String badgeViewGroupId;

    private RectF mBadgeTextRect;
    private RectF mBadgeBackgroundRect;
    private Paint.FontMetrics mBadgeTextFontMetrics;
    private PointF mBadgeCenter;
    private TextPaint mBadgeTextPaint;
    private Paint mBadgeBackgroundPaint;

    private IEdgeBadgeView iBadge;
    private String TAG = "BadgeViewHelperV2";

    public EdgeBadgeViewHelper(IEdgeBadgeView iBadge, AttributeSet attrs) {
        this.iBadge = iBadge;
        init(iBadge.getContext(), attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBadgeTextRect = new RectF();
        mBadgeBackgroundRect = new RectF();
        mBadgeCenter = new PointF();
        mBadgeTextPaint = new TextPaint();
        mBadgeTextPaint.setAntiAlias(true);
        mBadgeTextPaint.setSubpixelText(true);
        mBadgeTextPaint.setFakeBoldText(true);
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
        initCustomAttrs(context, attrs);
        measureText();
    }

    private void drawBadge(Canvas canvas, PointF center, float radius) {
        if (center.x == -1000 && center.y == -1000) {
            return;
        }
        if (TextUtils.isEmpty(mBadgeText) || mBadgeText.length() == 1) {
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
        if (!TextUtils.isEmpty(mBadgeText)) {
            float y = (mBadgeBackgroundRect.bottom + mBadgeBackgroundRect.top
                    - mBadgeTextFontMetrics.bottom - mBadgeTextFontMetrics.top) / 2f;
            canvas.drawText(mBadgeText, center.x, y, mBadgeTextPaint);
        }
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
        if (TextUtils.isEmpty(mBadgeText)) {
            mBadgeTextRect.right = mRedDotRadios;
            mBadgeTextRect.bottom = mRedDotRadios;
        } else {
            mBadgeTextPaint.setTextSize(mBadgeTextSize);
            mBadgeTextRect.right = mBadgeTextPaint.measureText(mBadgeText);
            mBadgeTextFontMetrics = mBadgeTextPaint.getFontMetrics();
            mBadgeTextRect.bottom = mBadgeTextFontMetrics.descent - mBadgeTextFontMetrics.ascent;
        }
    }

    private void findBadgeCenter() {
        int mWidth = iBadge.getWidth();
        int mHeight = iBadge.getHeight();
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
            mColorBackground = typedArray.getColor(attr, mColorBackground);
        } else if (attr == R.styleable.badge_view_badge_background) {
            int badgeBackSource = typedArray.getResourceId(attr, -1);
            if (badgeBackSource != -1) {
                mDrawableBackground = context.getResources().getDrawable(badgeBackSource);
            }
        } else if (attr == R.styleable.badge_view_badge_text_color) {
            mColorBadgeText = typedArray.getColor(attr, mColorBadgeText);
        } else if (attr == R.styleable.badge_view_badge_text_size) {
            mBadgeTextSize = typedArray.getDimensionPixelSize(attr, (int) mBadgeTextSize);
        } else if (attr == R.styleable.badge_view_badge_horizontal_padding) {
            mHorizontalBadgePadding = typedArray.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.badge_view_badge_vertical_padding) {
            mVerticalBadgePadding = typedArray.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.badge_view_badge_horizontal_margin) {
            mGravityOffsetX = typedArray.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.badge_view_badge_vertical_margin) {
            mGravityOffsetY = typedArray.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.badge_view_badge_gravity) {
            mBadgeGravity = typedArray.getInt(attr, mBadgeGravity);
        } else if (attr == R.styleable.badge_view_badge_text) {
            mBadgeText = typedArray.getString(attr);
            measureText();
        } else if (attr == R.styleable.badge_view_badge_red_dot_radio) {
            mRedDotRadios = typedArray.getDimensionPixelSize(attr, mRedDotRadios);
        } else if (attr == R.styleable.badge_view_badge_auto_update_attach_view_size) {
            autoUpdateAttachViewSize = typedArray.getBoolean(attr, false);
        } else if (attr == R.styleable.badge_view_badge_view_id) {
            badgeViewGroupId = typedArray.getString(attr);
        }
    }

    private void resetPaints() {
        mBadgeBackgroundPaint.setColor(mColorBackground);
        mBadgeTextPaint.setColor(mColorBadgeText);
        mBadgeTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float getBadgeCircleRadius() {
        if (TextUtils.isEmpty(mBadgeText)) {
            return mRedDotRadios;
        } else if (mBadgeText.length() == 1) {
            return mBadgeTextRect.height() > mBadgeTextRect.width() ?
                    mBadgeTextRect.height() / 2f + mVerticalBadgePadding * 0.5f :
                    mBadgeTextRect.width() / 2f + mHorizontalBadgePadding * 0.5f;
        } else {
            return mBadgeBackgroundRect.height() / 2f;
        }
    }

    private void updateBadgeRooterView() {
        Log.d(TAG + "updateBadgeRooterView",
                "width:" + iBadge.getWidth() + ",  height:" + iBadge.getHeight());
        if (autoUpdateAttachViewSize && iBadge.getWidth() == 0 || iBadge.getHeight() == 0) {

            ViewGroup.LayoutParams layoutParams = iBadge.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = getBadgeWidth();
                layoutParams.height = getBadgeHeight();
            }
        }
    }

    @Override
    public int getBadgeWidth() {
        float radios = getBadgeCircleRadius();
        return (int) (mHorizontalBadgePadding * 2 + mGravityOffsetX * 2 + Math.max(Math.max(mBadgeTextRect.width(), mBadgeTextRect.height()),radios));
    }

    @Override
    public int getBadgeHeight() {
        float radios = getBadgeCircleRadius();
        return (int) (mVerticalBadgePadding * 2 + mGravityOffsetY * 2 + Math.max(mBadgeTextRect.height(), radios));
    }

    public void onDraw(@NonNull Canvas canvas) {
        if (!iBadge.isEnabled()) return;
        String newItemIndicatorId = iBadge.getNewItemIndicatorId();
        Log.d("onDraw-indicatorId:",newItemIndicatorId);
//        if (!NewItemIndicatorManager.getInstance().shouldShowBadgeById(newItemIndicatorId)) return;

        resetPaints();
        findBadgeCenter();
        drawBadge(canvas, mBadgeCenter, getBadgeCircleRadius());
        updateBadgeRooterView();
    }

    @Override
    public IEdgeBadgeController setAutoUpdateAttachViewSize(boolean autoUpdate) {
        this.autoUpdateAttachViewSize = autoUpdate;
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public String getBadgeGroupId() {
        return badgeViewGroupId == null ? "" : badgeViewGroupId;
    }

    @Override
    public IEdgeBadgeController setBadgeNumber(int badgeNum) {
        mBadgeNumber = badgeNum;
        if (mBadgeNumber < 0) {
            mBadgeText = "";
        }else if (mBadgeNumber > 0 && mBadgeNumber <= 99) {
            mBadgeText = String.valueOf(mBadgeNumber);
        } else if (mBadgeNumber == 0) {
            mBadgeText = null;
        }
        measureText();
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeText(String badgeText) {
        mBadgeText = badgeText;
        mBadgeNumber = 1;
        measureText();
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeTextColor(int color) {
        mColorBadgeText = color;
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeTextSize(float size, boolean isSpValue) {
        mBadgeTextSize = isSpValue ? DisplayUtil.dp2px(iBadge.getContext(), size) : size;
        measureText();
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeBackgroundColor(int color) {
        mColorBackground = color;
        if (mColorBackground == Color.TRANSPARENT) {
            mBadgeTextPaint.setXfermode(null);
        } else {
            mBadgeTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeBackground(Drawable drawable) {
        mDrawableBackground = drawable;
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgePadding(float padding, boolean isDpValue) {
        float badgePadding = isDpValue ? DisplayUtil.dp2px(iBadge.getContext(), padding) : padding;
        mHorizontalBadgePadding = badgePadding;
        mVerticalBadgePadding = badgePadding;
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setBadgeGravity(@EdgeBadgeGravity int gravity) {
        mBadgeGravity = gravity;
        iBadge.postInvalidate();
        return this;
    }

    @Override
    public IEdgeBadgeController setGravityOffset(float offsetX, float offsetY, boolean isDpValue) {
        return setGravityOffset(offsetX, offsetY, isDpValue);
    }

    @Override
    public void badgeInvalidate() {
        iBadge.postInvalidate();
    }
}

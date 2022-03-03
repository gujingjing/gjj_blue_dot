package com.example.myapplication.hight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class UserHintView extends View {

    private Context mContext;

    //需要绘制高亮的view
    private View mHeightLightView;
    private int[] location;
    private int viewWidth = 0;
    private int viewHeight = 0;

    //绘制高亮类型
    private DrawHeightLightType mHeightLightType = DrawHeightLightType.RECT;

    public enum DrawHeightLightType {
        RECT, CIRCLE, ROUND_RECT, CUSTOM_ROUND_RECT
    }

    /**
     * 矩形/圆角矩形 高光扩大范围
     */
    private int mHeightLightExpandSize = 0;

    /**
     * 圆角
     */
    private int mLeftTopRound = 0;
    private int mRightTopRound = 0;
    private int mLeftBottomRound = 0;
    private int mRightBottomRound = 0;

    /**
     * 引导箭头图片
     */
    private Bitmap mArrow;

    /**
     * 设置要绘制的方向
     */
    private DrawHintDirection mDirection = DrawHintDirection.RIGHT;

    public enum DrawHintDirection {
        TOP, BOTTOM, LEFT, RIGHT
    }

    /**
     * 箭头图片绘制宽高
     */
    private int mArrowWidth;
    private int mArrowHeight;
    /**
     * 箭头图片偏移量
     */
    private int mArrowOffsetX = 0;
    private int mArrowOffsetY = 0;

    //绘制文字内容
    private String mHintText;
    /**
     * 文字内容偏移量
     */
    private int mHintTextOffsetX = 0;
    private int mHintTextOffsetY = 0;
    private TextPaint mPaintText = new TextPaint();
    private int mTextColor = Color.parseColor("#FFFFFF");
    private int mTextSize = 30;

    //绘制背景颜色
    private int mBackgroundColor = Color.parseColor("#50000000");

    //画笔
    private Paint mPaint = new Paint();
    private PorterDuffXfermode mPdf = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private PorterDuffXfermode mPdfDstOver = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);

    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    //图片着色器
    private BitmapShader shader;
    private Matrix matrix = new Matrix();

    public UserHintView(Context context) {
        this(context, null);
    }

    public UserHintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserHintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        location = new int[]{0, 0};
    }

    /**
     * 设置绘制高亮的view
     *
     * @param view
     * @return
     */
    public UserHintView setHeightLightView(View view) {
        mHeightLightView = view;
        location = new int[]{0, 0};
        if (mHeightLightView != null) {
            mHeightLightView.getLocationOnScreen(location);
            viewWidth = mHeightLightView.getMeasuredWidth();
            viewHeight = mHeightLightView.getMeasuredHeight();
        }
        location[1] = location[1] - getStatusBarHeight();
        return this;
    }

    /**
     * 设置绘制高亮的类型
     *
     * @param type 矩形 圆形 圆角矩形 自定义圆角矩形
     * @return
     */
    public UserHintView setHeightLightType(DrawHeightLightType type) {
        mHeightLightType = type;
        return this;
    }

    /**
     * 设置矩形/圆角矩形 高光扩大范围
     *
     * @param size
     * @return
     */
    public UserHintView setHeightLightExpendSize(int size) {
        mHeightLightExpandSize = dp2px(size);
        return this;
    }

    /**
     * 设置引导箭头图片
     *
     * @param bitmap
     * @return
     */
    public UserHintView setArrowBitmap(Bitmap bitmap) {
        if (bitmap == null) return this;
        mArrow = bitmap;
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        return this;
    }

    /**
     * 设置引导箭头图片大小
     *
     * @param width
     * @param height
     * @return
     */
    public UserHintView setArrowSize(int width, int height) {
        mArrowWidth = dp2px(width);
        mArrowHeight = dp2px(height);
        return this;
    }

    /**
     * 设置引导箭头图片 X Y轴 位置偏移量
     *
     * @param x
     * @param y
     * @return
     */
    public UserHintView setArrowOffsetXY(int x, int y) {
        mArrowOffsetX = dp2px(x);
        mArrowOffsetY = dp2px(y);
        return this;
    }

    /**
     * 设置绘制提示箭头与文字的位置
     *
     * @param direction
     * @return
     */
    public UserHintView setDrawHintDirection(DrawHintDirection direction) {
        mDirection = direction;
        return this;
    }

    /**
     * 设置绘制的提示文字
     *
     * @param hintText
     * @return
     */
    public UserHintView setHintText(String hintText) {
        mHintText = hintText;
        return this;
    }

    /**
     * 设置绘制提示文字的偏移量
     *
     * @param x
     * @param y
     * @return
     */
    public UserHintView setHintTextOffsetXY(int x, int y) {
        mHintTextOffsetX = dp2px(x);
        mHintTextOffsetY = dp2px(y);
        return this;
    }

    /**
     * 绘制提示文字的字号
     *
     * @param textSize
     * @return
     */
    public UserHintView setHintTextSize(int textSize) {
        mTextSize = dp2px(textSize);
        return this;
    }

    /**
     * 绘制提示文字的颜色
     *
     * @param color
     * @return
     */
    public UserHintView setHintTextColor(int color) {
        mTextColor = color;
        return this;
    }

    /**
     * 设置圆角
     *
     * @param rectRound
     * @return
     */
    public UserHintView setRectRound(int rectRound) {
        return setRectRoundCustom(rectRound, rectRound, rectRound, rectRound);
    }

    /**
     * 设置自定义圆角
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     * @return
     */
    public UserHintView setRectRoundCustom(int leftTop, int rightTop, int leftBottom, int rightBottom) {
        mLeftTopRound = dp2px(leftTop);
        mRightTopRound = dp2px(rightTop);
        mLeftBottomRound = dp2px(leftBottom);
        mRightBottomRound = dp2px(rightBottom);
        return this;
    }

    public UserHintView setHintBackgroundColor(int color) {
        mBackgroundColor = color;
        return this;
    }

    /**
     * 开始绘制
     */
    public void create() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (viewHeight == 0 || viewWidth == 0) return;
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawColor(mBackgroundColor);
        mPaint.setXfermode(mPdf);
        initHeightLightDraw(canvas);
        initArrowBitmapDraw(canvas);
        initHintTextDraw(canvas);
    }

    /**
     * 绘制高光提示
     *
     * @param canvas
     */
    private void initHeightLightDraw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        //抗锯齿
        mPaint.setAntiAlias(true);

        RectF rectF = new RectF();
        rectF.left = location[0] - mHeightLightExpandSize;
        rectF.top = location[1] - mHeightLightExpandSize;
        rectF.right = location[0] + viewWidth + mHeightLightExpandSize;
        rectF.bottom = location[1] + viewHeight + mHeightLightExpandSize;

        switch (mHeightLightType) {
            case RECT:
                canvas.drawRect(rectF, mPaint);
                break;
            case CIRCLE:
                canvas.drawCircle(location[0] + viewWidth / 2f, location[1] + viewHeight / 2f,
                        viewWidth > viewHeight ? viewWidth / 2f + mHeightLightExpandSize : viewHeight / 2f + mHeightLightExpandSize, mPaint);
                break;
            case ROUND_RECT: {
                canvas.drawRoundRect(rectF, mLeftTopRound, mRightBottomRound, mPaint);
                break;
            }
            case CUSTOM_ROUND_RECT:
                initCustomRoundRect(canvas, location[0], location[1], viewWidth, viewHeight);
                break;
        }
    }

    /**
     * 绘制中部十字形，再绘制四个角
     *
     * @param canvas
     * @param x
     * @param y
     * @param viewWidth
     * @param viewHeight
     */
    private void initCustomRoundRect(Canvas canvas, int x, int y, int viewWidth, int viewHeight) {
        if (mLeftTopRound != 0) {
            canvas.drawCircle(x + mLeftTopRound, y + mLeftTopRound, mLeftTopRound, mPaint);
        } else {
            canvas.drawRect(x, y, x + mRightTopRound, y + mRightTopRound, mPaint);
        }
        if (mRightTopRound != 0) {
            canvas.drawCircle(x + viewWidth - mRightTopRound, y + mRightTopRound, mRightTopRound, mPaint);
        } else {
            canvas.drawRect(x + viewWidth - mLeftTopRound, y, x + viewWidth, y + mLeftTopRound, mPaint);
        }
        if (mLeftBottomRound != 0) {
            canvas.drawCircle(x + mLeftBottomRound, y + viewHeight - mLeftBottomRound, mLeftBottomRound, mPaint);
        } else {
            canvas.drawRect(x, y + viewHeight - mRightBottomRound, x + mRightBottomRound, y + viewHeight, mPaint);
        }
        if (mRightBottomRound != 0) {
            canvas.drawCircle(x + viewWidth - mRightBottomRound, y + viewHeight - mRightBottomRound, mRightBottomRound, mPaint);
        } else {
            canvas.drawRect(x + viewWidth - mLeftBottomRound, y + viewHeight - mLeftBottomRound, x + viewWidth, y + viewHeight, mPaint);
        }
        int horLeftRound = Math.max(mLeftTopRound, mLeftBottomRound);
        int horRightRound = Math.max(mRightTopRound, mRightBottomRound);
        //中部矩形 - 纵向
        canvas.drawRect(x + horLeftRound, y, x + viewWidth - horRightRound, y + viewHeight, mPaint);
        //中部矩形 - 横向
        int verLeftRound = Math.max(mLeftTopRound, mRightTopRound);
        int verRightRound = Math.max(mLeftBottomRound, mRightBottomRound);
        canvas.drawRect(x, y + verLeftRound, x + viewWidth, y + viewHeight - verRightRound, mPaint);
    }

    /**
     * 绘制箭头图片
     *
     * @param canvas
     */
    private void initArrowBitmapDraw(Canvas canvas) {
        //抗锯齿
        mPaintBitmap.setAntiAlias(true);

        if (mArrow != null) {
            if (mArrowWidth == 0 && mArrowHeight == 0) {
                mArrowWidth = dp2px(150);
                mArrowHeight = dp2px(150);
            }
            // 计算缩放比例
            float scaleWidth = ((float) mArrowWidth) / mArrow.getWidth();
            float scaleHeight = ((float) mArrowHeight) / mArrow.getHeight();
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            mArrow = Bitmap.createBitmap(mArrow, 0, 0, mArrow.getWidth(), mArrow.getHeight(), matrix, true);

            int left = 0, top = 0;
            //Rect rect = new Rect();
            switch (mDirection) {
                case LEFT: {
                    left = location[0] - mArrowWidth;
                    top = location[1];
                    /*rect.left = location[0] - mArrowWidth;
                    rect.top = location[1];
                    rect.right = location[0];
                    rect.bottom = location[1] + mArrowHeight;*/
                    break;
                }
                case RIGHT: {
                    left = location[0] + viewWidth;
                    top = location[1];
                    break;
                }
                case TOP: {
                    left = location[0];
                    top = location[1] - mArrowHeight;
                    break;
                }
                case BOTTOM: {
                    left = location[0];
                    top = location[1] + viewHeight;
                    break;
                }
            }
            canvas.drawBitmap(mArrow, left + mArrowOffsetX, top + mArrowOffsetY, mPaintBitmap);
        }
    }

    /**
     * 绘制提示文字
     *
     * @param canvas
     */
    private void initHintTextDraw(Canvas canvas) {
        if (TextUtils.isEmpty(mHintText)) return;
        int x = 0, y = 0;
        switch (mDirection) {
            case LEFT: {
                x = location[0] - mArrowWidth + mArrowOffsetX + mHintTextOffsetX;
                y = location[1] + mArrowHeight + mArrowOffsetY + mHintTextOffsetY;
                break;
            }
            case RIGHT: {
                x = location[0] + viewWidth + mArrowWidth + mArrowOffsetX + mHintTextOffsetX;
                y = location[1] + mArrowHeight + mArrowOffsetY + mHintTextOffsetY;
                break;
            }
            case TOP: {
                x = location[0] + mArrowWidth + mArrowOffsetX + mHintTextOffsetX;
                y = location[1] - mArrowHeight + mArrowOffsetY + mHintTextOffsetY;
                break;
            }
            case BOTTOM: {
                x = location[0] + mArrowWidth + mArrowOffsetX + mHintTextOffsetX;
                y = location[1] + viewHeight + mArrowHeight + mArrowOffsetY + mHintTextOffsetY;
                break;
            }
        }
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(mTextColor);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextSize(mTextSize);
        canvas.translate(x, y);
        StaticLayout myStaticLayout = new StaticLayout(mHintText, mPaintText, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
        //canvas.drawText(mHintText, , y, mPaintText);
        canvas.translate(-x, -y);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    private int getWindowWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    private int getWindowHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}

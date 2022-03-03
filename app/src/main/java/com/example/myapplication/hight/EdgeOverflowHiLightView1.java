package com.example.myapplication.hight;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

public class EdgeOverflowHiLightView1 extends RelativeLayout {

    private final String TAG = "EdgeOverflowHiLightView";
    private TextPaint mTextPaint;
    private Paint mBitmapPaint;
    private RectF mTargetRect;
    private float mTargetWidth;
    private float mTargetHeight;
    private int mLayerCount;
    private Xfermode mXfermode;
    private int mRadio;
    private int mTextWidth;
    private int mBackgroundColor;
    private float mPreTextLineHeight;

    private Paint mPaint;

    public EdgeOverflowHiLightView1(Context context) {
        this(context, null);
    }

    public EdgeOverflowHiLightView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeOverflowHiLightView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setWillNotDraw(false);
    }

    public void init() {
        mTargetRect = new RectF();

        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.hight_text_size));
        mTextPaint.setColor(getResources().getColor(R.color.hight_light_color));
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mTextPaint.setTypeface(font);
        mTextPaint.setAntiAlias(true);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setAntiAlias(true);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mPreTextLineHeight = fontMetrics.descent - fontMetrics.ascent;
        mRadio = getContext().getResources().getDimensionPixelSize(R.dimen.hight_padding) * 2;
        mTextWidth = getResources().getDimensionPixelOffset(R.dimen.hight_text_width);
        mBackgroundColor = getResources().getColor(R.color.hight_light_background);
    }

    public void setTargetRect(Rect rect) {
        mTargetRect.set(rect);
        mTargetWidth = mTargetRect.right - mTargetRect.left;
        mTargetHeight = mTargetRect.bottom - mTargetRect.top;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw mTargetWidth:" + mTargetWidth + ",mTargetHeight:" + mTargetHeight);
        if (mTargetWidth == 0 || mTargetHeight == 0)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        } else {
            mLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        }

        mPaint.setXfermode(null);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        Log.e(TAG, "onDraw mTargetRect-left;" + mTargetRect.left + ",top:" + mTargetRect.top +
                ",rrigt;" + mTargetRect.right + ",bottom" + mTargetRect.bottom);

        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(mXfermode);
        canvas.drawRoundRect(mTargetRect, mRadio, mRadio, mPaint);
        drawTips1(canvas);
        drawTips2(canvas);
        canvas.restoreToCount(mLayerCount);
    }

    private void drawTips1(Canvas canvas) {
        Bitmap bitmap = getBitmap(getContext(), R.drawable.right_arrow);
        int bitmapHeight = bitmap.getHeight();
        int imagePadding = getResources().getDimensionPixelOffset(R.dimen.hight_image_padding);
        int imageMargin = getResources().getDimensionPixelOffset(R.dimen.hight_image_margin);
        int textPadding = getResources().getDimensionPixelOffset(R.dimen.hight_text_image_padding);
        float bitmapLeftPosition = mTargetRect.right - imagePadding - mRadio;
        float bitmapTopPosition = mTargetRect.top - bitmapHeight - imageMargin;

        canvas.drawBitmap(bitmap, bitmapLeftPosition, bitmapTopPosition, mBitmapPaint);
        // draw text
        String text = "Download page hasbeen moved here.";
        float diffY = (mTextPaint.measureText(text) / mTextWidth) * mPreTextLineHeight;
        canvas.save();
        canvas.translate(getSafeDrawPosition(bitmapLeftPosition - mTextWidth / 2),
                getSafeDrawPosition(bitmapTopPosition - textPadding - diffY));
        StaticLayout myStaticLayout = new StaticLayout(text, mTextPaint, mTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
        canvas.restore();
    }

    private void drawTips2(Canvas canvas) {
        Bitmap bitmap = getBitmap(getContext(), R.drawable.left_arrow);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int imagePadding = getResources().getDimensionPixelOffset(R.dimen.hight_image_padding);
        int imageMargin = getResources().getDimensionPixelOffset(R.dimen.hight_image_margin);
        int textPadding = getResources().getDimensionPixelOffset(R.dimen.hight_text_image_padding);
        float bitmapLeftPosition = mTargetRect.left - bitmapWidth - imageMargin;
        float bitmapTopPosition = mTargetRect.top - bitmapHeight + imagePadding + mRadio;
        canvas.drawBitmap(bitmap, bitmapLeftPosition, bitmapTopPosition, mBitmapPaint);
        // draw text
        String text = "Add to collection hasbeen moved here.";
        float diffY = (mTextPaint.measureText(text) / mTextWidth) * mPreTextLineHeight;
        canvas.save();
        canvas.translate(getSafeDrawPosition(bitmapLeftPosition - mTextWidth / 2),
                getSafeDrawPosition(bitmapTopPosition - textPadding - diffY));
        StaticLayout myStaticLayout = new StaticLayout(text, mTextPaint, mTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);
        canvas.restore();
    }

    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    public void show(Activity activity) {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        rootView.addView(this, lp);
    }

    private float getTextDrawHeight(String text) {
        return (mTextPaint.measureText(text) / mTextWidth) * mPreTextLineHeight;
    }

    private float getSafeDrawPosition(float position) {
        if (position < 0) {
            return 0;
        } else if (position > getMeasuredWidth()) {
            return getMeasuredWidth();
        } else {
            return position;
        }
    }
}

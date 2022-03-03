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
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

public class EdgeOverflowHiLightView1 extends RelativeLayout {

    private final String TAG = "EdgeOverflowHiLightView";
    private Paint mTextPaint;
    private Paint mBitmapPaint;
    private RectF mTargetRect;
    private float mTargetWidth;
    private float mTargetHeight;
    private int mLayerCount;
    private Xfermode mXfermode;

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
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(20);
        mTextPaint.setAntiAlias(true);
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setAntiAlias(true);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        } else {
            mLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        }

        mPaint.setXfermode(null);
        mPaint.setColor(Color.parseColor("#90000000"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        Log.e(TAG, "onDraw mTargetRect-left;" + mTargetRect.left + ",top:" + mTargetRect.top +
                ",rrigt;" + mTargetRect.right + ",bottom" + mTargetRect.bottom);
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(mXfermode);
        canvas.drawRoundRect(mTargetRect, 0, 0, mPaint);

        drawTips1(canvas);

        canvas.restoreToCount(mLayerCount);
    }

    private void drawTips1(Canvas canvas) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.left_arrow);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap = getBitmap(getContext(), R.drawable.right_arrow);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int padding = 20;
        canvas.drawBitmap(bitmap, mTargetRect.right - padding, mTargetRect.top - height, mBitmapPaint);
        // draw text
        String text = "test text";
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, mTargetRect.right - padding - textWidth / 2, mTargetRect.top - height, mTextPaint);
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
}

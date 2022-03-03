package com.example.myapplication.hight;

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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

public class EdgeOverflowHiLightView extends RelativeLayout {

    private final String TAG = "EdgeOverflowHiLightView";
    private Paint mTextPaint;
    private Paint mEraserPaint;
    private Paint mBitmapPaint;
    private RectF mTargetRect;
    private Canvas mEraserCanvas;
    private Bitmap mEraserBitmap;
    private float mTargetWidth;
    private float mTargetHeight;

    public EdgeOverflowHiLightView(Context context) {
        this(context, null);
    }

    public EdgeOverflowHiLightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdgeOverflowHiLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setWillNotDraw(false);
    }

    public void init() {
        mTargetRect = new RectF();
        mEraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEraserPaint.setColor(Color.WHITE);
        mEraserPaint.setAntiAlias(true);
//        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

//        mEraserBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        mEraserCanvas = new Canvas(mEraserBitmap);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(20);
        mTextPaint.setAntiAlias(true);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setAntiAlias(true);
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
        intEraserBitmapIfNeeded();
        Log.e(TAG, "onDraw mEraserBitmap width:" + mEraserBitmap.getWidth() + ",height:" + mEraserBitmap.getHeight());
        mEraserBitmap.eraseColor(Color.TRANSPARENT);
        mEraserCanvas.drawColor(Color.parseColor("#50000000"));

//        drawHiLight(mEraserCanvas);
        mEraserCanvas.drawRoundRect(mTargetRect, 0, 0, mEraserPaint);
        drawTips1(mEraserCanvas);
        canvas.drawBitmap(mEraserBitmap, 0, 0, null);
    }

    private void intEraserBitmapIfNeeded() {
        if (mEraserCanvas == null) {
            mEraserBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            mEraserCanvas = new Canvas(mEraserBitmap);
        }
    }

    private void drawHiLight(Canvas canvas) {
        canvas.drawRoundRect(mTargetRect, 0, 0, mEraserPaint);
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

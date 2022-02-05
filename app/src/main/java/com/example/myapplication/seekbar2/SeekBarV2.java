package com.example.myapplication.seekbar2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.example.myapplication.R;

public class SeekBarV2 extends AppCompatSeekBar {
    private final int MAX_PROGRESS = 100;
    private int mSection;
    private int mTickRadius;
    private int mTickSectionColor;
    private TickSlideListener mTickSlideListener;

    public SeekBarV2(Context context) {
        this(context, null);
    }

    public SeekBarV2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(attrs);
        setThumbAndProgressColor();
        setMax(MAX_PROGRESS * mSection);
        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mTickSlideListener != null) {
                    mTickSlideListener.onSliding(getSection(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int section = getSection(getProgress());
                setProgress(section * MAX_PROGRESS);
                if (mTickSlideListener != null) {
                    mTickSlideListener.onStopSlide(section);
                }
            }
        });
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.EdgeTickSlide);
        mSection = t.getInt(R.styleable.EdgeTickSlide_edgeTickCount, 4);
        mTickRadius = t.getDimensionPixelSize(R.styleable.EdgeTickSlide_edgeTickRadio, 4);
        mTickSectionColor = t.getColor(R.styleable.EdgeTickSlide_edgeTickSectionColor, Color.TRANSPARENT);
        t.recycle();
    }

    private void setThumbAndProgressColor() {
        try {
            Drawable drawable = getProgressDrawable();
            if (drawable == null) return;
            Drawable progressDrawable = drawable.mutate();
            if (progressDrawable instanceof LayerDrawable) {
                progressDrawable = new SeekBarBackgroundDrawable(((LayerDrawable) progressDrawable).getDrawable(0), mSection);
            }
            if (progressDrawable instanceof SeekBarBackgroundDrawable) {
                SeekBarBackgroundDrawable seekDrawable = (SeekBarBackgroundDrawable) progressDrawable;
                seekDrawable.mPaintColor = mTickSectionColor;
            }
            setProgressDrawable(progressDrawable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getSection(int progress) {
        int section = progress / MAX_PROGRESS;
        int mod = progress % MAX_PROGRESS;
        if (mod >= MAX_PROGRESS / 2) {
            section++;
        }
        return section;
    }

    public void setTickSlideListener(TickSlideListener tickSlideListener) {
        mTickSlideListener = tickSlideListener;
    }

    @SuppressLint("RestrictedApi")
    private class SeekBarBackgroundDrawable extends DrawableWrapper {

        private int mPaintColor;
        private float[] mBaseX;
        private Paint mPaint;
        private int mSection;

        public SeekBarBackgroundDrawable(Drawable drawable, int section) {
            super(drawable);
            mSection = section;
            mBaseX = new float[section + 1];
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            getWrappedDrawable().draw(canvas);
            drawTick(canvas);
        }

        private void drawTick(Canvas canvas) {
            Rect bounds = getBounds();
            float tickBaseY = (bounds.top + bounds.bottom) / 2;
            int drawWidth = bounds.width();
            mPaint.setColor(mPaintColor);
            int sectionWidth = drawWidth / mSection;
            for (int i = 0; i < mBaseX.length; i++) {
                mBaseX[i] = i * sectionWidth;
                int width = 5, heigt = 14;
                canvas.drawRect(mBaseX[i] - width, tickBaseY + heigt, mBaseX[i] + width, tickBaseY - heigt, mPaint);
//                canvas.drawCircle(mBaseX[i], tickBaseY, mTickRadius - 1f, mPaint);
            }
        }
    }
}

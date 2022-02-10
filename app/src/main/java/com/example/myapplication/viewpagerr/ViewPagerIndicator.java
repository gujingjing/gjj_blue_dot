package com.example.myapplication.viewpagerr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerIndicator extends LinearLayout {

    private int mPageCount;
    private int mSelectedIndex;
    private int mIndicatorMargin;
    private int mIndicatorDefaultColor;
    private int mIndicatorSelectedColor;

    private final List<View> mIndexViews = new ArrayList<>();
    private ViewPager.OnPageChangeListener mListener;
    private IndicatorViewAdapter mViewAdapter;

    public ViewPagerIndicator(@NonNull final Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EdgeViewPagerIndicator, 0, 0);
        try {
            mIndicatorMargin = attributes.getDimensionPixelSize(R.styleable.EdgeViewPagerIndicator_indicatorMargin, 0);
            mIndicatorDefaultColor = attributes.getColor(R.styleable.EdgeViewPagerIndicator_indicatorDefaultColor, Color.TRANSPARENT);
            mIndicatorSelectedColor = attributes.getColor(R.styleable.EdgeViewPagerIndicator_indicatorSelectedColor, Color.TRANSPARENT);
            int itemLayoutId = attributes.getResourceId(R.styleable.EdgeViewPagerIndicator_indicatorItemLayoutId, 0);
            if (itemLayoutId != 0) {
                mViewAdapter = new DefaultViewAdapter(getContext(), itemLayoutId, mIndicatorDefaultColor, mIndicatorSelectedColor);
            }
        } finally {
            attributes.recycle();
        }
    }

    public void bindViewPager(@NonNull final ViewPager viewPager) {
        setPageCount(viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new OnPageChangeListener());
    }

    public void addOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    public void setSelectedIndex(final int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex > mPageCount - 1) {
            return;
        }
        if (mViewAdapter != null) {
            final View willUnSelectedView = mIndexViews.get(mSelectedIndex);
            mViewAdapter.onItemUnSelected(willUnSelectedView, mSelectedIndex);

            final View willSelectedView = mIndexViews.get(selectedIndex);
            mViewAdapter.onItemSelected(willSelectedView, selectedIndex);
        }
        mSelectedIndex = selectedIndex;
    }

    public void setPageCount(final int pageCount) {
        mPageCount = pageCount;
        mSelectedIndex = 0;
        removeAllViews();
        mIndexViews.clear();

        if (mViewAdapter == null) return;

        for (int i = 0; i < pageCount; ++i) {
            View view = mViewAdapter.getView(this);
            mIndexViews.add(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            if (layoutParams instanceof MarginLayoutParams && i != 0) {
                ((MarginLayoutParams) layoutParams).leftMargin = mIndicatorMargin;
            }
            addView(view, layoutParams);
        }
        setSelectedIndex(mSelectedIndex);
    }

    protected class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            if (mListener != null) {
                mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            setSelectedIndex(position);
            if (mListener != null) {
                mListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            if (mListener != null) {
                mListener.onPageScrollStateChanged(state);
            }
        }
    }

    public void setIndicatorViewAdapter(IndicatorViewAdapter adapter) {
        mViewAdapter = adapter;
    }

    interface IndicatorViewAdapter {
        View getView(ViewGroup parent);

        void onItemSelected(View view, int position);

        void onItemUnSelected(View view, int position);
    }

    class DefaultViewAdapter implements IndicatorViewAdapter {
        private Context mContext;
        private int mLayoutId;
        private int mIndicatorDefaultColor;
        private int mIndicatorSelectedColor;

        public DefaultViewAdapter(Context context, int layoutId,
                                  int indicatorDefaultColor, int indicatorSelectedColor) {
            mContext = context;
            mLayoutId = layoutId;
            mIndicatorDefaultColor = indicatorDefaultColor;
            mIndicatorSelectedColor = indicatorSelectedColor;
        }

        @Override
        public View getView(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            view.setBackgroundColor(mIndicatorDefaultColor);
            return view;
        }

        @Override
        public void onItemSelected(View view, int position) {
            view.setBackgroundColor(mIndicatorSelectedColor);
        }

        @Override
        public void onItemUnSelected(View view, int position) {
            view.setBackgroundColor(mIndicatorDefaultColor);
        }
    }
}
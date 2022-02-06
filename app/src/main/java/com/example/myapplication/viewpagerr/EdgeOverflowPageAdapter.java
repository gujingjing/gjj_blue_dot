package com.example.myapplication.viewpagerr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class EdgeOverflowPageAdapter<T> extends PagerAdapter {
    private Context mContext;
    private final List<T> mTabItems;
    private final ArrayMap<T, EdgeOverflowPageItem> pageItems;

    public EdgeOverflowPageAdapter(Context context, List<T> list) {
        mContext = context;
        mTabItems = list;
        pageItems = new ArrayMap<>();
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // Return this can refresh current page , when viewpager data change
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        } else {
            super.destroyItem(container, position, object);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        T item = mTabItems.get(position);
        if (pageItems.get(item) == null) {
            EdgeOverflowPageItem pageItem = instantiateView(item, position);
            pageItems.put(item, pageItem);
        }
        View toAddView = pageItems.get(item).getView();
        if (container.indexOfChild(toAddView) != -1) {
            container.removeView(toAddView);
        }
        container.addView(toAddView);
        return toAddView;
    }

    abstract EdgeOverflowPageItem instantiateView(T item, int position);
}

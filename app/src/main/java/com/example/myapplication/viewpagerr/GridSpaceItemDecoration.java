package com.example.myapplication.viewpagerr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mColumnCount;
    private int hafPrePadding;
    private final String TAG = "GridSpaceItemDecoration";

    public GridSpaceItemDecoration(Context context, int mSpanCount, int maxWidth, int itemWidth) {
        this.mColumnCount = mSpanCount;
        maxWidth = getWidth(context);
        int preItemWidth = maxWidth / mColumnCount;
        int preSpan = preItemWidth - itemWidth;
        int maxSpan = preSpan * mColumnCount;
        if (maxSpan <= 0)
            hafPrePadding = 0;
        else
            hafPrePadding = maxSpan / ((mColumnCount + 1) * 2);
    }

    private int getWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width3 = dm.widthPixels;
        int height3 = dm.heightPixels;
        return width3;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view); // 获取view 在adapter中的位置。
        int column = position % mColumnCount; // view 所在的列

//        outRect.left = column * mColumnSpacing / mSpanCount; // column * (列间距 * (1f / 列数))
//        outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount; // 列间距 - (column + 1) * (列间距 * (1f /列数))

//        outRect.left = hafPrePadding;
//        outRect.right = hafPrePadding;

        if (column == 0) {
            outRect.left = 0;
            outRect.right = hafPrePadding;
        } else if (column == mColumnCount - 1) {
            outRect.left = hafPrePadding;
            outRect.right = 0;
        } else {
            outRect.left = hafPrePadding;
            outRect.right = hafPrePadding;
        }


        Log.e(TAG, "position:" + position
                + "    columnIndex: " + column
                + "    left,right ->" + outRect.left + "," + outRect.right);
    }

    public int getPadding() {
        return hafPrePadding * 2;
    }
}

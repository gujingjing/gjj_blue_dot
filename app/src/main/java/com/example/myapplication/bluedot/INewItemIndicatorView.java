package com.example.myapplication.bluedot;

import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;

import com.example.myapplication.R;

/**
 * Interface added on an {@link android.widget.ImageView} that support draw
 * new item indicator by conditions. Ref: {@link BadgeImageView} and {@link BadgeLottieView}
 *
 * @author zhagao
 */
public interface INewItemIndicatorView {

    int LAST_CELL_INDEX = 2;
    /**
     * use "id(:extraidentifier)" as new item indicator id
     * @return id
     */
    @Nullable
    String getNewItemIndicatorId();

    /**
     * The blue dot badge location, by default it shows at top right in LFT layout.
     * Varies implementation can override this method.
     * Default: {@link Corner#TOP_END}
     */
    default @Corner int showAtCorner() {
        return Corner.TOP_END;
    }
    /**
     * Mark positions in 3x3 cells, first byte indicate row, second byte indicate column,
     * due to RTL layout, so use START/END instead of LEFT/RIGHT.
     * No center value, so 0x22 which is the center cell is excluded.
     * <pre>
     *                TOP_CENTER                 TOP_CENTER
     *                     |                          |
     *    TOP_START●-------●-------● TOP_END  ●-------●-------●TOP_START
     *             |               |          |               |
     *             |               |          |               |
     * MIDDLE_START● LTR ImageView ●MIDDLE_END● RTL ImageView ●MIDDLE_START
     *             |               |          |               |
     *             |               |          |               |
     * BOTTOM_START●------●--------●BOTTOM_END●-------●-------●BOTTOM_START
     *                    |                          |
     *               BOTTOM_CENTER              BOTTOM_CENTER
     * </pre>
     */
    @interface Corner {
        int TOP_START = 0x00;
        int TOP_CENTER = 0x01;
        int TOP_END = 0x02;
        int CENTER_START = 0x10;
        int CENTER_END = 0x12;
        int BOTTOM_START = 0x20;
        int BOTTOM_CENTER = 0x21;
        int BOTTOM_END = 0x22;
    }

    default @DimenRes int indicatorDiameterRes() {
        return R.dimen.new_item_indicator_dot_diameter;
    }
}


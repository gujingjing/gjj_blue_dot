package com.example.myapplication.bluedot_v3;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark positions in 3x3 cells, first byte indicate row, second byte indicate column,
 * due to RTL layout, so use START/END instead of LEFT/RIGHT.
 * No center value, so 0x22 which is the center cell is excluded.
 * <pre>
 *                TOP_CENTER
 *                     |
 *    TOP_START●-------●-------● TOP_END
 *             |               |
 *             |               |
 * CENTER_START● LTR ImageView ●CENTER_END
 *             | CENTER_CENTER |
 *             |               |
 * BOTTOM_START●------●--------●BOTTOM_END
 *                    |
 *               BOTTOM_CENTER
 * </pre>
 */
@IntDef({EdgeBadgeGravity.TOP_START, EdgeBadgeGravity.TOP_CENTER, EdgeBadgeGravity.TOP_END,
        EdgeBadgeGravity.CENTER_START, EdgeBadgeGravity.CENTER_CENTER, EdgeBadgeGravity.CENTER_END,
        EdgeBadgeGravity.BOTTOM_START, EdgeBadgeGravity.BOTTOM_CENTER, EdgeBadgeGravity.BOTTOM_END})
@Retention(RetentionPolicy.SOURCE)
public @interface EdgeBadgeGravity {
    int TOP_START = 0x00;
    int TOP_CENTER = 0x01;
    int TOP_END = 0x02;
    int CENTER_START = 0x10;
    int CENTER_CENTER = 0x11;
    int CENTER_END = 0x12;
    int BOTTOM_START = 0x20;
    int BOTTOM_CENTER = 0x21;
    int BOTTOM_END = 0x22;
}

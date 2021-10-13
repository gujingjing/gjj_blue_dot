package com.example.myapplication.bluedot_4;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({EdgeBadgeViewType.TYPE_DOT, EdgeBadgeViewType.TYPE_TEXT})
@Retention(RetentionPolicy.SOURCE)
public @interface EdgeBadgeViewType {
    int TYPE_DOT = 0;
    int TYPE_NUMBER = 1;
    int TYPE_TEXT = 2;
}

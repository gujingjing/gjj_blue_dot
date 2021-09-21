package com.example.myapplication.bluedot_4;

import android.content.Context;

public interface IViewController {
    int getWidth();

    int getHeight();

    boolean isEnabled();

    void postInvalidate();

    void requestLayout();

    Context getContext();
}

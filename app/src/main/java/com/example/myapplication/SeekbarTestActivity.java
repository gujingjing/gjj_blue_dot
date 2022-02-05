package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;

import com.example.myapplication.seekbar2.SeekBarV2;
import com.example.myapplication.seekbar2.TickSlideListener;

public class SeekbarTestActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar_test1);
//        AppCompatSeekBar appCompatSeekBar = findViewById(R.id.appCompatSeekBar);
        SeekBarV2 seekBar = findViewById(R.id.seekBarV2);
        seekBar.setTickSlideListener(new TickSlideListener() {
            @Override
            public void onSliding(int section) {
                Log.e("seekbarr-", "onSliding");
            }

            @Override
            public void onStopSlide(int section) {
                Log.e("seekbarr-", "onStopSlide:section=" + section);
            }
        });
    }
}

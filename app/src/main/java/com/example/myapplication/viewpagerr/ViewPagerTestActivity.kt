package com.example.myapplication.viewpagerr

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R

class ViewPagerTestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_test)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val list = listOf<String>("0", "1", "2", "3", "4", "5", "6")
        viewPager.offscreenPageLimit = 0
        viewPager.adapter = object : EdgeOverflowPageAdapter<String>(this, list) {
            override fun instantiateView(item: String?, position: Int): EdgeOverflowPageItem {
                return if (position % 2 == 0) {
                    EdgeTestPageItem(this@ViewPagerTestActivity, item)
                } else {
                    EdgeListTestPageItem(
                        this@ViewPagerTestActivity,
                        arrayListOf(
                            "position:$position -1",
                            "position:$position -2",
                            "position:$position -3",
                            "position:$position -4",
                            "position:$position -5",
                            "position:$position -6",
                            "position:$position -7",
                            "position:$position -8",
                            "position:$position -9",
                        )
                    )
                }
            }
        }
    }
}
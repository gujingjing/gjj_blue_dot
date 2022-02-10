package com.example.myapplication.viewpagerr

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
        val viewPagerIndicator = findViewById<ViewPagerIndicator>(R.id.viewPagerIndicator)
        var mIndicatorDefaultColor = resources.getColor(R.color.new_item_indication_dot_color)
        var mIndicatorSelectedColor = resources.getColor(R.color.new_item_indication_dot_text_color)
//        viewPagerIndicator.setIndicatorViewAdapter(object :
//            ViewPagerIndicator.IndicatorViewAdapter {
//            override fun getView(): View {
////                val view: View =
////                    LayoutInflater.from(this@ViewPagerTestActivity)
////                        .inflate(R.layout.indicator_item, null, false)
//                var view = LinearLayout(this@ViewPagerTestActivity)
//                view.layoutParams = ViewGroup.LayoutParams(80, 40)
//                view.setBackgroundColor(mIndicatorDefaultColor)
//                return view
//            }
//
//            override fun onItemSelected(view: View?, position: Int) {
//                view?.setBackgroundColor(mIndicatorSelectedColor)
//            }
//
//            override fun onItemUnSelected(view: View?, position: Int) {
//                view?.setBackgroundColor(mIndicatorDefaultColor)
//            }
//
//        })
        viewPagerIndicator.bindViewPager(viewPager)
        viewPagerIndicator.setPageCount(list.size)
    }
}
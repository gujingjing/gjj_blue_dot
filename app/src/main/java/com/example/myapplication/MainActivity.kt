package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.myapplication.bluedot.NewItemIndicatorManager
import com.example.myapplication.bluedot_4.EdgeBadgeViewType
import com.example.myapplication.bluedot_4.view.EdgeBadgeTextView
import com.example.myapplication.bluedot_4.view.EdgeBadgeView
import com.example.myapplication.hh5test.H5TestActivity
import com.example.myapplication.swap.SwapTestActivity


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        findViewById<View>(R.id.btn_test_asset)?.apply {
            setOnClickListener {
//                NewItemIndicatorManager.getInstance().init()
                startActivity(Intent(this@MainActivity, H5TestActivity::class.java))
            }
        }
        findViewById<View>(R.id.btn_test_asset2)?.apply {
            setOnClickListener {
//                NewItemIndicatorManager.getInstance().init()
//                startActivity(Intent(this@MainActivity, SwapTestActivity::class.java))
                startActivity(Intent(this@MainActivity, SeekbarTestActivity::class.java))
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.badge_test_1)?.apply {
            setOnClickListener {
                this.setMargin(10, 10, true)
                        .setBadgePadding(4, true)
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.text_new_test)?.let { badgeView ->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("测")
                badgeView.setBadgeViewType(EdgeBadgeViewType.TYPE_TEXT);
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.text_number_test)?.let { badgeView ->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("99")
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.text_dot_test)?.let { badgeView ->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("-1")
            }
        }
        findViewById<EdgeBadgeView>(R.id.text_add_test)?.let { badgeView->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("NNN")
            }
        }
        findViewById<EdgeBadgeView>(R.id.number_add_test)?.let { badgeView->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("999")
            }
        }
        findViewById<EdgeBadgeView>(R.id.dot_to_number_test)?.let { badgeView->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("999")
            }
        }
    }
}
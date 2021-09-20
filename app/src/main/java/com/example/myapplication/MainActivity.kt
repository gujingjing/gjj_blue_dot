package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.myapplication.bluedot.NewItemIndicatorManager
import com.example.myapplication.bluedot_v3.view.EdgeBadgeView


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        findViewById<View>(R.id.btn_test_asset)?.apply {
            setOnClickListener {
                NewItemIndicatorManager.getInstance().init()
            }
        }
        findViewById<View>(R.id.badge_test_1)?.apply {
            setOnClickListener {

            }
        }
        findViewById<EdgeBadgeView>(R.id.text_add_test)?.let {badgeView->
            badgeView.setOnClickListener {
                badgeView.badgeController.setBadgeText("测试用的文字")
            }
        }
        findViewById<EdgeBadgeView>(R.id.number_add_test)?.let {badgeView->
            badgeView.setOnClickListener {
                badgeView.badgeController.setBadgeNumber(999)
            }
        }
        findViewById<EdgeBadgeView>(R.id.dot_to_number_test)?.let {badgeView->
            badgeView.setOnClickListener {
                badgeView.badgeController.setBadgeNumber(999)
            }
        }
    }
}
package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.myapplication.bluedot.NewItemIndicatorManager
import com.example.myapplication.bluedot_4.EdgeBadgeViewType
import com.example.myapplication.bluedot_4.view.EdgeBadgeTextView
import com.example.myapplication.bluedot_4.view.EdgeBadgeView


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
        findViewById<EdgeBadgeTextView>(R.id.text_new_test)?.let { badgeView->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("测")
                badgeView.setBadgeViewType(EdgeBadgeViewType.TYPE_TEXT);
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.text_number_test)?.let { badgeView->
            badgeView.setOnClickListener {
                badgeView.setBadgeText("99")
            }
        }
        findViewById<EdgeBadgeTextView>(R.id.text_dot_test)?.let { badgeView->
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
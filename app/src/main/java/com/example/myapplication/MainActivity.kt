package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.myapplication.bluedot.NewItemIndicatorManager


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
    }
}
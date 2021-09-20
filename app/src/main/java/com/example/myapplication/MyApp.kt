package com.example.myapplication

import android.app.Application
import com.example.myapplication.bluedot.ContextUtils
import com.example.myapplication.bluedot.NewItemIndicatorManager

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtils.initApplicationContext(this)
        NewItemIndicatorManager.getInstance().init()
    }
}
package com.example.myapplication.hh5test

import android.R.attr.button
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import com.example.myapplication.R


class H5TestActivity : Activity() {

    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_test)
        webView = findViewById(R.id.webview)

        initWebview()
    }

    fun initWebview() {
        val setting: WebSettings = webView?.settings ?: return
        //设置支持javascript
        setting.javaScriptEnabled = true
        //增加接口方法,让html页面调用
        webView?.addJavascriptInterface(object : Any() {
            //这里我定义了一个拨打的方法
            @JavascriptInterface
            fun toastTest(ss: String) {
                Toast.makeText(this@H5TestActivity, ss, Toast.LENGTH_SHORT).show()
            }
        }, "android")
        //加载页面
        webView?.loadUrl("file:///android_asset/test.html")
    }
}
package com.example.myapplication.swap

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import com.example.myapplication.R

class SwapTestActivity : Activity() {
    var webView: WebView? = null
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    //    lateinit var swipeRefreshHelper: SwipeRefreshHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_test)

        webView = findViewById(R.id.webview)
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout)
        initSwip()
        initWebview()
    }

    fun initSwip() {
        mSwipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            mSwipeRefreshLayout?.postDelayed({
                mSwipeRefreshLayout?.isRefreshing = false
            }, 2000)
        })
    }

    fun initWebview() {
        val setting: WebSettings = webView?.settings ?: return
        //设置支持javascript
        setting.javaScriptEnabled = true
        setting.domStorageEnabled = true
        setting.databaseEnabled = true
        setting.setAppCacheEnabled(true)
        setting.setJavaScriptEnabled(true); // 设置支持javascript脚本
        setting.setAllowFileAccess(true); // 允许访问文件
        setting.setUseWideViewPort(true);
        //增加接口方法,让html页面调用
        webView?.addJavascriptInterface(object : Any() {
            //这里我定义了一个拨打的方法
            @JavascriptInterface
            fun toastTest(ss: String) {
                Toast.makeText(this@SwapTestActivity, ss, Toast.LENGTH_SHORT).show()
            }
        }, "android")
        //加载页面
//        webView?.loadUrl("file:///android_asset/test.html")
        webView?.loadUrl("https://github.com/")
    }

}
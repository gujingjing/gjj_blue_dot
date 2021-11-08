package cn.iwgang.simplifyspan.debug

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.socialapp.chat.MainApplication
import com.strawberry.chat.BuildConfig

object DebugInitManager {

    fun debugInit(application: MainApplication) {
        Log.w("zzh", "SDKINIT is inDebug:${BuildConfig.DEBUG}")
        if (BuildConfig.DEBUG) {
            uiDebug(application)
        }
    }

    private fun uiDebug(application: MainApplication) {
        application.registerActivityLifecycleCallbacks(object : DebugActivityLifecycleCallback() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityCreated(activity, savedInstanceState)
                Log.i("Debug", ">>> Create activity $activity")
                activity.window.decorView.apply {
                    overlay?.add(
                            DebugTipsDrawable(
                                    activity.componentName.className,
                                    alignToEnd = true
                            )
                    )
                    invalidate()
                }
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager
                            .registerFragmentLifecycleCallbacks(
                                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
                                    object : FragmentManager.FragmentLifecycleCallbacks() {
                                        override fun onFragmentViewCreated(
                                                fm: FragmentManager,
                                                f: Fragment,
                                                v: View,
                                                savedInstanceState: Bundle?
                                        ) {
                                            v.overlay.add(
                                                    DebugTipsDrawable(
                                                            f.javaClass.simpleName,
                                                            textColor = Color.GREEN
                                                    )
                                            )
                                        }

                                        override fun onFragmentViewDestroyed(
                                                fm: FragmentManager,
                                                f: Fragment
                                        ) {
                                            f.view?.overlay?.clear()
                                        }
                                    }, true
                            )
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
                Log.i("Debug", ">>> destroy activity $activity")
                activity.window.decorView.overlay.clear()
            }
        })
    }
}
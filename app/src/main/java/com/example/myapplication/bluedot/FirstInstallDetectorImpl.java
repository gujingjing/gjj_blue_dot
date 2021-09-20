package com.example.myapplication.bluedot;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * class to detect if current install is first install on android
 *
 * First install:
 * firstInstallTime == lastUpdateTime
 *
 * Update install:
 * firstInstallTime != lastUpdateTime
 */
public class FirstInstallDetectorImpl implements NewItemIndicatorManager.FirstInstallDetector {
    private static final int NO_INSTALL_TIME = 0;

    private Boolean mIsFirstInstall = null;

    @Override
    public boolean isFirstInstall() {
        if (mIsFirstInstall == null) {
            Context context = ContextUtils.getApplicationContext();
            mIsFirstInstall = getPackageFirstInstallTime(context) == getPackageLastUpdateTime(context);
        }

        return mIsFirstInstall.booleanValue();
    }

    private long getPackageFirstInstallTime(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo == null ? NO_INSTALL_TIME : packageInfo.firstInstallTime;
    }

    private long getPackageLastUpdateTime(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo == null ? NO_INSTALL_TIME : packageInfo.lastUpdateTime;
    }
}

package com.example.myapplication.bluedot;

import java.util.Set;

/**
 * PersistStorage implementation that using shared preference
 */
public class PersistStorageImpl implements NewItemIndicatorManager.PersistStorage {
    @Override
    public void putStringSet(String key, Set<String> values) {
        ContextUtils.getAppSharedPreferences().edit().putStringSet(key, values).apply();
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return ContextUtils.getAppSharedPreferences().getStringSet(key, defValues);
    }

    @Override
    public void putInt(String key, int value) {
        ContextUtils.getAppSharedPreferences().edit().putInt(key, value).apply();
    }

    @Override
    public int getInt(String key, int defValue) {
        return ContextUtils.getAppSharedPreferences().getInt(key, defValue);
    }
}

package com.example.myapplication.bluedot;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * class that manage which UI should show new item badge
 *
 * TODO: add badge according to UI position
 */
public class NewItemIndicatorManager {
    private static final String TAG = "NewItemIndicatorManager";
    static final String LAST_VERSION_KEY = "new_item_indicator_last_version_key";
    static final String SHOWING_UI_KEY = "new_item_indicator_showing_ui_key";

    static final String OVERFLOW_BUTTON_TOP_ID = "id/overflow_button_top";
    static final String OVERFLOW_BUTTON_BOTTOM_ID = "id/overflow_button_bottom";

    private final PersistStorage mPersistStorage;
    private final ConfigReader mConfigReader;
    private final FirstInstallDetector mFirstInstallDetector;

    /**Set that store which UIs should show badge**/
    @VisibleForTesting
    @Nullable
    volatile Set<String> mUiIds;

    private static class InstanceHolder {
        private static NewItemIndicatorManager instance = new NewItemIndicatorManager(new PersistStorageImpl(),
                new ConfigReaderImpl(), new FirstInstallDetectorImpl());
    }

    static public NewItemIndicatorManager getInstance() {
        return InstanceHolder.instance;
    }

    @VisibleForTesting
    NewItemIndicatorManager(PersistStorage persistStorage, ConfigReader configReader, FirstInstallDetector firstInstallDetector) {
        mPersistStorage = persistStorage;
        mConfigReader = configReader;
        mFirstInstallDetector = firstInstallDetector;
    }

    /**
     * init when application started, can be called on worker thread
     *
     * compare current version with last updated version, if not equal, read current config
     * and store in shared preference, else read in previous config from shared preference
     */
    @WorkerThread
    public void init() {
        String config = mConfigReader.getConfig();

        config = getConfigWithOverlay(config, "");

        int curVersionCode = parseVersion(config);
        if (curVersionCode != mPersistStorage.getInt(LAST_VERSION_KEY, -1)) {
            // if version changes
            // clear old store
            mPersistStorage.putStringSet(SHOWING_UI_KEY, null);

            // read new config
            mUiIds = parseUiIds(config);

            // new store
            mPersistStorage.putStringSet(SHOWING_UI_KEY, mUiIds);
            mPersistStorage.putInt(LAST_VERSION_KEY, curVersionCode);
        } else {
            mUiIds = mPersistStorage.getStringSet(SHOWING_UI_KEY, new HashSet<>());
        }
        Log.e(TAG, "init:uid-size="+mUiIds.size());
    }

    String getConfigWithOverlay(String originConfig, String currentLocale) {
        try {
            JSONObject top = new JSONObject(originConfig);
            JSONObject overlay = top.optJSONObject("overlay");
            if (overlay != null) {
                Iterator<String> keys = overlay.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    //TODO use current locale
//                    if (key.equalsIgnoreCase(currentLocale)) {
                        return overlay.optString(key);
//                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getConfigWithOverlay", e);
        }
        return originConfig;
    }

    /**
     * parse version from config
     *
     * @param config
     * @return
     */
    @VisibleForTesting
    int parseVersion(@NonNull String config) {
        try {
            JSONObject jsonObject = new JSONObject(config);
            int version = jsonObject.getInt("version");
            return version;
        } catch (Exception e) {
            Log.e(TAG, "parseUiIds", e);
        }

        return -1;
    }

    /**
     * parse ui id array from config and return Java set
     *
     * @param config json array config
     * @return
     */
    @VisibleForTesting
    Set<String> parseUiIds(@NonNull String config) {
        Set<String> res = new HashSet<>();

        try {
            JSONObject jsonObject = new JSONObject(config);
            JSONArray array = jsonObject.getJSONArray("ids");
            for (int i = 0; i < array.length(); i++) {
                res.add(array.getString(i));
            }
        } catch (Exception e) {
            Log.e(TAG, "parseUiIds", e);
        }
        return res;
    }

    /**
     * check if UI with id should show badge
     * id usually is android id, some cases to distinguish UI with same id,
     * the id is composed of "android_id:extra_identifier"
     *
     * @param id
     * @return
     */
    public boolean shouldShowBadgeById(@NonNull String id) {
        if (mUiIds == null || mFirstInstallDetector.isFirstInstall()) return false;

        return mUiIds.contains("id/" + id);
    }

    /**
     * check if UI with key should show badge
     * key is used to identify preferences which do not have android id
     *
     * @param key
     * @return
     */
    public boolean shouldShowBadgeByKey(@NonNull String key) {
        if (mUiIds == null || mFirstInstallDetector.isFirstInstall()) return false;

        return mUiIds.contains("key/" + key);
    }

    /**
     * when UI with id has been clicked, notify NewItemIndicatorManager to remove this UI from
     * config
     *
     * @param id
     */
    public void onClickedById(@NonNull String id) {
        if (mUiIds == null || mFirstInstallDetector.isFirstInstall()) {
            return;
        }
        boolean res = mUiIds.remove("id/" + id);
        if (res) {
            mPersistStorage.putStringSet(SHOWING_UI_KEY, mUiIds);
        }
    }

    /**
     * when UI with key has been clicked, notify NewItemIndicatorManager to remove this UI from
     * config
     *
     * @param key
     */
    public void onClickedByKey(@NonNull String key) {
        if (mUiIds == null || mFirstInstallDetector.isFirstInstall()) return;

        boolean res = mUiIds.remove("key/" + key);
        if (res) {
            mPersistStorage.putStringSet(SHOWING_UI_KEY, mUiIds);
        }
    }

    /**
     * Util method to add badge for text view.
     * if show badge, add right compound drawable, else remove right compound drawable
     *
     * @param view
     * @param showIndicator
     */
    public static void processTextView(TextView view, boolean showIndicator) {
//        if (view.isEnabled() && showIndicator) {
//            view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, view.getResources().getDrawable(R.drawable.new_item_indicator_dot), null);
//            view.setCompoundDrawablePadding(view.getResources().getDimensionPixelSize(R.dimen.new_item_indicator_dot_padding));
//        } else {
//            view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
//        }
    }

    /**
     * interface to abstract persist storage, currently using shared preference
     */
    public interface PersistStorage {
        void putStringSet(String key, Set<String> values);
        Set<String> getStringSet(String key, Set<String> defValues);
        void putInt(String key, int value);
        int getInt(String key, int defValue);
    }

    /**
     * interface to abstract config reader, currently using assetmanager to read config
     */
    public interface ConfigReader {
        String getConfig();
    }

    /**
     * interface to abstract first install detection logic
     */
    public interface FirstInstallDetector {
        boolean isFirstInstall();
    }
}


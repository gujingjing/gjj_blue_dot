package com.example.myapplication.bluedot;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ConfigReader implementation that using Asset Manager
 */
public class ConfigReaderImpl implements NewItemIndicatorManager.ConfigReader {
    private static final String FILE_NAME = "config.json";

    @Override
    public String getConfig() {
        AssetManager assetManager = ContextUtils.getApplicationAssets();

        String res = "";

        try (BufferedReader r = new BufferedReader(new InputStreamReader(assetManager.open(FILE_NAME)))) {
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            res = total.toString();
        } catch (IOException e) {
            Log.e("ConfigReaderImpl", "getConfig", e);
        }

        return res;
    }

}

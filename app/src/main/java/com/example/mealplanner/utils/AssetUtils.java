package com.example.mealplanner.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class AssetUtils {

    public static boolean fileExistInAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try (InputStream ignored = assetManager.open(fileName)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
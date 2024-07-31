package com.jls.mealplanner.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public static Bitmap getIconFromAssets(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(filePath);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading icon from assets", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error while closing input stream", e);
                }
            }
        }
    }
}
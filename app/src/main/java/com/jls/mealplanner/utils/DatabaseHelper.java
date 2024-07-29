package com.jls.mealplanner.utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseHelper {

    private static final String DEFAULT_DB_NAME = "default_database.db";
    private static final String DB_NAME = "application_database.db";
    private static final boolean forceCopy = true;
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public void copyDefaultDatabaseToLocalStorage() throws IOException {
        if (!doesDatabaseExist() || forceCopy) {
            if (!AssetUtils.fileExistInAssets(context, DEFAULT_DB_NAME)) {
                throw new FileNotFoundException("Default database file not found in assets");
            }

            InputStream defaultDbInputStream = context.getAssets().open(DEFAULT_DB_NAME);
            OutputStream dbOutputStream = Files.newOutputStream(Paths.get(getDatabasePath()));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = defaultDbInputStream.read(buffer)) > 0) {
                dbOutputStream.write(buffer, 0, length);
            }
            dbOutputStream.flush();
            dbOutputStream.close();
            defaultDbInputStream.close();
        }
    }

    private boolean doesDatabaseExist() {
        File dbFile = context.getDatabasePath(DEFAULT_DB_NAME);
        return dbFile.exists();
    }

    private String getDatabasePath() {
        return context.getDatabasePath(DB_NAME).getPath();
    }
}
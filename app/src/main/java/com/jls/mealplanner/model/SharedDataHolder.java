package com.jls.mealplanner.model;

public class SharedDataHolder {

    private static SharedDataHolder INSTANCE = null;

    private boolean isTestRequestSent;

    private SharedDataHolder() {
        isTestRequestSent = false;
    }

    public static SharedDataHolder getInstance() {
        if (SharedDataHolder.INSTANCE == null) {
            SharedDataHolder.INSTANCE = new SharedDataHolder();
        }
        return SharedDataHolder.INSTANCE;
    }

    public boolean isTestRequestSent() {
        return isTestRequestSent;
    }

    public void setTestRequestSent(boolean isSent) {
        isTestRequestSent = isSent;
    }
}

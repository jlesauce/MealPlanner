package com.jls.mealplanner.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public SettingsViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is fragment Settings");
    }

    public LiveData<String> getText() {
        return text;
    }
}
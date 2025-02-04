package com.jls.mealplanner.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> text;

    public AboutViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is fragment About");
    }

    public LiveData<String> getText() {
        return text;
    }
}
package org.jls.mealplanner.ui.calendar.day;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DayModel extends ViewModel {

    private MutableLiveData<String> lunchLabel;
    private MutableLiveData<String> dinnerLabel;

    public DayModel() {
        lunchLabel = new MutableLiveData<>();
        dinnerLabel = new MutableLiveData<>();
        lunchLabel.setValue("Lunch");
        dinnerLabel.setValue("Dinner");
    }

    public LiveData<String> getLunchLabel() {
        return lunchLabel;
    }

    public LiveData<String> getDinnerLabel() {
        return dinnerLabel;
    }
}
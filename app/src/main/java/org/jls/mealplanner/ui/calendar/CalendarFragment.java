package org.jls.mealplanner.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jls.mealplanner.R;
import org.jls.mealplanner.ui.calendar.day.DayActivity;

public class CalendarFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calendar_fragment, container, false);
        CalendarView calendarView = root.findViewById(R.id.calendarView);

        addDateChangeListenerTo(calendarView);

        return root;
    }

    private void addDateChangeListenerTo(@NonNull final CalendarView calendarView) {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/" + dayOfMonth;

                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
package Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import utilities.FileHelper;
import utilities.TimerHelper;

// This class will be called by view model.
// then will call the file helper class to parse the data in local storage into List<AbsTimer>
public class TimerListRepository {
    private Application app;
    public MutableLiveData<List<AbsTimer>> timers;
    Timer refresher;
    public TimerListRepository(@NonNull Application application) {
        app = application;
        timers = new MutableLiveData<>();
        List<AbsTimer> data = FileHelper.getAbsTimersFromStorage(application,
                                                        application.getString(R.string.TimersJSON));
        //sorting by remaining time
        Collections.sort(data, new Comparator<AbsTimer>() {
            @Override
            public int compare(AbsTimer o1, AbsTimer o2) {
                return o2.getTimeRemaining() - o1.getTimeRemaining();
            }
        });
        TimerHelper.StartCounting(data);

        timers.setValue(data);
        updateValue();
    }

    // The observer at activity cannot monitor the remaining time in the live data even it is changing.
    // This method will update the live data with the same value and trigger the observer.
    private void updateValue() {
        refresher = new Timer();

        TimerTask refreshUI = new TimerTask() {
            @Override
            public void run() {
                List<AbsTimer> value = timers.getValue();
                timers.postValue(value);
            }
        };
        refresher.scheduleAtFixedRate(refreshUI, 0, 1000);
    }
}

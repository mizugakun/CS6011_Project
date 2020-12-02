package Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utilities.FileHelper;
import utilities.TimerHelper;

// The repository class will be called by view model.
// it will then call the file helper class to parse the data in local storage into List<AbsTimer>
public class TimerListRepository {
    public MutableLiveData<List<AbsTimer>> timers;
    public TimerListRepository(@NonNull Application application) {
        // live data for passing to view model class
        timers = new MutableLiveData<>();

        // get List<AbsTimer> data from local storage
        List<AbsTimer> data = FileHelper.getAbsTimersFromStorage(application,
                                                        application.getString(R.string.TimersJSON));
        //sorting by remaining time
        Collections.sort(data, new Comparator<AbsTimer>() {
            @Override
            public int compare(AbsTimer o1, AbsTimer o2) {
                return o2.getTimeRemaining() - o1.getTimeRemaining();
            }
        });

        // make the timers start counting
        TimerHelper.StartCounting(data);

        // set the List<AbsTimer> data as the main value in the live data
        timers.setValue(data);
    }
}

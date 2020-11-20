package Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cs6011_project.AbsTimer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import data.TimerData;
import utilities.FileHelper;
import utilities.TimerHelper;

public class TimerListRepository {
    private Application app;
    public MutableLiveData<List<AbsTimer>> timers;
    Timer refresher;
    public TimerListRepository(@NonNull Application application) {
        app = application;
        timers = new MutableLiveData<>();

        String jsonString = FileHelper.getTextFromAssets(app,"Timers.json");
        List<AbsTimer> data = FileHelper.ParseHelper(jsonString);
        TimerHelper.StartCounting(data);
        timers.setValue(data);
        updateValue();
    }

    private void updateValue() {
        refresher = new Timer();
        TimerTask refreshUI = new TimerTask() {
            @Override
            public void run() {
                timers.postValue(timers.getValue());
            }
        };
        refresher.scheduleAtFixedRate(refreshUI, 0, 1000);
    }
}

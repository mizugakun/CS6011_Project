package Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import data.TimerData;
import utilities.FileHelper;

public class TimerListRepository {
    private Application app;
    public MutableLiveData<List<TimerData>> timers;

    public TimerListRepository(@NonNull Application application) {
        app = application;
        timers = new MutableLiveData<>();

        String jsonString = FileHelper.getTextFromAssets(app,"Timers.json");
        List<TimerData> data = FileHelper.ParseHelper(jsonString);

        timers.setValue(data);
    }
}

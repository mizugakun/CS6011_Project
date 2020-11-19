package Repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cs6011_project.AbsTimer;

import java.util.List;

import data.TimerData;
import utilities.FileHelper;
import utilities.TimerHelper;

public class TimerListRepository {
    private Application app;
    public MutableLiveData<List<AbsTimer>> timers;

    public TimerListRepository(@NonNull Application application) {
        app = application;
        timers = new MutableLiveData<>();

        String jsonString = FileHelper.getTextFromAssets(app,"Timers.json");
        List<AbsTimer> data = FileHelper.ParseHelper(jsonString);
        timers.setValue(data);
        TimerHelper.StartCounting(data);
    }
}

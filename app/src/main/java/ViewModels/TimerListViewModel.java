package ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Repositories.TimerListRepository;
import data.TimerData;

public class TimerListViewModel extends ViewModel {
    private TimerListRepository repo;
    public MutableLiveData<List<TimerData>> timers;


    public TimerListViewModel(@NonNull Application application) {
        repo = new TimerListRepository(application);
        timers = repo.timers;
    }
}

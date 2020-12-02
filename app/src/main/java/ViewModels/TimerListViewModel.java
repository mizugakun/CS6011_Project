package ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs6011_project.AbsTimer;

import java.util.List;

import Repositories.TimerListRepository;
import data.TimerData;

// a class that responsible for communicate with repository class and activity class
public class TimerListViewModel extends ViewModel {
    private TimerListRepository repo;
    public MutableLiveData<List<AbsTimer>> timers;

    public TimerListViewModel(@NonNull Application application) {
        // create the repository class
        repo = new TimerListRepository(application);

        // pass the value in the repository to the view model
        timers = repo.timers;
    }
}

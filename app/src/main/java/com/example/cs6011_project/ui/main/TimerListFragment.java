package com.example.cs6011_project.ui.main;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ViewModels.TimerListViewModel;

// a fragment class for timer list UI
public class TimerListFragment extends Fragment {
    TimerListViewModel timerListViewModel;
    RecyclerView recyclerView;
    Timer refreshUI;

    // This method will be called when the UI is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_list, container, false);

        // find the recycler view that need an adapter
        recyclerView = view.findViewById(R.id.timers_list_view);

        // get view model of timers (local storage <--> repository <--> view model)
        timerListViewModel = new TimerListViewModel(getActivity().getApplication());

        // setting the observer and the adapter for monitoring the data and changing the value on the screen
        timerListViewModel.timers.observe(getViewLifecycleOwner(), new Observer<List<AbsTimer>>() {
            @Override
            public void onChanged(List<AbsTimer> timerData) {
                TimersRecyclerAdapter adapter = new TimersRecyclerAdapter(requireContext(), timerData);
                recyclerView.setAdapter(adapter);
            }
        });

        // set the function for refresh the UI
        setRefreshUI();
        return view;
    }

    // This method will be called when the UI has been created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // catch the "Add Timer" Button and set the event for navigate to the create timer UI
        view.findViewById(R.id.btn_Add_New_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TimerListFragment.this)
                        .navigate(R.id.action_timerListFragment_to_createTimerFragment);
            }
        });
    }


    // The observer at view model cannot monitor the remaining time in the live data even it is changing.
    // Instead of informing the observer to set the adapter, this method will notify the adapter that the data has been change per second.
    private void setRefreshUI() {
        // the task for the timer is notifying the adapter that every data in it has been updated
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Activity activity = getActivity();
                assert activity != null;

                // Only the original thread that created a view hierarchy can touch its views
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.Adapter adapter = recyclerView.getAdapter();
                        assert adapter != null;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        refreshUI = new Timer();
        // schedule the task with timer and make it execute the task every second
        refreshUI.schedule(task, 0, 1000);
    }



}
package com.example.cs6011_project;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ViewModels.TimerListViewModel;
import data.TimerData;
import utilities.FileHelper;

public class TimerListFragment extends Fragment {
    TimerListViewModel timerListViewModel;

//    @Override
//    public void onStart() {
//        super.onStart();
//        timerListViewModel = new ViewModelProvider(this).get(TimerListViewModel.class);
//        timerListViewModel.timers.observe(getViewLifecycleOwner(), new Observer<List<TimerData>>() {
//            @Override
//            public void onChanged(List<TimerData> timerData) {
//                FileHelper.TimerLogHelper(timerData);
//            }
//        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        timerListViewModel = new TimerListViewModel(getActivity().getApplication());
        timerListViewModel.timers.observe(getViewLifecycleOwner(), new Observer<List<TimerData>>() {
            @Override
            public void onChanged(List<TimerData> timerData) {
                FileHelper.TimerLogHelper(timerData);
            }
        });
        return inflater.inflate(R.layout.fragment_timer_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_Add_New_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TimerListFragment.this)
                        .navigate(R.id.action_timerListFragment_to_createTimerFragment);
            }
        });
    }

}
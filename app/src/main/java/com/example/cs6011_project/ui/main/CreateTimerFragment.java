package com.example.cs6011_project.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cs6011_project.R;
import com.example.cs6011_project.ui.dialog.customTimerDialog;

import java.time.LocalDateTime;

import data.TimerData;
import utilities.FileHelper;
import utilities.TimerHelper;

import static androidx.core.content.ContextCompat.getSystemService;

// a fragment class for create timer UI
public class CreateTimerFragment extends Fragment {
    Spinner timerName;
    Spinner timerType;
    EditText offset_day;
    Spinner offset_hr;
    Spinner offset_min;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // make keyboard won't push the UI component up
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // catch the "Cancel" Button and set the event for navigate to the previous fragment
        view.findViewById(R.id.btn_Withdraw_Adding_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CreateTimerFragment.this).popBackStack();
            }
        });

        // catch the "Create" button and set the event for the creation of a timer
        view.findViewById(R.id.btn_Confirm_Adding_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTimerEvent(v);
            }
        });

        // get UI components of dropdown lists
        timerName = view.findViewById(R.id.timers_name_spinner);
        timerType = view.findViewById(R.id.timers_type_spinner);
        offset_day =  view.findViewById(R.id.editText_timers_offset_day);
        offset_hr = view.findViewById(R.id.spinner_timer_offset_hours);
        offset_min = view.findViewById(R.id.spinner_timer_offset_minutes);

        // create Spinner Content
        createSpinner(timerName, R.array.timer_name_array, true);
        createSpinner(timerType, R.array.timer_type_array, true);
        createSpinner(offset_hr, 24, false);
        createSpinner(offset_min, 60, false);
    }

    //create a timer
    private void createTimerEvent(View view) {
        // create the timer if the input is right, otherwise show the dialog for warning
        if (isInputCorrect()) {
            // find four major parameters of a timer
            String name = timerName.getSelectedItem().toString();
            String type = timerType.getSelectedItem().toString();
            LocalDateTime startDate = TimerHelper.getStartDate(Integer.parseInt(offset_day.getText().toString()),
                                                Integer.parseInt(offset_hr.getSelectedItem().toString()),
                                                Integer.parseInt(offset_min.getSelectedItem().toString()));
            int duration = FileHelper.getDurationHelper(getContext(), name, type);

            // write timer's data into the local storage
            FileHelper.addOneTimer(getContext(), new TimerData(name, type, duration, startDate.toString()));

            // close the keyboard
            closeKeyBoard(view);

            // navigate the UI back to timer list UI
            NavHostFragment.findNavController(CreateTimerFragment.this).popBackStack();
        } else {
            // create the instance of dialog
            customTimerDialog d = new customTimerDialog(view.getContext());

            // the dialog need to be shown before access.
            d.show();

            // set the elements in the dialog UI
            d.setDialogIcon(R.drawable.ic_caution_foreground);
            d.setDialogTitle("WRONG FORMAT!!");
            d.setDialogMessage("Please enter valid number in days.");
            d.positiveButton.setText("Confirm");
        }
    }

    // judge whether the input of creating timer is correct
    private boolean isInputCorrect() {
        String days;

        // Catch the "days" text box component
        EditText offset_day_view = getView().findViewById(R.id.editText_timers_offset_day);

        // get the text in the text box
        days = offset_day_view.getText().toString();

        int val;
        // if the text cannot be parse to the number, return false; otherwise return true
        try {
            val = Integer.parseInt(days);
        } catch (NumberFormatException e) {
            val = -1;
        }

        return val >= 0;
    }

    // create the content in the dropdown list
    private void createSpinner(Spinner spinner, int val, boolean isArrayId) {
        // adapter for dropdown list
        ArrayAdapter<CharSequence> adapter;

        // check whether the content of the adapter for the dropdown list is created by Resource or adding number ranging from 0 to certain value
        if (isArrayId) {
            adapter = createAdapterWithArrayId(val);
        } else {
            adapter = createAdapterWithCount(val);
        }

        // set the UI of the adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the adapter and the dropdown list
        spinner.setAdapter(adapter);
        // set the initial selection
        spinner.setSelection(0);
    }

    // create the content in the adapter with existing resource
    private ArrayAdapter<CharSequence> createAdapterWithArrayId (int ArrayId) {
        // createFromResource will search for certain preset array based on the id,
        // make every element in the array as the content in the adapter,
        // and set UI component for the items in the content
        return ArrayAdapter.createFromResource(getContext(),
                ArrayId, android.R.layout.simple_spinner_item);
    }
    private ArrayAdapter<CharSequence> createAdapterWithCount(int Counts) {
        // create an adapter with certain UI
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);

        // add option in the adapter
        for (int i = 0 ; i < Counts; i++) {
            adapter.add(String.format("%02d", i));
        }
        return adapter;
    }

    // hide the keyboard
    private void closeKeyBoard(View view) {
        View rootView = view.getRootView();
        InputMethodManager imm = getSystemService(rootView.getContext(), InputMethodManager.class);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }
}
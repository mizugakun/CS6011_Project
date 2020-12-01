package com.example.cs6011_project.ui.main;

import android.app.AlertDialog;
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

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link CreateTimerFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class CreateTimerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    public CreatTimerFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment CreatTimerFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static CreatTimerFragment newInstance(String param1, String param2) {
//        CreatTimerFragment fragment = new CreatTimerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    Spinner timerName;
    Spinner timerType;
    EditText offset_day;
    Spinner offset_hr;
    Spinner offset_min;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return inflater.inflate(R.layout.fragment_create_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_Withdraw_Adding_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CreateTimerFragment.this).popBackStack();
            }
        });
        view.findViewById(R.id.btn_Confirm_Adding_Timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTimerEvent(v);
            }
        });

        // get UI Components
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

            closeKeyBoard(view);
            NavHostFragment.findNavController(CreateTimerFragment.this).popBackStack();
        } else {
            customTimerDialog d = new customTimerDialog(view.getContext());
            d.show();
            d.setDialogIcon(R.drawable.ic_caution_foreground);
            d.setDialogTitle("WRONG FORMAT!!");
            d.setDialogMessage("Please enter valid number in days.");
            d.positiveButton.setText("Confirm");
        }
    }

    private boolean isInputCorrect() {
        String days;
        EditText offset_day_view = getView().findViewById(R.id.editText_timers_offset_day);
        days = offset_day_view.getText().toString();

        int val;
        try {
            val = Integer.parseInt(days);
        } catch (NumberFormatException e) {
            val = -1;
        }

        return val >= 0;
    }

    private void createSpinner(Spinner spinner, int val, boolean isArrayId) {
        ArrayAdapter<CharSequence> adapter;
        if (isArrayId) {
            adapter = createAdapterWithArrayId(val);
        } else {
            adapter = createAdapterWithCount(val);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    private ArrayAdapter<CharSequence> createAdapterWithArrayId (int ArrayId) {
        return ArrayAdapter.createFromResource(getContext(),
                ArrayId, android.R.layout.simple_spinner_item);
    }
    private ArrayAdapter<CharSequence> createAdapterWithCount(int Counts) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = 0 ; i < Counts; i++) {
            adapter.add(String.format("%02d", i));
        }
        return adapter;
    }

    private void closeKeyBoard(View view) {
        View rootView = view.getRootView();
        InputMethodManager imm = getSystemService(rootView.getContext(), InputMethodManager.class);
        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }
}
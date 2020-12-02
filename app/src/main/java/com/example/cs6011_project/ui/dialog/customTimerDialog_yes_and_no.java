package com.example.cs6011_project.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs6011_project.R;

// a class for customized dialog (with confirm and cancel buttons)
public class customTimerDialog_yes_and_no extends customTimerDialog {

    public Button negativeButton;

    // constructor
    public customTimerDialog_yes_and_no(Context context) {
        super(context);
        setContentView(R.layout.dialog_yes_and_no);
    }

    // call this method when the UI is in creation stage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // catch the button on the UI
        negativeButton = findViewById(R.id.button_cancel);

        // set the click event for dismissing the dialog window
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

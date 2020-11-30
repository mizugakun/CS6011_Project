package com.example.cs6011_project.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs6011_project.R;


public class customTimerDialog_yes_and_no extends customTimerDialog {

    public Button negativeButton;
    public customTimerDialog_yes_and_no(Context context) {
        super(context);
        setContentView(R.layout.dialog_yes_and_no);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        negativeButton = findViewById(R.id.button_cancel);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

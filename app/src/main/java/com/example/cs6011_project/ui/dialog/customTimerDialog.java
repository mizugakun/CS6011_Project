package com.example.cs6011_project.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.cs6011_project.R;

// a class for customized dialog (with only one button)
public class customTimerDialog extends Dialog {
    public Button positiveButton;

    // constructor
    public customTimerDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_notification);
    }

    // call this method when the UI is in creation stage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // catch the button on the UI
        positiveButton = findViewById(R.id.button_confirm);

        // set the click event for dismissing the dialog window
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    // set the title content of the dialog
    public void setDialogTitle(@Nullable CharSequence title) {
        // finds the UI component of title text in the dialog UI
        TextView titleView = this.findViewById(R.id.dialog_title);

        // change the text content in the component
        titleView.setText(title);
    }

    // set the main information content of the dialog
    public void setDialogMessage(CharSequence message) {
        // finds the UI component of information block in the dialog UI
        TextView messageView = findViewById(R.id.dialog_content);

        // change the text content in the component
        messageView.setText(message);
    }

    // set the icon of the dialog
    public void setDialogIcon(int resId) {
        // finds the UI component of icon in the dialog UI
        ImageView iconView = this.findViewById(R.id.dialog_icon);

        // set the image source of the image view
        iconView.setImageResource(resId);
    }
}

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

    public customTimerDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positiveButton = findViewById(R.id.button_confirm);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setDialogTitle(@Nullable CharSequence title) {
        TextView titleView = this.findViewById(R.id.dialog_title);
        titleView.setText(title);
    }

    public void setDialogMessage(CharSequence message) {
        TextView messageView = findViewById(R.id.dialog_content);
        messageView.setText(message);
    }

    public void setDialogIcon(int resId) {
        ImageView iconView = this.findViewById(R.id.dialog_icon);
        iconView.setImageResource(resId);
    }
}

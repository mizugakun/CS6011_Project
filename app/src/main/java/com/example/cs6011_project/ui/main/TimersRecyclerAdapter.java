package com.example.cs6011_project.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;
import com.example.cs6011_project.ui.dialog.customTimerDialog;
import com.example.cs6011_project.ui.dialog.customTimerDialog_yes_and_no;

import java.util.List;

import utilities.FileHelper;

public class TimersRecyclerAdapter extends RecyclerView.Adapter<TimersRecyclerAdapter.ViewHolder> {
    Context context;
    List<AbsTimer> timers;
    public TimersRecyclerAdapter(@NonNull Context context, @NonNull List<AbsTimer> timers) {
        this.context = context;
        this.timers = timers;
    }

    // in the adapter, the view holder will store the UI components as the parameters
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.timer_list_item, parent, false);
        return new ViewHolder(view);
    }

    // binding the data in the array to the view holder's parameters
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AbsTimer timer = timers.get(position);
        holder.name.setText(timer.getTimerName());
        holder.type.setText(timer.getType());
        holder.day.setText(String.format("%03d", timer.getDays()));
        holder.hour.setText(String.format("%02d", timer.getHours()));
        holder.minute.setText(String.format("%02d", timer.getMinutes()));
        holder.second.setText(String.format("%02d", timer.getSeconds()));
        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTimerDialog d = new customTimerDialog(v.getContext());
                d.show();
                d.setDialogIcon(R.drawable.ic_information_foreground);
                d.setDialogTitle("Timer's Information");
                d.setDialogMessage(getTimerInformation(timer));
                d.positiveButton.setText("Confirm");
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final customTimerDialog_yes_and_no d = new customTimerDialog_yes_and_no(v.getContext());
                d.show();
                d.setDialogIcon(R.drawable.ic_caution_foreground);
                d.setDialogTitle("Caution!");
                d.setDialogMessage("The object may still be unsafe to touch! \nThis action cannot be reversed.\nAre you sure you want to delete this timer?");
                d.positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTimerEvent(position);
                        d.dismiss();
                    }
                });
            }
        });
    }

    private String getTimerInformation(AbsTimer timer) {
        String message = "Timer Type: " + timer.getTimerName() + "\n"
                        + "Surface Type: " + timer.getType() + "\n\n"
                        + timer.getAdvice();
        return message;
    }

    private void deleteTimerEvent(int position) {
        timers.remove(position);
        FileHelper.deleteOneTimer(context, timers);
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        TextView day;
        TextView hour;
        TextView minute;
        TextView second;
        Button btn_info;
        Button btn_delete;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.timer_name);
            type = view.findViewById(R.id.timer_type);
            day = view.findViewById(R.id.timer_day);
            hour = view.findViewById(R.id.timer_hour);
            minute = view.findViewById(R.id.timer_minute);
            second = view.findViewById(R.id.timer_second);
            btn_info = view.findViewById(R.id.btn_timer_info);
            btn_delete = view.findViewById(R.id.btn_timer_delete);
        }
    }

}

package com.example.cs6011_project.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;

import java.util.List;

import utilities.FileHelper;

public class TimersRecyclerAdapter extends RecyclerView.Adapter<TimersRecyclerAdapter.ViewHolder> {
    Context context;
    List<AbsTimer> timers;
    public TimersRecyclerAdapter(@NonNull Context context, @NonNull List<AbsTimer> timers) {
        this.context = context;
        this.timers = timers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.timer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AbsTimer timer = timers.get(position);
        final String advice = timer.getAdvice();
        holder.name.setText(timer.getTimerName());
        holder.type.setText(timer.getType());
        holder.day.setText(String.format("%03d", timer.getDays()));
        holder.hour.setText(String.format("%02d", timer.getHours()));
        holder.minute.setText(String.format("%02d", timer.getMinutes()));
        holder.second.setText(String.format("%02d", timer.getSeconds()));
        holder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Timer's information")
                        .setMessage(advice)
                        .setPositiveButton(R.string.got_it, null)
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .show();
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timers.remove(position);
                FileHelper.deleteOneTimer(context, timers);
            }
        });
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

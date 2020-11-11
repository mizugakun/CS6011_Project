package com.example.cs6011_project.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs6011_project.R;

import java.util.List;

import data.TimerData;

public class TimersRecyclerAdapter extends RecyclerView.Adapter<TimersRecyclerAdapter.ViewHolder> {
    Context context;
    List<TimerData> timers;
    public TimersRecyclerAdapter(@NonNull Context context, @NonNull List<TimerData> timers) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimerData data = timers.get(position);
        holder.name.setText(data.name);
        holder.type.setText(data.type);
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

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.timer_name);
            type = view.findViewById(R.id.timer_type);
            day = view.findViewById(R.id.timer_day);
            hour = view.findViewById(R.id.timer_hour);
            minute = view.findViewById(R.id.timer_minute);
            second = view.findViewById(R.id.timer_second);
        }
    }


}

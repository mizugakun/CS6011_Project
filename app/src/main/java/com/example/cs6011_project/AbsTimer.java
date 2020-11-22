package com.example.cs6011_project;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import utilities.TimerHelper;

public abstract class AbsTimer {
    private int timeremaining;
    protected int duration;
    protected LocalDateTime startDate;
    protected String timerName, type, advice;
    protected int days, hours, minutes, seconds;
    private Timer timer;
    private TimerTask countdown;

    public AbsTimer(String timerName, String type, LocalDateTime startDate) {
        this.timerName = timerName;
        this.type = type;
        this.startDate = startDate;

        timer = new Timer();
        countdown = new TimerTask() {
            public void run() {
                getLog();
                if (timeremaining > 0) {
                    timeremaining--;
                    updateTime(timeremaining);
                }
                else {
                    timeremaining = 0;
                    updateTime(timeremaining);
                    timer.cancel();
                }
            }
        };
    }

    //in main needs to create the object, then do object.start()
    public void start() {
        timer.scheduleAtFixedRate(countdown, 0, 1000);
    }
    public void updateTime(int totalsec) {
    	int remainder;
    	days = totalsec / 86400;
    	remainder = totalsec % 86400;
    	hours = remainder / 3600;
    	remainder = remainder % 3600;
    	minutes = remainder / 60;
    	remainder = remainder % 60;
    	seconds = remainder;
    }
    public void setAdvice(String newAdvice) {
    	advice = newAdvice;
    }
    public void setDuration(int duration) {
        this.duration = duration;
        timeremaining = duration - TimerHelper.getOffset(startDate);
    }
    public void getLog() {
        Log.i("Timer Data", String.format("%d days %d:%d:%d", days, hours, minutes, seconds));
    }

    public String getTimerName() { return timerName; }
    public String getType() { return type; }
    public int getDuration() {return duration; }
    public LocalDateTime getStartDate() { return startDate; }

    public int getDays() { return days; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }
}

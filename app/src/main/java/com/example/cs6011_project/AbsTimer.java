package com.example.cs6011_project;

import android.util.Log;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import utilities.TimerHelper;

// basic class for the timer that the project will use
public abstract class AbsTimer {
    private int timeRemaining;
    protected String timerName, type, advice;
    protected int days, hours, minutes, seconds;
    private Timer timer;
    private TimerTask countdown;

    public AbsTimer(String timerName, String type, int duration, LocalDateTime startDate) {
        this.timerName = timerName;
        this.type = type;

        // calculate the remaining seconds
        timeRemaining = duration - TimerHelper.getOffset(startDate);

        // create the instance of timer
        timer = new Timer();

        // determine the task for counting down.
        countdown = new TimerTask() {
            public void run() {
                // if remaining seconds > 0, update the properties of this class; otherwise close
                // the timer.
                if (timeRemaining > 0) {
                    timeRemaining--;
                    updateTime(timeRemaining);
                }
                else {
                    timeRemaining = 0;
                    updateTime(timeRemaining);
                    timer.cancel();
                }
            }
        };
    }

    //in main needs to create the object, then do object.start()
    public void start() {
        timer.scheduleAtFixedRate(countdown, 0, 1000);
    }

    // update days, hours, minutes, seconds in this class
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

    // set the specific advice for this timer class
    public void setAdvice(String newAdvice) {
    	advice = newAdvice;
    }

    // get certain properties in this class
    public String getTimerName() { return timerName; }
    public String getType() { return type; }
    public int getTimeRemaining(){ return timeRemaining; }
    public String getAdvice() { return advice; }

    public int getDays() { return days; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }
}

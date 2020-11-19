package com.example.cs6011_project;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public abstract class AbsTimer {
//	private Date created;
    private int timeremaining;
    protected String advice, type;
    protected int days, hours, minutes, seconds;

    public AbsTimer() {
        
    }

    Timer timer = new Timer();
    TimerTask countdown = new TimerTask() {
        public void run() {
            getLog();
            if (timeremaining > 0) {
                timeremaining--;
//                System.out.println(timeremaining);
                updatetime(timeremaining);
            }
            else {
                timeremaining = 0;
                updatetime(timeremaining);
                timer.cancel();
            }
        }
    };

    //in main needs to create the object, then do object.start()
    public void start() {
        timer.scheduleAtFixedRate(countdown, 0, 1000);
    }
    public void setTimeRemaining(int totaltime, int offset) {
    	timeremaining = totaltime - offset;
    }
    public void updatetime(int totalsec) {
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
    public void setType (String type) { this.type = type; }
//    public void setCreated(Date todayDate) {
//    	created = todayDate;
//    }
    public void getLog() {
        Log.i("Timer Data", String.format("%d days %d:%d:%d", days, hours, minutes, seconds));
    }
    public String getType() { return type; }
    public int getDays() { return days; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public int getSeconds() { return seconds; }



}

package com.example.cs6011_project;

import java.time.LocalDateTime;
import java.util.Date;

import utilities.TimerHelper;

public class LatexPaintTimer extends AbsTimer {
	String specificAdvice = "Avoid touching your eyes, nose, mouth, \n"
			+ "	or any object you may place on these places.\n"
			+ " Wash your hands with soap for at least 30 seconds.\n"
			+ " If the paint does not come off, apply mineral or baby oil\n"
			+ " for 2-3 minutes, then try washing again.";
	
	public LatexPaintTimer(String timerName, String type, LocalDateTime startDate) {
		super(timerName, type, startDate);
		int duration = getInit(type);
		this.setDuration(duration);
		this.setAdvice(specificAdvice);
	}

	private int getInit (String type) {
		int initValue = 3600;
		switch (type) {
			case "paper": initValue = 5400; break;
			case "cardboard": initValue = 5400; break;
			case "plastic": initValue = 5400; break;
			case "touchscreen": initValue = 5400; break;
			case "cloth": initValue = 5400; break;
			case "handwashing": initValue = 30; break;
			//reference: https://www.glidden.com/inspiration/all-articles/how-long-does-paint-take-to-dry-cure
		}
		return initValue;
	}
}

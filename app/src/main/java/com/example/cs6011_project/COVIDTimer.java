package com.example.cs6011_project;

import java.time.LocalDateTime;

public class COVIDTimer extends AbsTimer {
	String specificAdvice = "Avoid touching your eyes, nose, mouth, \n"
			+ "	or any object you may place on these places.\n"
			+ " Wash your hands with soap for at least 20 seconds.\n"
			+ " dry hands and operate faucet with disposable paper towel\n"
			+ " if available. Be conscious of other surfaces touched.";

	public COVIDTimer(String timerName, String type, LocalDateTime startDate) {
		super(timerName, type, startDate);
		int duration = getInit(type);
		this.setDuration(duration);
		this.setAdvice(specificAdvice);
	}

	private int getInit(String type) {
		int initValue = 3600;
		switch (type) {
			case "paper": initValue = 10800; break;
			case "cardboard": initValue = 86400; break;
			case "plastic": initValue = 432000; break;
			case "touchscreen": initValue = 432000; break;
			case "cloth": initValue = 172800; break;
			case "handwashing": initValue = 30; break;
			//reference: https://www.sfchronicle.com/health/article/Coronavirus-FAQ-How-long-does-it-stay-on-15152021.php
		}
		return initValue;
	}
}

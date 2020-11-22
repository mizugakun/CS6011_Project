package com.example.cs6011_project;

import java.time.LocalDateTime;
import java.util.Date;

public class FluTimer extends AbsTimer {
	String specificAdvice = "Avoid touching your eyes, nose, mouth, \n"
			+ "	or any object you may place on these places.\n"
			+ " Wash your hands with soap for at least 20 seconds.\n"
			+ " dry hands and operate faucet with disposable paper towel\n"
			+ " if available. Be conscious of other surfaces touched.";
	
	public FluTimer(String timerName, String type, LocalDateTime startDate) {
		super(timerName, type, startDate);
		int duration = getInit(type);
		this.setDuration(duration);
		this.setAdvice(specificAdvice);
	}

	private int getInit(String type) {
		int initValue = 3600;
		switch (type) {
			case "paper": initValue = 32400; break;
			case "cardboard": initValue = 259200; break;
			case "plastic": initValue = 259200; break;
			case "touchscreen": initValue = 259200; break;
			case "cloth": initValue = 32400; break;
			case "handwashing": initValue = 30; break;
			//reference: https://pubmed.ncbi.nlm.nih.gov/6282993/
		}
		return initValue;
	}
}

package com.example.cs6011_project;

import java.time.LocalDateTime;

public class COVIDTimer extends AbsTimer {
	// duration reference: https://www.sfchronicle.com/health/article/Coronavirus-FAQ-How-long-does-it-stay-on-15152021.php
	String specificAdvice = "Avoid touching your eyes, nose, mouth, or any object you may place on these places. Wash your hands with soap for at least 20 seconds. Dry hands and operate faucet with disposable paper towel if available. Be conscious of other surfaces touched.";

	public COVIDTimer(String timerName, String type, int duration, LocalDateTime startDate) {
		super(timerName, type, duration, startDate);
		this.setAdvice(specificAdvice);
	}
}

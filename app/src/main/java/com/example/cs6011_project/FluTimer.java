package com.example.cs6011_project;

import java.time.LocalDateTime;

// a subclass for influenza timer
public class FluTimer extends AbsTimer {

	//duration reference: https://pubmed.ncbi.nlm.nih.gov/6282993/
	String specificAdvice = "Avoid touching your eyes, nose, mouth, or any object you may place on these places. Wash your hands with soap for at least 20 seconds. Dry hands and operate faucet with disposable paper towel if available. Be conscious of other surfaces touched.";
	
	public FluTimer(String timerName, String type, int duration, LocalDateTime startDate) {
		super(timerName, type, duration, startDate);
		this.setAdvice(specificAdvice);
	}
}

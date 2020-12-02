package com.example.cs6011_project;

import java.time.LocalDateTime;

// a subclass for latex paint timer
public class LatexPaintTimer extends AbsTimer {
	// duration reference: https://www.glidden.com/inspiration/all-articles/how-long-does-paint-take-to-dry-cure
	String specificAdvice = "Avoid touching your eyes, nose, mouth, or any object you may place on these places. Wash your hands with soap for at least 30 seconds. If the paint does not come off, apply mineral or baby oil for 2-3 minutes, then try washing again.";
	
	public LatexPaintTimer(String timerName, String type, int duration, LocalDateTime startDate) {
		super(timerName, type, duration, startDate);
		this.setAdvice(specificAdvice);
	}
}

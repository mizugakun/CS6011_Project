package com.example.cs6011_project;

import java.time.LocalDateTime;

// a subclass for craft glue timer
public class CraftGlueTimer extends AbsTimer {
	// duration reference: https://www.wikihow.com/Get-Glue-off-Your-Hands
	String specificAdvice = "Avoid touching your eyes, nose, mouth, or any object you may place on these places. Soak your hands in warm water for several minutes. Scrub hands until craft glue is removed.";
	
	public CraftGlueTimer(String timerName, String type, int duration, LocalDateTime startDate) {
		super(timerName, type, duration, startDate);
		this.setAdvice(specificAdvice);
	}
}

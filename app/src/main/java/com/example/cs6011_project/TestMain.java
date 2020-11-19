package com.example.cs6011_project;

public class TestMain extends AbsTimer {
	protected static int hours, minutes, seconds;
	public static void main(String[] args) {		
		//User would set hours, minutes, seconds using GUI.
		int finaloffset = getOffset(hours, minutes, seconds);
		AbsTimer testcase = new COVIDTimer("handwashing", finaloffset, false);
		testcase.start();
	}	
	public static int getOffset(int days, int hours, int minutes) {
		int finaloffset = 0;
			finaloffset += days * 86400;
			finaloffset += hours * 3600;
			finaloffset += minutes * 60;
		return finaloffset;
	}
}

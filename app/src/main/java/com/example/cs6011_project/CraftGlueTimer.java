import java.util.Date;

public class CraftGlueTimer extends AbsTimer {
	String specificAdvice = "Avoid touching your eyes, nose, mouth, \n"
			+ "	or any object you may place on these places.\n"
			+ " Soak your hands in warm water for several minutes.\n"
			+ " Scrub hands until craft glue is removed.";
	
	public CraftGlueTimer(String type, int secondsago, boolean existing) {
		int initValue = 3600;
		switch (type) {
		case "paper": initValue = 86400; break;
		case "cardboard": initValue = 86400; break;
		case "plastic": initValue = 86400; break;
		case "touchscreen": initValue = 86400; break;
		case "cloth": initValue = 86400; break;
		case "handwashing": initValue = 420; break;
		//reference: https://www.wikihow.com/Get-Glue-off-Your-Hands
		}
		
		this.setTimeRemaining(initValue, secondsago);
		this.setAdvice(specificAdvice);
		setCreated(new Date());
	}
}

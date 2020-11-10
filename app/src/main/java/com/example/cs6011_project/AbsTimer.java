import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public abstract class AbsTimer {
	private Date created;
    private int timeremaining;
    protected String advice;

    public AbsTimer() {
        
    }

    Timer timer = new Timer();
    TimerTask countdown = new TimerTask() {
        public void run() {
            if (timeremaining != 0) {
                timeremaining--;
                System.out.println(timeremaining);
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
    public void setAdvice(String newAdvice) {
    	advice = newAdvice;
    }
    public void setCreated(Date todayDate) {
    	created = todayDate;
    }
    
}

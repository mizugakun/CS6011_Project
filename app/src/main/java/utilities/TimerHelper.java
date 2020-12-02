package utilities;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.COVIDTimer;
import com.example.cs6011_project.CraftGlueTimer;
import com.example.cs6011_project.FluTimer;
import com.example.cs6011_project.LatexPaintTimer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

// calculate and create anything related to the timer
public class TimerHelper {
    // calculate total seconds based on the numbers of days, hours, minutes, and seconds
    public static int getTotalSeconds(int days, int hours, int minutes, int second) {
        int finaloffset = 0;
        finaloffset += days * 86400;
        finaloffset += hours * 3600;
        finaloffset += minutes * 60;
        return finaloffset + second;
    }

    // get the offset by (present timer - start time)
    public static int getOffset(LocalDateTime startTime) {
        // get present time
        LocalDateTime now = LocalDateTime.now();

        // get Period(days) between now and start date
        Period p = Period.between(startTime.toLocalDate(), now.toLocalDate());

        // get Duration(hour/minutes/seconds) between now and start date
        Duration d = Duration.between(startTime.toLocalTime(), now.toLocalTime());

        // calculate and return the total seconds
        return getTotalSeconds(p.getDays(), 0, 0, (int)d.getSeconds());
    }

    // get start date by (present time - Duration(seconds))
    public static LocalDateTime getStartDate(int seconds) {
        Duration d = Duration.ofSeconds(seconds);
        return LocalDateTime.now().minus(d);
    }
    // get total seconds first, then get start date by calling previous method
    public static LocalDateTime getStartDate(int days, int hours, int minutes) {
        return getStartDate(getTotalSeconds(days, hours, minutes, 0));
    }

    // create the instance based on the timersName
    public static AbsTimer getInstance(String timersName, String type, int duration, LocalDateTime startDate) {
        switch (timersName) {
            case "COVID-19":
                return new COVIDTimer(timersName, type, duration,startDate);
            case "Influenza":
                return new FluTimer(timersName, type, duration, startDate);
            case "Latex Painter":
                return new LatexPaintTimer(timersName, type, duration, startDate);
            case "Craft Glue":
                return new CraftGlueTimer(timersName, type, duration, startDate);
            default:
                return null;
        }
    }

    // make timer class execute the timer task
    public static void StartCounting(List<AbsTimer> timers) {
        for (AbsTimer timer : timers) {
            timer.start();
        }
    }
}

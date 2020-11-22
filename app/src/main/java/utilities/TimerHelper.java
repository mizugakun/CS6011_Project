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

public class TimerHelper {
    public static int getOffset(int days, int hours, int minutes, int second) {
        int finaloffset = 0;
        finaloffset += days * 86400;
        finaloffset += hours * 3600;
        finaloffset += minutes * 60;
        return finaloffset + second;
    }

    public static int getOffset(LocalDateTime startTime) {
        LocalDateTime now = LocalDateTime.now();
        Period p = Period.between(startTime.toLocalDate(), now.toLocalDate());
        Duration d = Duration.between(startTime.toLocalTime(), now.toLocalTime());
        return getOffset(p.getDays(), 0, 0, (int)d.getSeconds());
    }

    public static AbsTimer getInstance(String timersName, String type, LocalDateTime startDate) {
        switch (timersName) {
            case "COVID-19":
                return new COVIDTimer(timersName, type, startDate);
            case "Influenza":
                return new FluTimer(timersName, type, startDate);
            case "Latex Painter":
                return new LatexPaintTimer(timersName, type, startDate);
            case "Craft Glue":
                return new CraftGlueTimer(timersName, type, startDate);
            default:
                return null;
        }
    }

    public static void StartCounting(List<AbsTimer> timers) {
        for (AbsTimer timer : timers) {
            timer.start();
        }
    }
}

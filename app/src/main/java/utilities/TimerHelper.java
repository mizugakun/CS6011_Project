package utilities;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.COVIDTimer;
import com.example.cs6011_project.FluTimer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class TimerHelper {
    public static int getOffset(int days, int hours, int minutes, int second) {
        int finaloffset = 0;
        finaloffset += days * 86400;
        finaloffset += hours * 3600;
        finaloffset += minutes * 60;
        return finaloffset + second;
    }

    public static int getOffset(int duration, LocalDateTime startTime) {
        LocalDateTime now = LocalDateTime.now();
        Period p = Period.between(startTime.toLocalDate(), now.toLocalDate());
        Duration d = Duration.between(startTime.toLocalTime(), now.toLocalTime());
        return getOffset(p.getDays(), 0, 0, (int)d.getSeconds());
    }

    public static String getTimersName(AbsTimer timer) {
        if (timer instanceof COVIDTimer) {
            return "COVID-19";
        } else {
            return "Influenza";
        }
    }

    public static AbsTimer getInstance(String timersName, String type, int duration, LocalDateTime startDate) {
        int offset = getOffset(duration, startDate);
        if (timersName.equals("COVID-19")) {
            return new COVIDTimer(type, offset, true);
        } else if (timersName.equals("Influenza") ){
            return new FluTimer(type, offset, true);
        } else {
            return null;
        }
    }
}

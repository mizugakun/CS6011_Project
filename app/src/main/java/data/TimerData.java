package data;

// data structure for storing the information of existing timer
public class TimerData {
    public String name;
    public String type;
    public int duration;
    public String start_date;

    public TimerData(String name, String type, int duration, String start_date) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.start_date = start_date;
    }
}

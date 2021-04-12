package Models;

public class TimeBisyTable
{
    private String time_with;
    private String time_after;

    public TimeBisyTable(String time_with, String time_after) {
        this.time_with = time_with;
        this.time_after = time_after;
    }

    public String getTime_with() {
        return time_with;
    }

    public String getTime_after() {
        return time_after;
    }
}

package engine.trips;

public class Time {
    private int hours;
    private int minutes;
    private int day;

    public Time(int minutes, int hours, int day) {
        this.hours = hours;
        this.minutes = minutes;
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getDay() {
        return day;
    }

    public void addMinutes(int sumMinutes) {
        this.minutes += sumMinutes;
        handleCircle();
    }

    private void handleCircle() {
        while(minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        while(hours >= 24) {
            hours -= 24;
            day++;
        }
    }

    @Override
    public String toString() {
       StringBuilder str = new StringBuilder();
       str.append(String.format("Day %d ", day));
       if(hours < 10) {
           str.append(String.format("0%d:", hours));
       }
       else {
           str.append(String.format("%d:", hours));
       }

       if(minutes < 10) {
           str.append(String.format("0%d", minutes));
       }
       else  {
           str.append(String.format("%d", minutes));
       }
       return str.toString();
    }
}

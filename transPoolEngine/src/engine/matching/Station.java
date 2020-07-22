package engine.matching;


import engine.trips.Time;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.List;

public class Station {
    private String name;
    private Time arrivalTime;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return arrivalTime.getDay();
    }

    public int getHour() {
        return arrivalTime.getHours();
    }

    public int getMinutes() {
        return arrivalTime.getMinutes();
    }

    public Time getTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time time) {
        this.arrivalTime = time;
    }

    public static int getXCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getX();
            }
        }
        return -1;
    }

    public static int getYCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getY();
            }
        }
        return -1;
    }
}

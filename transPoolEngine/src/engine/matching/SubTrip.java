package engine.matching;

import engine.utils.TripsUtil;
import engine.trips.Time;
import engine.trips.TripSuggest;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.LinkedList;

public class SubTrip {
    private MapDescriptor map;
    private LinkedList<Station> stations;
    private int cost;
    private int requiredFuel;
    private TripSuggest trip;
    private int tripID;
    private int tripDay;
    private String subTripStory;
    private Time startTime;
    private Time arrivalTime;
    private boolean isRanked;

    public SubTrip(SubTrip subTrip, int day) {
        this.stations = subTrip.getRoute();
        this.cost = subTrip.getCost();
        this.requiredFuel = subTrip.getRequiredFuel();
        this.trip = subTrip.getTrip();
        this.tripID = subTrip.getTripID();
        this.tripDay = day;
        this.subTripStory = subTrip.getSubTripStory();
        this.startTime = subTrip.getStartTime();
        this.arrivalTime = subTrip.getArrivalTime();
        this.isRanked = subTrip.getIsRanked();
    }

    public boolean getIsRanked() {
        return isRanked;
    }

    public SubTrip(SubTrip subTrip) {
        this.stations = subTrip.getRoute();
        this.cost = subTrip.getCost();
        this.requiredFuel = subTrip.getRequiredFuel();
        this.trip = subTrip.getTrip();
        this.tripID = subTrip.getTripID();
        this.subTripStory = subTrip.getSubTripStory();
        this.startTime = subTrip.getStartTime();
        this.arrivalTime = subTrip.getArrivalTime();
        this.isRanked = subTrip.getIsRanked();
    }

    public SubTrip(TripSuggest tripsuggest, Station station, Station station1, int closestDayFromAbove) {
        this.trip = tripsuggest;
        stations = new LinkedList<>();
        stations.add(station);
        stations.add(station1);
        this.tripDay = closestDayFromAbove;
        this.isRanked = false;
    }

    public void buildSubTripStory() {
        StringBuilder str = new StringBuilder();
        str.append(String.format("Go up to %s's car in station %s\n", trip.getTripOwnerName(), stations.getFirst().getName()));
        str.append(String.format("Go down from %s's car in station %s\n",trip.getTripOwnerName(), stations.getLast().getName()));

        this.subTripStory = str.toString();
    }

    public Station getLastStation() {
        return stations.getLast();
    }

    public Station getFirstStation() {
        return stations.getFirst();
    }

    public int getCost() {
        return cost;
    }

    public int getRequiredFuel() {
        return requiredFuel;
    }

    public TripSuggest getTrip() {
        return trip;
    }

    public LinkedList<Station> getRoute() {
        return stations;
    }

    public void setEndStationInRoute(Station last, int closestDayFromAbove) {
        Station station = new Station(last.getName());
        Time time = new Time(last.getMinutes(), last.getHour(), last.getDay() + closestDayFromAbove);
        station.setArrivalTime(time);
        stations.addLast(station);
    }

    public void setStartStationInRoute(Station first, int closestDayFromBelow) {
        Station station = new Station(first.getName());
        Time time = new Time(first.getMinutes(), first.getHour(), first.getDay() + closestDayFromBelow);
        station.setArrivalTime(time);
        stations.addFirst(station);
    }

    public int getTripID() {
        return tripID;
    }

    public String getSubTripStory() {
        return subTripStory;
    }

    public void calcCost() {
        this.cost = TripsUtil.calcCost(trip.getPpk(), stations, map);
    }

    public void calcRequiredFuel() {
        this.requiredFuel = TripsUtil.calcRequiredFuel(stations, map);
    }

    public void calcStartArrivalTime() {
        this.startTime = stations.getFirst().getTime();
        this.arrivalTime = stations.getLast().getTime();
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setIsRanked(boolean val) {
        this.isRanked = val;
    }

}

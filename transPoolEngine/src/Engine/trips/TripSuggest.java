package Engine.trips;


import Engine.Utils.TripsUtil;
import Engine.manager.EngineManager;
import Engine.matching.RoadTrip;
import Engine.matching.Station;
import Engine.matching.SubTrip;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;
import prevEngine.java.Engine.TripSuggestUtil.TripSuggestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripSuggest {
    private int suggestID;
    private String TripOwnerName;
    private Route tripRoute;
    private RecurrencesTypes recurrencesType;
    private int ppk;
    private int staticCapacity;
    private int tripPrice;
    private DriverRating driverRating;
    private List<Integer> passengers;
    private int requiredFuel;
    private Time startingTime;
    private Time arrivalTime;
    private int startingDay;
    private Station[] stations;
    private Map<Integer,Integer> capacityPerTime;
    private MapDescriptor mapDescriptor;
    private List<String> liveParticipantsTripName;

    public TripSuggest(String ownerName, Route route, int minutes, int hour, int day, int recurrencesType, int ppk, int driverCapacity) {
        this.TripOwnerName = ownerName;
        this.tripRoute = route;
        handleRoute();
        this.setTripScheduleTypeByInt(recurrencesType);
        this.ppk = ppk;
        this.startingTime = new Time(minutes, hour, day);
        this.startingDay = day;
        this.staticCapacity = driverCapacity;
        this.tripPrice = TripsUtil.calculateTripPrice(ppk, route, mapDescriptor);
        this.passengers = new ArrayList<>();
        this.requiredFuel = TripsUtil.calcRequiredFuel(route, mapDescriptor);
        this.driverRating = new DriverRating();
        this.arrivalTime = TripsUtil.calcArrivalHour(route.getPath(), mapDescriptor, startingTime);
        calcStationsArrivalHour();
        this.liveParticipantsTripName = new ArrayList<>();
        this.capacityPerTime = new HashMap<>();
    }

    public Map<Integer,Integer> getCapacityPerTime() {
        return capacityPerTime;
    }

    private int getTotalMinutesToTime(Time currTime) {
        int day = currTime.getDay();
        int hours = currTime.getHours();
        int minutes = currTime.getMinutes();

        return (day * 24 * 60) + (hours * 60) + (minutes);
    }

    private void calcStationsArrivalHour() {
        for(int i = 0; i < stations.length; i++) {
            TripsUtil.calcArrivalHourToSpecificStation(stations, stations[i], mapDescriptor, startingTime);
        }
    }

    private void handleRoute() {
        String[] elements = tripRoute.getPath().split(",");
        stations = new Station[elements.length];

        for(int i =0; i < stations.length; i++) {
            stations[i] = new Station(elements[i]);
        }
    }

    public int getPpk() {
        return ppk;
    }

    public List<Integer> getPassengers() {
        return passengers;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public String getTripOwnerName() {
        return TripOwnerName;
    }

    public Route getTripRoute() {
        return tripRoute;
    }

    public int getStaticCapacity() {
        return staticCapacity;
    }

    public int getRequiredFuel() {
        return requiredFuel;
    }

    public int getSuggestID() {
        return suggestID;
    }

    public void setSuggestID(int suggestIDID) {
        this.suggestID = suggestIDID;
    }

    public Station getFirstStation() {
        return stations[0];
    }

    public Station getLastStation() {
        return stations[stations.length - 1];
    }

    public enum RecurrencesTypes
    {
        ONE_TIME_ONLY(0),
        DAILY(1),
        BI_DAILY(2),
        WEEKLY(7),
        MONTHLY(30);

        private final int value;
        RecurrencesTypes(int id) { this.value = id; }
        public int getValue() { return value; }

        public String getTripScheduleTypeString(){
            return this.toString();
        }
    }

    private void setTripScheduleTypeByInt(int tripScheduleType) {
        switch (tripScheduleType) {
            case 1:
                this.recurrencesType = RecurrencesTypes.ONE_TIME_ONLY;
                break;
            case 2:
                this.recurrencesType = RecurrencesTypes.DAILY;
                break;
            case 3:
                this.recurrencesType = RecurrencesTypes.BI_DAILY;
                break;
            case 4:
                this.recurrencesType = RecurrencesTypes.WEEKLY;
                break;
            case 5:
                this.recurrencesType = RecurrencesTypes.MONTHLY;
                break;
        }
    }

    public Time getArrivalTimeToStation(Station stationName) {
        for(int i = 0; i < stations.length; i++) {
            if(stationName.equals(stations[i])) {
                return stations[i].getTime();
            }
        }
        return null;
    }

    public void addRatingToDriver(int rating) {
        driverRating.addOneToNumOfRatings();
        driverRating.addRatingToDriver(rating);
    }

    public void addRatingToDriver(int rating, String literalRating) {
        driverRating.addRatingToDriver(rating, literalRating);
    }

    public DriverRating getDriverRating() {
        return driverRating;
    }

    public RecurrencesTypes getRecurrencesType() {
        return recurrencesType;
    }

    public Time getStartingTime() {
        return startingTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public int getStartingDay() {
        return startingDay;
    }

    public Station[] getRide() {
        return stations;
    }

    public Station[] getTripStations() {
        return stations;
    }

}

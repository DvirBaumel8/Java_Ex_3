package engine.matching;


import engine.trips.Time;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoadTrip {
    private LinkedList<SubTrip> completeTrip;
    private int totalCost;
    private double requiredFuel;
    private Time startTime;
    private Time arrivalTime;
    private String RoadStory;
    private TripRequest tripRequest;

    public RoadTrip() {
        completeTrip = new LinkedList<>();
    }

    public RoadTrip(RoadTrip roadTrip) {
        this.completeTrip = roadTrip.getSubTrips();
        this.totalCost = getTotalCost();
        this.requiredFuel = roadTrip.getRequiredFuel();
        this.startTime = roadTrip.getStartTime();
        this.arrivalTime = roadTrip.getArrivalTime();
        this.RoadStory = roadTrip.getRoadStory();
        this.tripRequest = roadTrip.getTripRequest();
    }

    public TripRequest getTripRequest() {
        return tripRequest;
    }

    public void setTripRequest(TripRequest tripRequest) {
        this.tripRequest = tripRequest;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public double getRequiredFuel() {
        return requiredFuel;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public String getRoadStory() {
        return RoadStory;
    }

    public LinkedList<SubTrip> getSubTrips() {
        return completeTrip;
    }

    public void addSubTripToRoadTrip(SubTrip subTrip) {
        this.completeTrip.add(subTrip);
    }

    public void calcTotalCost() {
        int cost = 0;
        for (SubTrip subTrip : completeTrip) {
            cost += subTrip.getCost();
        }

        this.totalCost = cost;
    }

    public void calcRequiredFuel() {
        double fuel = 0;
        for (SubTrip subTrip : completeTrip) {
            fuel += subTrip.getRequiredFuel();
        }

        this.requiredFuel = fuel;
    }

    public void calcStartArrivalTime() {
        this.startTime = completeTrip.getFirst().getStartTime();
        this.arrivalTime = completeTrip.getLast().getArrivalTime();
    }

    public void buildRoadTripStory() {
        StringBuilder str = new StringBuilder();
        str.append("Road Story:\n");
        for(SubTrip subTrip : completeTrip) {
            str.append(subTrip.getSubTripStory());
        }
        str.append(String.format("Total trip cost: %d\n",this.totalCost));
        str.append(String.format("Total required fuel: %f\n", this.requiredFuel));
        if(tripRequest.isRequestByStartTime()) {
            str.append(String.format("Arrival time: %s", arrivalTime.toString()));
        }
        else {
            str.append(String.format("Starting time: %s", startTime.toString()));
        }

        this.RoadStory = str.toString();
    }

    public Station getLastStation() {
        return completeTrip.getLast().getLastStation();
    }

    public Station getFirstStation() {
        return completeTrip.getFirst().getFirstStation();
    }

    public List<String> getAllSubTripsDriversNamesStillNotRanked() {
        List<String> ids = new ArrayList<>();
        for(SubTrip subTrip : completeTrip) {
            if(!subTrip.getIsRanked()) {
                ids.add(String.valueOf(subTrip.getTrip().getSuggestID()));
            }
        }
        return ids;
    }

    public List<TripSuggest> getAllTripSuggests() {
        List<TripSuggest> suggests = new ArrayList<>();
        for(SubTrip subTrip : completeTrip) {
            suggests.add(subTrip.getTrip());
        }
        return suggests;
    }

}

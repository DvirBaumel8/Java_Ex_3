package engine.matching;


import engine.trips.Time;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.LinkedList;
import java.util.List;

public class MatchUtil {

    public LinkedList<LinkedList<SubTrip>> findPotentialMatches(TripRequest request, int numberOfTripsToOffer, List<TripSuggest> tripSuggests) {//By departure or arrival
        if (request.isRequestByStartTime())
            return collectMatchingTripsByDepartureTime(request, numberOfTripsToOffer, tripSuggests);
        else
            return collectMatchingTripsByArrivalTime(request, numberOfTripsToOffer, tripSuggests);
    }

    private LinkedList<LinkedList<SubTrip>> collectMatchingTripsByDepartureTime(TripRequest request, int numberOfTripsToOffer, List<TripSuggest> suggests) {

        int index;
        LinkedList<SubTrip> subTrips = new LinkedList<>();
        LinkedList<LinkedList<SubTrip>> res = new LinkedList<>();

        for (TripSuggest suggest : suggests) {
            for (index = 0; index < suggest.getRide().length - 1; index++) {
                if (suggest.getRide()[index].getName().equals(request.getSourceStation()) && isTripTimeEqual(request.getStartTime().getDay(), request.getStartTime().getHours(), request.getStartTime().getMinutes(), suggest.getRide()[index], suggest)) {
                    subTrips.add(new SubTrip(suggest, suggest.getRide()[index], suggest.getRide()[index + 1], findClosestDayFromAbove(suggest, suggest.getRecurrencesType().getValue(), suggest.getRide()[index], request.getStartTime().getDay(), request.getStartTime().getHours(), request.getStartTime().getMinutes()), suggest.getMapDescriptor()));
                    buildMatchingTripsByDeparture(subTrips, request, res, numberOfTripsToOffer, suggests);
                    break;
                }
            }
        }
        return res;
    }

    private void buildMatchingTripsByDeparture(LinkedList<SubTrip> subTrips, TripRequest request, LinkedList<LinkedList<SubTrip>> res, int numberOfTripsToOffer, List<TripSuggest> suggests) {

        if (res.size() == numberOfTripsToOffer)
            return;

        Station current = subTrips.getLast().getLastStation();
        if (request.getDestinationStation().equals(current.getName())) {
            res.add(copyLinkedList(subTrips));

        }
        else {
            LinkedList<SubTrip> matchingRides = findTripsForNextStations(subTrips, suggests);
            for (SubTrip subTrip : matchingRides) {
                LinkedList<SubTrip> newRide = copyLinkedList(subTrips);
                if (subTrip.getTrip().getSuggestID() == subTrips.getLast().getTrip().getSuggestID()) {
                    newRide.getLast().setEndStationInRoute(subTrip.getRoute().getLast(), findClosestDayFromAbove(subTrip.getTrip(), subTrip.getTrip().getRecurrencesType().getValue(), subTrip.getRoute().getFirst(), newRide.getLast().getRoute().getLast().getDay(), newRide.getLast().getLastStation().getHour(), newRide.getLast().getLastStation().getMinutes()));
                    buildMatchingTripsByDeparture(copyLinkedList(newRide), request, res, numberOfTripsToOffer, suggests);
                    newRide.getLast().getRoute().removeLast();
                } else {
                    newRide.add(new SubTrip(subTrip, findClosestDayFromAbove(subTrip.getTrip(), subTrip.getTrip().getRecurrencesType().getValue(), subTrip.getFirstStation(), newRide.getLast().getLastStation().getDay(), newRide.getLast().getLastStation().getHour(), newRide.getLast().getLastStation().getMinutes())));
                    buildMatchingTripsByDeparture(copyLinkedList(newRide), request, res, numberOfTripsToOffer, suggests);
                    newRide.removeLast();
                }
            }
        }
    }

    private LinkedList<SubTrip> copyLinkedList(LinkedList<SubTrip> ride) {
        LinkedList newList = new LinkedList<SubTrip>();
        for (SubTrip subTrip : ride) {
            newList.add(new SubTrip(subTrip));
        }
        return newList;
    }


    public LinkedList<SubTrip> findTripsForNextStations(LinkedList<SubTrip> ride, List<TripSuggest> suggests) {

        LinkedList<SubTrip> subTrips = new LinkedList<>();
        Station currentStation = ride.getLast().getRoute().getLast();
        int index;
        for (TripSuggest suggest : suggests) {
            for (index = 0; index < suggest.getRide().length - 1; index++) {
                if (suggest.getRide()[index].getName().equals(currentStation.getName()) &&
                        isTripTimeBigger(currentStation, suggest.getRide()[index], suggest) &&
                        isTheStationExist(ride, suggest.getRide()[index + 1])) {
                    subTrips.add(new SubTrip(suggest, suggest.getRide()[index], suggest.getRide()[index + 1], findClosestDayFromAbove(suggest, suggest.getRecurrencesType().getValue(), suggest.getRide()[index], ride.getLast().getLastStation().getDay(), ride.getLast().getLastStation().getHour(), ride.getLast().getLastStation().getMinutes()), suggest.getMapDescriptor()));
                }
            }
        }
        return subTrips;
    }

    private boolean isTheStationExist(LinkedList<SubTrip> ride, Station station) {
        for (SubTrip mr : ride) {
            for (Station st : mr.getRoute()) {
                if (st.getName().equals(station.getName()))
                    return false;
            }
        }
        return true;
    }

    private boolean isTripTimeEqual(int day, int hour, int minutes, Station tripStation, TripSuggest trip) {
        int recurrences = trip.getRecurrencesType().getValue();
        if(recurrences == 0) {
            Time startingTime = trip.getStartingTime();
            if(day == startingTime.getDay() && hour == startingTime.getHours() && minutes == startingTime.getMinutes()) {
                return true;
            }
        }
        else if (tripStation.getDay() <= day) {
            if ((tripStation.getDay() * 24 * 60) + (tripStation.getHour() * 60) + (tripStation.getMinutes()) < ((day * 60 * 24) + (hour * 60) + (minutes)) && trip.getRecurrencesType().equals("ONE_TIME_ONLY")) {
                return false;
            }
            else if ((tripStation.getDay() % recurrences) == day % recurrences && tripStation.getHour() == hour && tripStation.getMinutes() == minutes && (!trip.getCapacityPerTime().containsKey("" + day + hour + minutes) || (trip.getCapacityPerTime().containsKey("" + day + hour + minutes) && trip.getCapacityPerTime().get("" + day + hour + minutes) > 0))) {
                return true;
            }

        }
        return false;
    }
    /*
     int recurrences = trip.getRecurrencesType().getValue();
        if(recurrences == 0) {
            Time startingTime = trip.getStartingTime();
            if(day == startingTime.getDay() && hour == startingTime.getHours() && minutes == startingTime.getMinutes()) {
                return true;
            }
        }
        else {
            if (tripStation.getDay() <= day) {
                if ((tripStation.getDay() * 24 * 60) + (tripStation.getHour() * 60) + (tripStation.getMinutes()) < ((day * 60 * 24) + (hour * 60) + (minutes)) && trip.getRecurrencesType().equals("ONE_TIME_ONLY"))
                    return false;
                if ((tripStation.getDay() % recurrences) == day % recurrences &&
                        tripStation.getHour() == hour &&
                        tripStation.getMinutes() == minutes &&
                        (!trip.getCapacityPerTime().containsKey("" + day + hour + minutes) ||
                                (trip.getCapacityPerTime().containsKey("" + day + hour + minutes) &&
                                        trip.getCapacityPerTime().get("" + day + hour + minutes) > 0)))
                    return true;
            }
            return false;
        }
     */

    private boolean isTripTimeBigger(Station requestStation, Station tripStation, TripSuggest trip) {
        if (requestStation.getDay() > tripStation.getDay() && trip.getRecurrencesType().equals("ONE_TIME_ONLY"))
            return false;
        else if (trip.getRecurrencesType().equals("ONE_TIME_ONLY") && (trip.getCapacityPerTime().containsKey("" + trip.getStartingTime().getDay() + trip.getStartingTime().getHours() + trip.getStartingTime().getMinutes()) && trip.getCapacityPerTime().get("" + trip.getStartingTime().getDay() + trip.getStartingTime().getHours() + trip.getStartingTime().getMinutes()) == 0))
            return false;
        else
            return true;
    }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~arrival~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    private LinkedList<LinkedList<SubTrip>> collectMatchingTripsByArrivalTime(TripRequest request, int numberOfTripsToOffer, List<TripSuggest> suggests) {

        LinkedList<SubTrip> combinedTrip = new LinkedList<>();
        LinkedList<LinkedList<SubTrip>> res = new LinkedList<>(); // save all the optional result of combinedTrips

        for (TripSuggest suggest : suggests) {
            for (int index = suggest.getRide().length - 1; index > 0; index--) {
                if (isTripTimeEqual(request.getArrivalTime().getDay(), request.getArrivalTime().getHours(), request.getArrivalTime().getMinutes(), suggest.getRide()[index], suggest)) {
                    combinedTrip.add(new SubTrip(suggest, suggest.getRide()[index - 1], suggest.getRide()[index], findClosestDayFromBelow(suggest, suggest.getRecurrencesType().getValue(), suggest.getRide()[index], request.getArrivalTime().getDay(), request.getArrivalTime().getHours(), request.getArrivalTime().getMinutes()), suggest.getMapDescriptor()));
                    buildMatchingTripsByArrival(combinedTrip, request, res, numberOfTripsToOffer, suggests); //send them to a rec function to find all optional trips
                    break;
                }
            }
        }
        return res;
    }

    private void buildMatchingTripsByArrival(LinkedList<SubTrip> ride, TripRequest request, LinkedList<LinkedList<SubTrip>> res, int numberOfTripsToOffer, List<TripSuggest> suggests) {

        if (res.size() == numberOfTripsToOffer)
            return;

        Station current = ride.getFirst().getRoute().getFirst();
        if (request.getSourceStation().equals(current.getName())) { // if we in the destination
            res.add(ride);
        } else {
            LinkedList<SubTrip> matchingRides = findTripsForPrevStations(ride, suggests); //gating list of optional Plaid drive
            for (SubTrip subTrip : matchingRides) {
                LinkedList<SubTrip> newRide = copyLinkedList(ride);
                if (subTrip.getTrip().getSuggestID() == ride.getFirst().getTrip().getSuggestID()) {// if its the same drive just chang the end station
                    newRide.getFirst().setStartStationInRoute(subTrip.getRoute().getFirst(), findClosestDayFromBelow(subTrip.getTrip(), subTrip.getTrip().getRecurrencesType().getValue(), subTrip.getRoute().getLast(), newRide.getLast().getRoute().getFirst().getDay(), newRide.getLast().getRoute().getFirst().getHour(), newRide.getLast().getRoute().getFirst().getMinutes()));
                    buildMatchingTripsByArrival(newRide, request, res, numberOfTripsToOffer, suggests);
                } else {
                    newRide.addFirst(new SubTrip(subTrip, findClosestDayFromBelow(subTrip.getTrip(), subTrip.getTrip().getRecurrencesType().getValue(), subTrip.getRoute().getLast(), newRide.getFirst().getRoute().getFirst().getDay(), newRide.getFirst().getRoute().getFirst().getHour(), newRide.getFirst().getRoute().getFirst().getMinutes())));
                    buildMatchingTripsByArrival(copyLinkedList(newRide), request, res, numberOfTripsToOffer, suggests);
                    newRide.removeFirst();
                }
            }
        }
    }

    private LinkedList<SubTrip> findTripsForPrevStations(LinkedList<SubTrip> ride, List<TripSuggest> suggests) {

        LinkedList<SubTrip> matchingRides = new LinkedList<>();
        Station current = ride.getFirst().getRoute().getFirst();
        for (TripSuggest suggest : suggests) {
            for (int index = suggest.getRide().length - 1; index > 0; index--) {  // 0<i because if it is the lest station it cant continue withe driver
                if (suggest.getRide()[index].getName().equals(current.getName()) &&
                        isTripTimeSmaller(suggest, current,suggest.getRide()[index]) &&
                        isTheStationExist(ride, suggest.getRide()[index - 1]));
                    matchingRides.add(new SubTrip(suggest, suggest.getRide()[index - 1], suggest.getRide()[index], findClosestDayFromBelow(suggest, suggest.getRecurrencesType().getValue(), suggest.getRide()[index], ride.getFirst().getRoute().getFirst().getDay(), ride.getFirst().getRoute().getFirst().getHour(), ride.getFirst().getRoute().getFirst().getMinutes()), suggest.getMapDescriptor()));
            }

        }
        return matchingRides;
    }

    private boolean isTripTimeSmaller(TripSuggest trip, Station current, Station station) {
        int newDay = station.getDay();
        while (((current.getDay() * 24 * 60) + (current.getHour() * 60) + (current.getMinutes())) >= ((newDay * 24 * 60) + (station.getHour() * 60) + (station.getMinutes()))) {
            if ((!trip.getCapacityPerTime().containsKey("" + newDay + station.getHour() + station.getMinutes())) ||
                    (trip.getCapacityPerTime().containsKey("" + newDay + station.getHour() + station.getMinutes()) && trip.getCapacityPerTime().get("" + newDay + station.getHour() + station.getMinutes()) > 0))
                return true;
            else if (trip.getRecurrencesType().equals("ONE_TIME_ONLY"))
                return false;
            else
                newDay += trip.getRecurrencesType().getValue();
        }
        return false;
    }

    private int findClosestDayFromAbove(TripSuggest trip, int recurrences, Station station, int day, int hour, int minutes) {
        int newDay = station.getDay();
        int time = (newDay * 24 * 60) + (station.getHour() * 60) + (station.getMinutes());
        while (time < ((day * 24 * 60) + (hour * 60) + (minutes))) {
            newDay += recurrences;
            time = newDay * 24 * 60 + station.getHour() * 60 + station.getMinutes();
        }
        while (trip.getCapacityPerTime().containsKey("" + newDay + station.getHour() + station.getMinutes()) && trip.getCapacityPerTime().get("" + newDay + station.getHour() + station.getMinutes()) == 0)
            newDay += recurrences;

        return newDay - station.getDay();
    }

    private int findClosestDayFromBelow(TripSuggest trip, int recurrences, Station station, int day, int hour, int minutes) {
        int newDay = station.getDay();
        int time = (newDay * 24 * 60) + (station.getHour() * 60) + (station.getMinutes());

        if (trip.getRecurrencesType().equals("ONE_TIME_ONLY"))
            return 0;

        while (time <= ((day * 24 * 60) + (hour * 60) + (minutes))) {
            newDay += recurrences;
            time = newDay * 24 * 60 + station.getHour() * 60 + station.getMinutes();
        }
        while (trip.getCapacityPerTime().containsKey("" + newDay + station.getHour() + station.getMinutes()) && trip.getCapacityPerTime().get("" + newDay + station.getHour() + station.getMinutes()) == 0)
            newDay -= recurrences;

        if (time > station.getDay() * 24 * 60 + station.getHour() * 60 + station.getMinutes())
            return (newDay - recurrences) - station.getDay();
        else
            return 0;
    }

}

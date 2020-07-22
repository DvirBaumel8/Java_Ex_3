package prevEngine.java.Engine.tripSuggestUtil;


import engine.trips.TripSuggest;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPoolTrip;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TripSuggestUtil {
    private int nextSuggestID;
    private Map<Integer, TripSuggest> suggestedTrips;

    private Map<String, List<String>> suggestedTripsDto = new HashMap<>();
    static final int INPUT_ADD_TRIP_SUGGEST_SIZE = 9;


    public TripSuggestUtil() {
        this.nextSuggestID = 1;
        this.suggestedTrips = new HashMap<>();
    }

    public void convertPlannedTripsToSuggestedTrips(List<TransPoolTrip> plannedTrips) {
        for (TransPoolTrip trip : plannedTrips) {
            int ppk = trip.getPPK();
            TripSuggest tripSuggest = new TripSuggest(trip.getOwner(), trip.getRoute(), 0, trip.getScheduling().getHourStart(), trip.getScheduling().getDayStart(), getTripScheduleTypeInt(trip.getScheduling().getRecurrences()), ppk, trip.getCapacity());
            addSuggestTrip(tripSuggest);
        }
    }

    public void addSuggestTrip(TripSuggest suggestTrip) {
        suggestTrip.setSuggestID(nextSuggestID);
        suggestedTrips.put(nextSuggestID, suggestTrip);
        nextSuggestID++;
    }

    public Map<Integer, TripSuggest> getAllSuggestedTrips() {
        return suggestedTrips;
    }

    public void restSuggestedTripsId() {
        this.nextSuggestID = 1;
    }

    int getTripScheduleTypeInt(String tripScheduleType) {
        int res = 0;
        switch (tripScheduleType) {
            case "OneTime":
                res = 1;
                break;
            case "Daily":
                res = 2;
                break;
            case "BiDaily":
                res = 3;
                break;
            case "Weekly":
                res = 4;
                break;
            case "Monthly":
                res = 5;
                break;
        }

        return res;
    }



    public TripSuggest getTripSuggestByID(int suggestID) {
        return suggestedTrips.get(suggestID);
    }


    //-----------------------------------Ranking Func------------------------------------------


    public void addSuggestedTripsDto(String[] displayTripSuggestStrArr) {
        List<String> suggestedTripsDtoObj = new LinkedList<>();

        for (int i = 0; i < INPUT_ADD_TRIP_SUGGEST_SIZE; i++) {
            suggestedTripsDtoObj.add(displayTripSuggestStrArr[i]);
        }
        suggestedTripsDto.put(String.valueOf(nextSuggestID - 1), suggestedTripsDtoObj);
    }

    public Map<String, List<String>> getSuggestedTripsDto() {
        return suggestedTripsDto;
    }

    public void setNumOfRanksInTripSuggestMapDto(String tripSuggestId) {
        List<String> suggestedTripDto = suggestedTripsDto.get(tripSuggestId);
        StringBuilder numOfRank = new StringBuilder(suggestedTripDto.get(7));
        Character resNumOfRank = numOfRank.charAt(16);
        int intNumOfRank = Integer.valueOf(resNumOfRank.toString());
        intNumOfRank++;
        char charNumOfRanks = (char) (intNumOfRank + '0');
        numOfRank.setCharAt(16, charNumOfRanks);
        suggestedTripDto.set(7, numOfRank.toString());
        suggestedTripsDto.put(tripSuggestId, suggestedTripDto);
        // "-Average Rank:X";
        // = "-Num of ranking:0";
        //= "-Comments:";
    }

    public void setAverageInTripSuggestMapDto(String currRank, String tripSuggestId) {
        List<String> suggestedTripDto = suggestedTripsDto.get(tripSuggestId);
        StringBuilder rank = new StringBuilder(suggestedTripDto.get(6));
        Character resRank = rank.charAt(14);
        int intRank = 0;
        if (resRank >= '0' && resRank <= '5') {
            intRank = Integer.valueOf(resRank.toString());
        } else {
            intRank = Integer.parseInt(currRank);
        }

        StringBuilder numOfRank = new StringBuilder(suggestedTripDto.get(7));
        Character resNumOfRank = numOfRank.charAt(16);
        int intNumOfRankBefore = Integer.valueOf(resNumOfRank.toString()) - 1;
        int ranksBefore = intRank * intNumOfRankBefore;
        intRank = (ranksBefore + Integer.valueOf(currRank)) / (intNumOfRankBefore + 1);

        char charNewResRank = (char) (intRank + '0');
        rank.setCharAt(14, charNewResRank);
        suggestedTripDto.set(6, rank.toString());
        suggestedTripsDto.put(tripSuggestId, suggestedTripDto);
    }

    public void setAverageReviewsInTripSuggestMapDto(String newReview, String tripSuggestId) {
        List<String> suggestedTripDto = suggestedTripsDto.get(tripSuggestId);
        StringBuilder newReviewSb = new StringBuilder(suggestedTripDto.get(8));
        newReviewSb.append(newReview + ",");
        suggestedTripDto.set(8, newReviewSb.toString());
        suggestedTripsDto.put(tripSuggestId, suggestedTripDto);
    }

    public List<String> getTripSuggestDtoObj(String suggestId) {
        return suggestedTripsDto.get(suggestId);
    }

    public String getStringRoadTripOfSuggestedTrip(String suggestId) {
        TripSuggest currTripSuggest = this.suggestedTrips.get(Integer.parseInt(suggestId));
        Route route = currTripSuggest.getTripRoute();
        String res = route.getPath();
        res = res.replace(',','-');
        return res;

    }
}


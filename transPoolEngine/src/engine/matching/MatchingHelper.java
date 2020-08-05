package engine.matching;

import engine.dto.mapPage.PotentialRoadTripDto;
import engine.trips.TripRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MatchingHelper {

    public static List<PotentialRoadTripDto> convertToStr(List<RoadTrip> potentialCacheList, TripRequest tripRequest) {
        List<PotentialRoadTripDto> potentialRoadTripsStr = new ArrayList<>();
        int index = 0;
        for (RoadTrip roadTrip : potentialCacheList) {
            index++;
            if (tripRequest.isRequestByStartTime()) {
                potentialRoadTripsStr.add(new PotentialRoadTripDto(index, roadTrip.getRoadStory()));
            } else {
                potentialRoadTripsStr.add(new PotentialRoadTripDto(index, roadTrip.getRoadStory()));
            }
        }
        return potentialRoadTripsStr;
    }

    public static void updateSubTripsValues(LinkedList<LinkedList<SubTrip>> potentialRoadTrips) {
        for(LinkedList<SubTrip> subTrips : potentialRoadTrips) {
            for(SubTrip subTrip : subTrips) {
                subTrip.calcCost();
                subTrip.calcRequiredFuel();
                subTrip.buildSubTripStory();
                subTrip.calcStartArrivalTime();
            }
        }
    }

    public static List<RoadTrip> convertTwoLinkedListToOneRoadTripLinkedList(LinkedList<LinkedList<SubTrip>> potentialRoadTrips, TripRequest request) {
        LinkedList<RoadTrip> roadTrips = new LinkedList<>();
        for(LinkedList<SubTrip> roadTrip : potentialRoadTrips) {
            roadTrips.add(createRoadTripFromLinkListSubTrips(roadTrip, request));
        }

        return roadTrips;
    }

    public static RoadTrip createRoadTripFromLinkListSubTrips(LinkedList<SubTrip> subRoadTrips, TripRequest request) {
        RoadTrip roadTrip = new RoadTrip();
        roadTrip.setTripRequest(request);
        for(SubTrip subTrip : subRoadTrips) {
            roadTrip.addSubTripToRoadTrip(subTrip);
        }
        roadTrip.calcRequiredFuel();
        roadTrip.calcTotalCost();
        roadTrip.calcStartArrivalTime();
        roadTrip.buildRoadTripStory();

        return roadTrip;
    }
}

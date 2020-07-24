package engine.notifications;

import engine.dto.MapsTableElementDetailsDto;

public class MatchNotificationsDetails {
    private MapsTableElementDetailsDto mapsTableElementDetails;
    private Integer matchedRequestId;
    private double tripCost;

    public MatchNotificationsDetails(MapsTableElementDetailsDto mapsTableElementDetails, Integer matchedRequestId, double tripCost) {
        this.mapsTableElementDetails = mapsTableElementDetails;
        this.matchedRequestId = matchedRequestId;
        this.tripCost = tripCost;
    }
}

package engine.notifications;

import engine.maps.MapsTableElementDetails;

public class MatchNotificationsDetails {
    private MapsTableElementDetails mapsTableElementDetails;
    private Integer matchedRequestId;
    private double tripCost;

    public MatchNotificationsDetails(MapsTableElementDetails mapsTableElementDetails, Integer matchedRequestId, double tripCost) {
        this.mapsTableElementDetails = mapsTableElementDetails;
        this.matchedRequestId = matchedRequestId;
        this.tripCost = tripCost;
    }
}

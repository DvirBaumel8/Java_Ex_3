package Engine.Notifications;

import Engine.maps.MapsTableElementDetails;

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

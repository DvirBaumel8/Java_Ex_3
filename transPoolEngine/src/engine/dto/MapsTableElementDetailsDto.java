package engine.dto;

import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

public class MapsTableElementDetailsDto {
    private String userName;
    private String mapName;
    private int stationsQuantity;
    private int roadsQuantity;
    private int tripSuggestsQuantity;
    private int tripRequestQuantity;
    private int matchedTripRequestQuantity;

    public MapsTableElementDetailsDto(MapDescriptor mapDescriptor, String userNameOwner, String mapName) {
        this.userName = userNameOwner;
        this.mapName = mapName;
        stationsQuantity = mapDescriptor.getStops().getStop().size();
        roadsQuantity = mapDescriptor.getPaths().getPath().size();
        tripSuggestsQuantity = 0;
        tripRequestQuantity = 0;
        matchedTripRequestQuantity = 0;
    }

    public MapsTableElementDetailsDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getStationsQuantity() {
        return stationsQuantity;
    }

    public void setStationsQuantity(int stationsQuantity) {
        this.stationsQuantity = stationsQuantity;
    }

    public int getRoadsQuantity() {
        return roadsQuantity;
    }

    public void setRoadsQuantity(int roadsQuantity) {
        this.roadsQuantity = roadsQuantity;
    }

    public int getTripSuggestsQuantity() {
        return tripSuggestsQuantity;
    }

    public void setTripSuggestsQuantity(int tripSuggestsQuantity) {
        this.tripSuggestsQuantity = tripSuggestsQuantity;
    }

    public int getTripRequestQuantity() {
        return tripRequestQuantity;
    }

    public void setTripRequestQuantity(int tripRequestQuantity) {
        this.tripRequestQuantity = tripRequestQuantity;
    }

    public int getMatchedTripRequestQuantity() {
        return matchedTripRequestQuantity;
    }

    public void setMatchedTripRequestQuantity(int matchedTripRequestQuantity) {
        this.matchedTripRequestQuantity = matchedTripRequestQuantity;
    }

    public void addTripRequest() {
        tripRequestQuantity++;
    }

    public void addTripSuggest() {
        tripSuggestsQuantity++;
    }

    public void addMatchTripRequest() {
        matchedTripRequestQuantity++;
    }
}


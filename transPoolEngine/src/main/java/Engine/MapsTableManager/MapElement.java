package Engine.MapsTableManager;

public class MapElement {
    private String userNameOwner;
    private String mapName;
    private int stationsQuantity;
    private int roadsQuantity;
    private int tripSuggestsQuantity;
    private int tripRequestQuantity;
    private int matchedTripRequestQuantity;

    public String getUserNameOwner() {
        return userNameOwner;
    }

    public void setUserNameOwner(String userNameOwner) {
        this.userNameOwner = userNameOwner;
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
}

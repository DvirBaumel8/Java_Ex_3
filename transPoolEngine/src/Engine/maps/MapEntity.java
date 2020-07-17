package Engine.maps;

import Engine.trips.TripRequest;
import Engine.trips.TripSuggest;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MapEntity {
    private MapsTableElementDetails mapsTableElementDetails;
    private List<TripRequest> tripRequests;
    private List<TripSuggest> tripSuggests;
    //private Graph graph;

    public MapEntity(MapDescriptor mapDescriptor, String userName, String mapName) {
        tripRequests = new ArrayList<>();
        tripSuggests = new ArrayList<>();
        mapsTableElementDetails = new MapsTableElementDetails(mapDescriptor, userName, mapName);
    }

    public MapsTableElementDetails getMapsTableElementDetails() {
        return mapsTableElementDetails;
    }

    public void addNewTripRequest(TripRequest tripRequest) {
        tripRequests.add(tripRequest);
    }

    public void addNewTripSuggest(TripSuggest tripSuggest) {
        tripSuggests.add(tripSuggest);
    }
}

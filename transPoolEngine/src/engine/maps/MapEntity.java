package engine.maps;

import engine.dto.userPage.MapsTableElementDetailsDto;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapEntity {
    private MapsTableElementDetailsDto mapsTableElementDetails;
    private MapDescriptor mapDescriptor;
    private List<TripRequest> tripRequests;
    private List<TripSuggest> tripSuggests;
    private String htmlGraph;

    public MapEntity(MapDescriptor mapDescriptor, String userName, String mapName, String htmlGraph) {
        tripRequests = new ArrayList<>();
        tripSuggests = new ArrayList<>();
        this.mapDescriptor = mapDescriptor;
        mapsTableElementDetails = new MapsTableElementDetailsDto(mapDescriptor, userName, mapName);
        this.htmlGraph = htmlGraph;
    }

    public MapsTableElementDetailsDto getMapsTableElementDetails() {
        return mapsTableElementDetails;
    }

    public void addNewTripRequest(TripRequest tripRequest) {
        tripRequests.add(tripRequest);
        mapsTableElementDetails.addTripRequest();
    }

    public void addNewTripSuggest(TripSuggest tripSuggest) {
        tripSuggests.add(tripSuggest);
        mapsTableElementDetails.addTripSuggest();
    }

    public void addNewMatchTripRequest() {
        mapsTableElementDetails.addMatchTripRequest();
    }

    public HashSet<String> getAllLogicStations() {
        HashSet<String> allStations = new HashSet<>();
        for(Stop stop : mapDescriptor.getStops().getStop()) {
            allStations.add(stop.getName());
        }

        return allStations;
    }

    public List<TripRequest> getTripRequests() {
        return tripRequests;
    }

    public List<TripSuggest> getTripSuggests() {
        return tripSuggests;
    }

    public TripRequest getTripRequestById(Integer id) {
        for(TripRequest request : tripRequests) {
            if(request.getRequestID() == id) {
                return request;
            }
        }
        return null;
    }

    public TripSuggest getTripSuggestById(Integer id) {
        for(TripSuggest suggest : tripSuggests) {
            if(suggest.getSuggestID() == id) {
                return suggest;
            }
        }
        return null;
    }

    public MapDescriptor getMapDescriptor() {
        return mapDescriptor;
    }

    public String getHtmlGraph() {
        return htmlGraph;
    }
}

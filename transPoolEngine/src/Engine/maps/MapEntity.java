package Engine.maps;

import Engine.trips.TripRequest;
import Engine.trips.TripSuggest;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapEntity {
    private MapsTableElementDetails mapsTableElementDetails;
    private MapDescriptor mapDescriptor;
    private List<TripRequest> tripRequests;
    private List<TripSuggest> tripSuggests;
    //private Graph graph;

    public MapEntity(MapDescriptor mapDescriptor, String userName, String mapName) {
        tripRequests = new ArrayList<>();
        tripSuggests = new ArrayList<>();
        this.mapDescriptor = mapDescriptor;
        mapsTableElementDetails = new MapsTableElementDetails(mapDescriptor, userName, mapName);
    }

    public MapsTableElementDetails getMapsTableElementDetails() {
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
}

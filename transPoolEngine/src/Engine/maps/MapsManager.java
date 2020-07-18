package Engine.maps;

import Engine.trips.TripRequest;
import Engine.trips.TripSuggest;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MapsManager {
    private HashMap<String, MapEntity> mapNameToEntity;

    public MapsManager() {
        mapNameToEntity = new HashMap<>();
    }

    public void createNewMap(MapDescriptor mapDescriptor, String userName, String mapName) throws Exception {
        if(mapNameToEntity.containsKey(mapName)) {
            throw new Exception();
        }
        MapEntity mapEntity = new MapEntity(mapDescriptor, userName, mapName);
        mapNameToEntity.put(mapName, mapEntity);
    }

    public List<MapsTableElementDetails> getAllMapsTableElementsDetails() {
        List<MapsTableElementDetails> detailsList = new ArrayList<>();
        for(Map.Entry<String, MapEntity> entry : mapNameToEntity.entrySet()) {
            detailsList.add(entry.getValue().getMapsTableElementDetails());
        }
        return detailsList;
    }

    public void addTripRequestByMapName(String mapName, TripRequest tripRequest) {
        MapEntity mapEntity = mapNameToEntity.get(mapName);
        mapEntity.addNewTripRequest(tripRequest);
    }

    public void addTripSuggestByMapName(String mapName, TripSuggest tripSuggest) {
        MapEntity mapEntity = mapNameToEntity.get(mapName);
        mapEntity.addNewTripSuggest(tripSuggest);
    }

    public HashSet<String> getAllLogicStationsByMapName(String mapName) {
        MapEntity mapEntity = mapNameToEntity.get(mapName);
        return mapEntity.getAllLogicStations();
    }


    public TripRequest getMapTripRequestByMapNameAndRequestId(String mapName, Integer requestId) {
        return mapNameToEntity.get(mapName).getTripRequestById(requestId);
    }

    public TripSuggest getMapTripSuggestByMapNameAndSuggestId(String mapName, Integer suggestId) {
        return mapNameToEntity.get(mapName).getTripSuggestById(suggestId);
    }

    public void addMatchTripRequestToMapByMap(String mapName) {
        mapNameToEntity.get(mapName).addNewMatchTripRequest();
    }

    public MapsTableElementDetails getMapTableElementDetailsByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getMapsTableElementDetails();
    }

    public MapDescriptor getMapDescriptorByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getMapDescriptor();
    }

    public List<TripSuggest> getTripSuggestsByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getTripSuggests();
    }
}

package engine.maps;

import engine.dto.MapsTableElementDetailsDto;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.*;

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

    public List<MapsTableElementDetailsDto> getAllMapsTableElementsDetails() {
        List<MapsTableElementDetailsDto> detailsList = new ArrayList<>();
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

    public MapsTableElementDetailsDto getMapTableElementDetailsByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getMapsTableElementDetails();
    }

    public MapDescriptor getMapDescriptorByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getMapDescriptor();
    }

    public List<TripSuggest> getTripSuggestsByMapName(String mapName) {
        return mapNameToEntity.get(mapName).getTripSuggests();
    }

    public MapEntity getMapEntityByMapName(String mapName) {
        return mapNameToEntity.get(mapName);
    }

    public List<MapsTableElementDetailsDto> getAllMapsTableElementsDetailsCheck() {
        List<MapsTableElementDetailsDto> res = new LinkedList<>();
        MapsTableElementDetailsDto mapsTableElementDetails = new MapsTableElementDetailsDto();
        mapsTableElementDetails.setMatchedTripRequestQuantity(3);
        mapsTableElementDetails.setUserName("check");
        mapsTableElementDetails.setMapName("x");
        mapsTableElementDetails.setStationsQuantity(3);
        mapsTableElementDetails.setRoadsQuantity(4);
        mapsTableElementDetails.setTripSuggestsQuantity(2);
        mapsTableElementDetails.setTripRequestQuantity(4);
        mapsTableElementDetails.setMatchedTripRequestQuantity(1);
        res.add(mapsTableElementDetails);
        return res;
    }
}

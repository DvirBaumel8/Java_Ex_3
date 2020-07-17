package Engine.maps;

import Engine.trips.TripRequest;
import Engine.trips.TripSuggest;
import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsManager {
    private HashMap<Integer, MapEntity> mapEntities;
    private static Integer index;

    public MapsManager() {
        mapEntities = new HashMap<>();
        index = 1;
    }

    public void createNewMap(MapDescriptor mapDescriptor, String userName, String mapName) {
        MapEntity mapEntity = new MapEntity(mapDescriptor, userName, mapName);
        mapEntities.put(index, mapEntity);
        index++;
    }

    public List<MapsTableElementDetails> getAllMapsTableElementsDetails() {
        List<MapsTableElementDetails> detailsList = new ArrayList<>();
        for(Map.Entry<Integer, MapEntity> entry : mapEntities.entrySet()) {
            detailsList.add(entry.getValue().getMapsTableElementDetails());
        }
        return detailsList;
    }

    public void addTripRequestByMapId(Integer mapId, TripRequest tripRequest) {
        MapEntity mapEntity = mapEntities.get(mapId);
        mapEntity.addNewTripRequest(tripRequest);
    }

    public void addTripSuggestByMapId(Integer mapId, TripSuggest tripSuggest) {
        MapEntity mapEntity = mapEntities.get(mapId);
        mapEntity.addNewTripSuggest(tripSuggest);
    }
}

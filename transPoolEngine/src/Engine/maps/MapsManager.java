package Engine.maps;

import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapsManager {
    private List<Map> maps;

    public MapsManager() {
        maps = new ArrayList<>();
    }

    public void createNewMap(MapDescriptor mapDescriptor, String userName, String mapName) {
        Map map = new Map(mapDescriptor, userName, mapName);
    }

    public List<MapsTableElementDetails> getAllMapsTableElementsDetails() {
        return maps.stream().map(Map::getMapsTableElementDetails).collect(Collectors.toList());
    }
}

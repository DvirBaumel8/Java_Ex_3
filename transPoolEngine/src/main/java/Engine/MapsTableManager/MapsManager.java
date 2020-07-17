package Engine.MapsTableManager;

import Engine.XML.XMLLoading.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MapsManager {
    private List<MapElement> mapElements;

    public MapsManager() {
        mapElements = new ArrayList<>();
    }

    public void createNewMapElement(MapDescriptor mapDescriptor, String userNameOwner, String mapName) {
        MapElement mapElement = new MapElement(mapDescriptor, userNameOwner, mapName);
        mapElements.add(mapElement);
    }
}

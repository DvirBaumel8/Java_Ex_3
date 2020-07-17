package Engine.maps;


import Engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;

import java.util.ArrayList;
import java.util.List;

public class MapsTableElementDetailsManager {
    private List<MapsTableElementDetails> mapsTableElementDetails;

    public MapsTableElementDetailsManager() {
        mapsTableElementDetails = new ArrayList<>();
    }

    public void createNewMapElement(MapDescriptor mapDescriptor, String userNameOwner, String mapName) {
        MapsTableElementDetails mapsTableElementDetails = new MapsTableElementDetails(mapDescriptor, userNameOwner, mapName);
        this.mapsTableElementDetails.add(mapsTableElementDetails);
    }
}

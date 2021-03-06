package engine.xmlLoading.xmlValidation;


import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPoolTrip;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path;


import java.util.List;

public interface XMLValidations {
    Integer[] MAP_BOUNDARIES = {6,100};
    boolean validateFileExistsAndXmlFile(String myPathToTheXMLFile);
    boolean validateMapSize(int mapLength, int mapWidth);
    boolean validateUniqueNameStations(List<Stop> stops);
    boolean validateStationsBorders(List<Stop> stops, int mapLength, int mapWidth);
    boolean validateStationsUniqueLocations(List<Stop> stops);
    boolean validateEachWayDefinedFromDefinedStations(List<Path> paths);
    boolean validateEachRoutePassesOnlyThroughDefinedStations(List<TransPoolTrip> transPoolTrips, MapDescriptor mapDescriptor);
}

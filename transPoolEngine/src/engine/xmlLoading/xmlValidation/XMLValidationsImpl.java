package engine.xmlLoading.xmlValidation;

import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPool;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.TransPoolTrip;


import java.io.File;
import java.util.*;

public class XMLValidationsImpl implements XMLValidations {
    private static final String VALID_XML_MESSAGE = "XML file was loaded to the system successfully\n";
    private TransPool transPool;

    public XMLValidationsImpl(TransPool transPool) {
        this.transPool = transPool;
    }

    public boolean validateXmlFile(List<String> errors) {
        List<Stop> stops = transPool.getMapDescriptor().getStops().getStop();
        List<Path> paths = transPool.getMapDescriptor().getPaths().getPath();
        MapDescriptor mapDescriptor = transPool.getMapDescriptor();
        int mapLength = mapDescriptor.getMapBoundries().getLength();
        int mapWidth = mapDescriptor.getMapBoundries().getWidth();

        List<TransPoolTrip> transPoolTrips;

        boolean isValid = true;

        if (!validateMapSize(mapLength, mapWidth)) {
            errors.add("   Map boundaries aren't from 6 to 100\n");
            isValid = false;
        }
        if (!validateUniqueNameStations(stops)) {
            errors.add("   Duplicate station name\n");
            isValid = false;
        }
        if (!validateStationsBorders(stops, mapLength, mapWidth)) {
            errors.add("   Stations locations should be inside the map boundaries\n");
            isValid = false;
        }
        if (!validateStationsUniqueLocations(stops)) {
            errors.add("   Stations locations should be unique\n");
            isValid = false;
        }
        if (!validateEachWayDefinedFromDefinedStations(paths)) {
            errors.add("   Each path contains only exist and define stations\n");
            isValid = false;
        }
        try {
            transPoolTrips = transPool.getPlannedTrips().getTransPoolTrip();
        }
        catch (NullPointerException e) {
            return true;
        }

        if (!validateEachRoutePassesOnlyThroughDefinedStations(transPoolTrips, mapDescriptor)) {
            errors.add("   Each trip suggest contains only exist and defined stations\n");
            isValid = false;
        }
        if(!validateEachSchedulingHasValue(transPoolTrips, errors)) {
            isValid = false;
        }
        if(!validateSuggestedTripsOwnerNameUnique(transPoolTrips)) {
            errors.add("   Suggest trip owner name must be unique\n");
            isValid = false;
        }

        return isValid;
    }

    private boolean validateSuggestedTripsOwnerNameUnique(List<TransPoolTrip> transPoolTrips) {
        List<String> suggestTripsOwnerNames = new ArrayList<>();

        for(TransPoolTrip trip : transPoolTrips) {
            if(suggestTripsOwnerNames.contains(trip.getOwner())) {
                return false;
            }
            else {
                suggestTripsOwnerNames.add(trip.getOwner());
            }
        }
        return true;
    }

    private boolean validateEachSchedulingHasValue(List<TransPoolTrip> transPoolTrips, List<String> errors) {
        for(TransPoolTrip trip : transPoolTrips) {
            if(trip.getScheduling().getDayStart() == null) {
                errors.add("   Each trip suggest should contain a value in scheduling day-start (mandatory)\n");
                return false;
            }
            else if(trip.getScheduling().getDayStart() >= 1){

            }
            else {
                errors.add("   Scheduling day-start should be a number that higher or equals to 1 (mandatory)\n");
                return trip.getScheduling().getDayStart() >= 1;
            }
        }
        return true;
    }

    @Override
    public boolean validateFileExistsAndXmlFile(String myPathToTheXMLFile) {
        File xml;
        try {
            xml = new File(myPathToTheXMLFile);
            return xml.exists() && xml.getName().endsWith(".xml");
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateMapSize(int mapLength, int mapWidth) {
        boolean isMapSizeValidate = false;
        if ((mapLength >= MAP_BOUNDARIES[0] && mapLength <= MAP_BOUNDARIES[1])
                || (mapWidth >= MAP_BOUNDARIES[0] && mapWidth <= MAP_BOUNDARIES[1])) {
            isMapSizeValidate = true;
        }
        return isMapSizeValidate;
    }

    @Override
    public boolean validateUniqueNameStations(List<Stop> stops) {
        Set<Stop> myStops = new HashSet<>();
        for (Stop stop : stops) {
            if(!myStops.add(stop)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean validateStationsBorders(List<Stop> stops, int mapLength, int mapWidth) {
        boolean isValidateStationsBorders = true;

        for (Stop stop : stops) {
            if (stop.getX() <= mapWidth && stop.getY() >= mapLength) {
                isValidateStationsBorders = false;
            }
        }
        return isValidateStationsBorders;
    }

    @Override
    public boolean validateStationsUniqueLocations(List<Stop> stops) {
        boolean isValidateUniqueLocation = true;

        for (Stop stop1 : stops) {
            for (Stop stop2 : stops) {
                if (!stop1.equals(stop2)) {
                    if ((stop1.getX() == stop2.getX()) &&
                            (stop1.getY() == stop2.getY())) {
                        isValidateUniqueLocation = false;
                    }
                }
            }
        }
        return isValidateUniqueLocation;
    }

    @Override
    public boolean validateEachWayDefinedFromDefinedStations(List<engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path> paths) {
        boolean isValida = true;

        for (Path path : paths) {
            String from = path.getFrom();
            String to = path.getTo();

            if (!(checkStopStationExist(from) && checkStopStationExist(to))) {
                isValida = false;
            }
        }

        return isValida;
    }

    @Override
    public boolean validateEachRoutePassesOnlyThroughDefinedStations(List<TransPoolTrip> transPoolTrips, MapDescriptor mapDescriptor) {

        for(TransPoolTrip trip : transPoolTrips) {
            String[] route = trip.getRoute().getPath().split(",");
            if(!checkIfRouteIsLegal(route, mapDescriptor.getPaths().getPath())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfRouteIsLegal(String[] route, List<Path> paths) {
        for(int i =0; i < route.length - 1; i++) {
            if(!checkIfPathLegal(route[i].trim(), route[i + 1].trim(), paths)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfPathLegal(String from, String to, List<Path> paths) {
        for(Path path : paths) {
            if( (path.getFrom().equals(from) && path.getTo().equals(to) ) || (path.getFrom().equals(to) && path.getTo().equals(from) && !path.isOneWay())) {
                return true;
            }
        }

        return false;
    }

    public boolean checkStopStationExist(String stationName) {


        for(Stop stop : transPool.getMapDescriptor().getStops().getStop()) {
            if(stop.getName().equals(stationName)) {
                return true;
            }
        }

        return false;
    }

    public static String getValidXmlMessage() {
        return VALID_XML_MESSAGE;
    }
}
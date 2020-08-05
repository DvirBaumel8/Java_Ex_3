package engine.utils;

import engine.matching.Station;
import engine.trips.Time;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;

import java.util.LinkedList;

public class TripsUtil {

    public static int calcCost(int ppk, LinkedList<Station> stations, MapDescriptor mapDescriptor) {
        int sum = 0;
        for(int i = 0; i < stations.size() - 1; i++) {
            int km = getLengthBetweenStations(stations.get(i).getName(), stations.get(i+1).getName(), mapDescriptor);
            sum += km * ppk;
        }
        return sum;
    }

    private static int getLengthBetweenStations(String pathFrom, String pathTo, MapDescriptor mapDescriptor) {
        for (Path path : mapDescriptor.getPaths().getPath()) {
            if (path.getFrom().equals(pathFrom) && path.getTo().equals(pathTo) || !path.isOneWay() && path.getFrom().equals(pathTo) && path.getTo().equals(pathFrom)) {
                return path.getLength();
            }
        }
        return -1;
    }

    public static double calcRequiredFuel(LinkedList<Station> stations, MapDescriptor mapDescriptor) {
        for (Path path : mapDescriptor.getPaths().getPath()) {
            if (path.getFrom().equals(stations.getFirst().getName()) && path.getTo().equals(stations.getLast().getName()) || !path.isOneWay() && path.getFrom().equals(stations.getLast().getName()) && path.getTo().equals(stations.getFirst().getName())) {
                return (double) path.getLength() / path.getFuelConsumption();
            }
        }
        return -1;
    }

    public static double calcRequiredFuel(Station station1, Station station2, MapDescriptor mapDescriptor) {
        for (Path path : mapDescriptor.getPaths().getPath()) {
            if (path.getFrom().equals(station1.getName()) && path.getTo().equals(station2.getName()) || !path.isOneWay() && path.getFrom().equals(station2.getName()) && path.getTo().equals(station1.getName())) {
                return (double) path.getLength() / path.getFuelConsumption();
            }
        }
        return -1;
    }


    public static double calcRequiredFuel(Route route, MapDescriptor mapDescriptor) {
        double sum = 0;
        String[] paths = route.getPath().split(",");
        for (int i = 0; i < paths.length - 1; i++) {
            sum += getRequiredFuelToPath(paths[i], paths[i + 1], mapDescriptor);
        }
        return sum;
    }


    private static double getRequiredFuelToPath(String pathFrom, String pathTo, MapDescriptor mapDescriptor) {
        for (Path path : mapDescriptor.getPaths().getPath()) {
            if (path.getFrom().equals(pathFrom) && path.getTo().equals(pathTo) || !path.isOneWay() && path.getFrom().equals(pathTo) && path.getTo().equals(pathFrom)) {
                return (double)path.getLength() / path.getFuelConsumption();
            }
        }
        return -1;
    }


    public static double calcMinutesToRoute(String pathFrom, String pathTo, MapDescriptor mapDescriptor) {
        boolean isPathOneWay;
        double retVal = 0;
        for (Path path : mapDescriptor.getPaths().getPath()) {
            if (path.getFrom().equals(pathFrom) && path.getTo().equals(pathTo) ) {
                return (double) path.getLength() / path.getSpeedLimit();
            } else {
                try {
                    isPathOneWay = path.isOneWay();
                } catch (NullPointerException e) {
                    isPathOneWay = false;
                }
                if (!isPathOneWay) {
                    if (path.getTo().equals(pathFrom) && path.getFrom().equals(pathTo)) {
                        return (double) path.getLength() / path.getSpeedLimit();
                    }
                }
            }
        }
        return 0;
    }

    public static void calcArrivalHourToSpecificStation(Station[] stations, Station station, MapDescriptor mapDescriptor, Time startingTime) {
        StringBuilder pathToCalc = new StringBuilder();

        for(int i =0; i < stations.length; i++) {
            if(!stations[i].getName().equals(station.getName())) {
                pathToCalc.append(stations[i].getName());
                pathToCalc.append(",");
            }
            else {
                pathToCalc.append(stations[i].getName());
                break;
            }
        }
        station.setArrivalTime(calcArrivalHour(pathToCalc.toString(), mapDescriptor, startingTime));
    }

    public static int calculateTripPrice(int ppk, Route route, MapDescriptor mapDescriptor) {
        int sum = 0;
        String[] paths = route.getPath().split(",");
        for(int i = 0; i < paths.length - 1; i++) {
            int km = getLengthBetweenStations(paths[i], paths[i+1], mapDescriptor);
            sum += km * ppk;
        }
        return sum;
    }

    public static Time calcArrivalHour(String path, MapDescriptor mapDescriptor, Time startingTime) {
        int sumMinutes = 0;
        double currRouteMinutes = 0;

        String[] paths = path.split(",");
        for(int i = 0; i < paths.length - 1; i++) {
            currRouteMinutes = calcMinutesToRoute(paths[i], paths[i+1], mapDescriptor);
            sumMinutes += convertMinutesToHoursFormat(currRouteMinutes);
        }
        Time arrivalTime = new Time(startingTime.getMinutes(), startingTime.getHours(), startingTime.getDay());
        arrivalTime.addMinutes(sumMinutes);
        return arrivalTime;
    }

    private static int convertMinutesToHoursFormat(double currRouteMinutes) {
        int retVal = 0;
        double temp;

        currRouteMinutes = currRouteMinutes * 0.6;

        temp = (currRouteMinutes * 100) % 10;

        if( temp != 5.0 && temp != 0) {
            temp = temp % 5;
            if(temp > 2.5) {
                currRouteMinutes = currRouteMinutes - temp/100;
                currRouteMinutes = currRouteMinutes + 0.05;
            }
            else {
                currRouteMinutes = currRouteMinutes - temp/100;
            }
        }
        retVal += currRouteMinutes*100;
        return retVal;
    }

}

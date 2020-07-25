package engine.maps.graph;


import engine.maps.graph.components.coordinate.CoordinateNode;
import engine.maps.graph.components.coordinate.CoordinatesManager;
import engine.maps.graph.components.station.StationManager;
import engine.maps.graph.components.station.StationNode;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.List;

public class GraphBuilder {
    private MapDescriptor mapDescriptor;
    private CoordinatesManager coordinatesManager = null;

    private StationManager stationManager = null;

    public GraphBuilder(MapDescriptor mapDescriptor) {
        this.mapDescriptor = mapDescriptor;
    }

    public String buildHtmlGraph(MapDescriptor mapDescriptor) {
        return "";
    }

//    private void createEdges() {
//        List<Path> pathList = mapDescriptor.getPaths().getPath();
//        List<Stop> stopStations = mapDescriptor.getStops().getStop();
//
//        for(Stop stop1 : stopStations) {
//            for(Stop stop2 : stopStations) {
//                for(Path path : pathList) {
//                    String from = path.getFrom();
//                    String to = path.getTo();
//                    boolean isOneWay = path.isOneWay();
//                    if(from.equals(stop1.getName()) && to.equals(stop2.getName())) {
//                        ArrowShape arrow = new ArrowShape(coordinatesManager.getOrCreate(stop1.getX(), stop1.getY()), coordinatesManager.getOrCreate(stop2.getX(), stop2.getY()));
//                        arrow.textProperty().set(String.valueOf(path.getLength()));
//
//                        model.addEdge(arrow); // 1-3
//                        if(!isOneWay) {
//                            arrow edge2 = new arrow(coordinatesManager.getOrCreate(stop2.getX(),stop2.getY())
//                                    , coordinatesManager.getOrCreate(stop1.getX(),stop1.getY()));
//                            model.addEdge(edge2); // 1-3
//                        }
//                    }
//                }
//            }
//        }
//    }

//    public Graph setAndGetGraphByCurrentTripSuggest(String currentTripSuggestDetails) {
//        // mark path station in unique color plus mark the path in uniqe color
//
//        String[] inputs = currentTripSuggestDetails.split(",");
//        String suggestId = inputs[1];
//        String roadTrip = inputs[2];
//        String currUserStation = inputs[3];
//        suggestId = suggestId.substring(4);
//        roadTrip = roadTrip.substring(8);
//        currUserStation = currUserStation.substring(12);
//        String[] currRoute = roadTrip.split("-");
//        List<Stop> suggestRoadTripStations = getSuggestRoadTripStationsFromPathArr(currRoute);
//        Stop currStation = getStopObjectFromStationName(currUserStation, suggestRoadTripStations);
//
//        markSuggestTripStationsAndCurrStation(suggestRoadTripStations, currStation, suggestId);
//        graph.endUpdate();
//        graph.getCanvas().setMaxWidth(1030);
//        graph.getCanvas().setPrefWidth(1030);
//        graph.getCanvas().setPrefHeight(800);
//        graph.getCanvas().setMaxHeight(800);
//        graph.layout(new MapGridLayout(coordinatesManager, stationManager));
//
//        return null;
//    }

//    private List<Stop> getSuggestRoadTripStationsFromPathArr(String[] currRoute) {
//        List<Stop> suggestRoadTripStations = new LinkedList<>();
//        int index = 0;
//        List<Stop> transPoolStopList = transPool.getMapDescriptor().getStops().getStop();
//
//        for(int i = 0 ; i < transPoolStopList.size() ; i ++) {
//            if(transPoolStopList.get(i).getName().equals(currRoute[index])) {
//                suggestRoadTripStations.add(transPoolStopList.get(i));
//                index ++;
//            }
//        }
//
//        return suggestRoadTripStations;
//    }

    private Stop getStopObjectFromStationName(String stationName, List<Stop> stopStations) {
        Stop res = new Stop();
        res.setName(stationName);

        for(Stop stop : stopStations) {
            String stopStr = stop.getName();
            if(stopStr.equals(res)) {
                res.setX(stop.getX());
                res.setY(stop.getY());
            }
        }

        return res;
    }

    private Path getSpecificPath(List<Path> pathList, String fromStr, String toStr) {
        Path resPath = null;

        for(Path path : pathList) {
            if(path.equals(fromStr) && path.equals(toStr)) {
                resPath = path;
            }
        }

        return resPath;
    }

    private int findCoordinateYToStation(String sourceStation) {
        return getXCoorOfStation(sourceStation, mapDescriptor.getStops().getStop());
    }

    private int findCoordinateXToStation(String sourceStation) {
        return getYCoorOfStation(sourceStation, mapDescriptor.getStops().getStop());
    }

    private void createCoordinates() {
        coordinatesManager = new CoordinatesManager(CoordinateNode::new);
        List<Stop> stopStations = mapDescriptor.getStops().getStop();
        int mapLength = mapDescriptor.getMapBoundries().getLength();
        int mapWidth = mapDescriptor.getMapBoundries().getWidth();

        for (int i=0; i<mapLength; i++) {
            for (int j = 0; j < mapWidth; j++) {
               // model.addCell(coordinatesManager.getOrCreate(i+1, j+1));
            }
        }

    }

    private void createStations() {
        StationNode node;

        for(Stop station : mapDescriptor.getStops().getStop()) {
            node = stationManager.getOrCreate(station.getX(), station.getY());
            node.setName(station.getName());
            //model.addCell(node);
        }
    }

    private StationManager markSuggestTripStationsAndCurrStation(List<Stop> suggestRoadTripStations, Stop currStation,
                                                                 String suggestId) {
        StationNode node;
        int index = 1;

        for(Stop station : suggestRoadTripStations) {
            if(currStation.equals(station.getName())) {
                node = stationManager.getOrCreate(station.getX(), station.getY());
                node.setName(station.getName() + suggestId +",This is curr station! number " + index + "at the road trip");
                //model.addCell(node);
            }
            else {
                node = stationManager.getOrCreate(station.getX(), station.getY());
                node.setName(station.getName() + suggestId + ",station number:" + index + "at the road trip" );
               // model.addCell(node);
            }
            index++;
        }

        return stationManager;
    }

//    private void moveAllEdgesToTheFront(Graph graph) {
//
//        List<Node> onlyEdges = new ArrayList<>();
//
//        // finds all edge nodes and remove them from the beginning of list
//        ObservableList<Node> nodes = graph.getCanvas().getChildren();
//        while (nodes.get(0).getClass().getSimpleName().equals("EdgeGraphic")) {
//            onlyEdges.add(nodes.remove(0));
//        }
//
//        // adds them as last ones
//        nodes.addAll(onlyEdges);
//    }

//    public Graph createGraph(Time time, TransPool transPool) {
//        graph = new Graph();
//        model = graph.getModel();
//        graph.beginUpdate();
//        createCoordinates();
//        createStations();
//        createEdges();
//        graph.endUpdate();
//        graph.getCanvas().setMaxWidth(1030);
//        graph.getCanvas().setPrefWidth(1030);
//        graph.getCanvas().setPrefHeight(800);
//        graph.getCanvas().setMaxHeight(800);
//        graph.layout(new MapGridLayout(coordinatesManager, stationManager));
//
//        //graph.getViewportGestures().setZoomSpeed(1);
//        return graph;
//    }

    private int getXCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getX();
            }
        }
        return -1;
    }

    private int getYCoorOfStation(String sourceStation, List<Stop> stops) {
        for(Stop stop : stops) {
            if(stop.getName().equals(sourceStation)) {
                return stop.getY();
            }
        }
        return -1;
    }
}

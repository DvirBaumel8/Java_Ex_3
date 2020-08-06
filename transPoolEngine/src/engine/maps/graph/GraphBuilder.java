package engine.maps.graph;


import engine.maps.graph.components.CoordinateNode;
import engine.maps.graph.components.GraphModel;
import engine.maps.graph.components.PathNode;
import engine.maps.graph.components.StationNode;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.MapDescriptor;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Path;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Route;
import engine.xmlLoading.xmlLoadingClasses.jaxb.schema.generated.Stop;

import java.util.ArrayList;
import java.util.List;

public class GraphBuilder {
    private MapDescriptor mapDescriptor;

    public GraphBuilder(MapDescriptor mapDescriptor) {
        this.mapDescriptor = mapDescriptor;
    }

    public String buildHtmlGraph() {
        GraphModel model = new GraphModel();
        model.setCoordinateNodes(createCoordinatesNodes());
        model.setPathsNodes(createPathsNodes(model.getCoordinateNodes()));
        model.setStationsNodes(createStationsNodes(model.getCoordinateNodes()));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<script>\n" +
                "\tfunction stationClicked(e) {\n" +
                "\t\tconsole.log(\"hello\");\n" +
                "\t}\n" +
                "</script>\n" +
                "<svg width=\"650\" height=\"800\">\n" +
                "  <rect\n" +
                "  width=\"1500\"\n" +
                "  height=\"600\"\n" +
                "  style=\"fill:lightblue;stroke-width:1;stroke:rgb(0,0,0)\" />\n");

        for(CoordinateNode coordinateNode : model.getCoordinateNodes()) {
            html.append(String.format("<circle cx=\"%d\" cy=\"%d\" r=\"2\" stroke=\"black\" stroke-width=\"1\" fill=\"black\" />\n", coordinateNode.getX(), coordinateNode.getY()));
        }

        for(StationNode node : model.getStationsNodes()) {
            html.append(String.format("<circle id= \"%s\" onclick =\"stationClicked('%s')\" cx=\"%d\" cy=\"%d\" r=\"7\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" />\n", node.getName(), node.getName(), node.getX(), node.getY()));
            html.append(String.format("<text x=\"%d\" y=\"%d\" fill=\"purple\" font-size=\"10px\">%s</text>\n", node.getX() - 15, node.getY() + 18, node.getName()));
        }

        for(PathNode node : model.getPathsNodes()) {
            double xMiddle = (node.getStartX() + node.getEndX())/2;
            double yMiddle = (node.getStartY() + node.getEndY())/2;
            html.append(String.format("  <line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:blue;stroke-width:1\" />\n", node.getStartX(), node.getStartY(), node.getEndX(), node.getEndY()));
            html.append(String.format("    <text x=\"%f\" y=\"%f\" fill=\"purple\" font-size=\"10px\">%d</text>\n", xMiddle + 2, yMiddle - 3, node.getLength()));
        }

        html.append("</svg>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    public String buildHtmlGraphSourceDestHighlight(String sourceStation, String destinationStation) {
        GraphModel model = new GraphModel();
        model.setCoordinateNodes(createCoordinatesNodes());
        model.setPathsNodes(createPathsNodes(model.getCoordinateNodes()));
        model.setStationsNodes(createStationsNodes(model.getCoordinateNodes()));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<script>\n" +
                "\tfunction stationClicked(e) {\n" +
                "\t\tconsole.log(\"hello\");\n" +
                "\t}\n" +
                "</script>\n" +
                "<svg width=\"650\" height=\"800\">\n" +
                "  <rect\n" +
                "  width=\"1500\"\n" +
                "  height=\"600\"\n" +
                "  style=\"fill:lightblue;stroke-width:1;stroke:rgb(0,0,0)\" />\n");

        for(CoordinateNode coordinateNode : model.getCoordinateNodes()) {
            html.append(String.format("<circle cx=\"%d\" cy=\"%d\" r=\"2\" stroke=\"black\" stroke-width=\"1\" fill=\"black\" />\n", coordinateNode.getX(), coordinateNode.getY()));
        }

        for(StationNode node : model.getStationsNodes()) {
            if(node.getName().equals(sourceStation) || node.getName().equals(destinationStation)) {
                html.append(String.format("<circle id= \"%s\" onclick =\"stationClicked('%s')\" cx=\"%d\" cy=\"%d\" r=\"9\" stroke=\"black\" stroke-width=\"1\" fill=\"blue\" />\n", node.getName(), node.getName(), node.getX(), node.getY()));
            }
            else {
                html.append(String.format("<circle id= \"%s\" onclick =\"stationClicked('%s')\" cx=\"%d\" cy=\"%d\" r=\"7\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" />\n", node.getName(), node.getName(), node.getX(), node.getY()));
            }
            html.append(String.format("<text x=\"%d\" y=\"%d\" fill=\"purple\" font-size=\"10px\">%s</text>\n", node.getX() - 15, node.getY() + 18, node.getName()));
        }

        for(PathNode node : model.getPathsNodes()) {
            double xMiddle = (node.getStartX() + node.getEndX())/2;
            double yMiddle = (node.getStartY() + node.getEndY())/2;
            html.append(String.format("  <line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:blue;stroke-width:1\" />\n", node.getStartX(), node.getStartY(), node.getEndX(), node.getEndY()));
            html.append(String.format("    <text x=\"%f\" y=\"%f\" fill=\"purple\" font-size=\"10px\">%d</text>\n", xMiddle + 2, yMiddle - 3, node.getLength()));
        }

        html.append("</svg>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    private List<CoordinateNode> createCoordinatesNodes() {
        List<CoordinateNode> nodes = new ArrayList<>();
        int width = mapDescriptor.getMapBoundries().getWidth();
        int length = mapDescriptor.getMapBoundries().getLength();
        int wValue = 30;
        int lValue = 20;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < length; j++) {
                CoordinateNode node = new CoordinateNode(wValue, lValue, i, j);
                nodes.add(node);
                wValue += 30;
            }
            wValue = 30;
            lValue += 30;
        }
        return nodes;
    }

    private List<PathNode> createPathsNodes(List<CoordinateNode> coordinateNodes) {
        List<PathNode> nodes = new ArrayList<>();
        for(Path path : mapDescriptor.getPaths().getPath()) {
            PathNode pathNode = createPathNodeFromPath(path, coordinateNodes);
            nodes.add(pathNode);
        }
        return nodes;
    }

    private PathNode createPathNodeFromPath(Path path, List<CoordinateNode> coordinateNodes) {
        double startX = 0, startY = 0, endX = 0, endY = 0;
        double logicStartX = 0, logicStartY = 0, logicEndX = 0, logicEndY = 0;

        String from = path.getFrom();
        String to = path.getTo();
        for(Stop stop : mapDescriptor.getStops().getStop()) {
            if(stop.getName().equals(from)) {
                startX = stop.getX();
                startY = stop.getY();
            }
            if(stop.getName().equals(to)) {
                endX = stop.getX();
                endY = stop.getY();
            }
        }
        for(CoordinateNode node : coordinateNodes) {
            if(node.getIndexX() == startX && node.getIndexY() == startY) {
                logicStartX = node.getX();
                logicStartY = node.getY();
                break;
            }
        }
        for(CoordinateNode node : coordinateNodes) {
            if(node.getIndexX() == endX && node.getIndexY() == endY) {
                logicEndX = node.getX();
                logicEndY = node.getY();
                break;
            }
        }

        return new PathNode(logicStartX, logicStartY, logicEndX, logicEndY, path.getLength(), from, to, path.isOneWay());
    }

    private List<StationNode> createStationsNodes(List<CoordinateNode> coordinateNodes) {
        List<StationNode> nodes = new ArrayList<>();
        for(Stop stop : mapDescriptor.getStops().getStop()) {
            StationNode node = createStationNodeFromStop(stop, coordinateNodes);
            nodes.add(node);
        }
        return nodes;
    }

    private StationNode createStationNodeFromStop(Stop stop, List<CoordinateNode> coordinateNodes) {
        int indexX = stop.getX();
        int indexY = stop.getY();
        int logicX = 0;
        int logicY = 0;
        for(CoordinateNode node : coordinateNodes) {
            if(node.getIndexX() == indexX && node.getIndexY() == indexY) {
                logicX = node.getX();
                logicY = node.getY();
                break;
            }
        }
        return new StationNode(logicX, logicY, stop.getName());
    }

    public String buildHtmlGraphHighlightRoute(Route tripRoute) {
        String[] stations = tripRoute.getPath().split(",");
        List<String> stationsList = new ArrayList<>();
        for(int i=0; i < stations.length; i++) {
            stationsList.add(stations[i]);
        }
        GraphModel model = new GraphModel();
        model.setCoordinateNodes(createCoordinatesNodes());
        model.setPathsNodes(createPathsNodes(model.getCoordinateNodes()));
        model.setStationsNodes(createStationsNodes(model.getCoordinateNodes()));

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<script>\n" +
                "\tfunction stationClicked(e) {\n" +
                "\t\tconsole.log(\"hello\");\n" +
                "\t}\n" +
                "</script>\n" +
                "<svg width=\"650\" height=\"800\">\n" +
                "  <rect\n" +
                "  width=\"1500\"\n" +
                "  height=\"600\"\n" +
                "  style=\"fill:lightblue;stroke-width:1;stroke:rgb(0,0,0)\" />\n");

        for(CoordinateNode coordinateNode : model.getCoordinateNodes()) {
            html.append(String.format("<circle cx=\"%d\" cy=\"%d\" r=\"2\" stroke=\"black\" stroke-width=\"1\" fill=\"black\" />\n", coordinateNode.getX(), coordinateNode.getY()));
        }

        for(StationNode node : model.getStationsNodes()) {
            html.append(String.format("<circle id= \"%s\" onclick =\"stationClicked('%s')\" cx=\"%d\" cy=\"%d\" r=\"7\" stroke=\"black\" stroke-width=\"1\" fill=\"red\" />\n", node.getName(), node.getName(), node.getX(), node.getY()));
            html.append(String.format("<text x=\"%d\" y=\"%d\" fill=\"purple\" font-size=\"10px\">%s</text>\n", node.getX() - 15, node.getY() + 18, node.getName()));
        }

        for(PathNode node : model.getPathsNodes()) {
            double xMiddle = (node.getStartX() + node.getEndX())/2;
            double yMiddle = (node.getStartY() + node.getEndY())/2;
            if(isNodePartOfRoute(node, tripRoute)) {
                html.append(String.format("  <line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:red;stroke-width:1\" />\n", node.getStartX(), node.getStartY(), node.getEndX(), node.getEndY()));
                html.append(String.format("   <text x=\"%f\" y=\"%f\" fill=\"blue\" font-size=\"10px\">%d</text>\n", xMiddle + 2, yMiddle - 3, node.getLength()));
            }
            else {
                html.append(String.format("  <line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" style=\"stroke:blue;stroke-width:1\" />\n", node.getStartX(), node.getStartY(), node.getEndX(), node.getEndY()));
                html.append(String.format("   <text x=\"%f\" y=\"%f\" fill=\"purple\" font-size=\"10px\">%d</text>\n", xMiddle + 2, yMiddle - 3, node.getLength()));
            }

        }

        html.append("</svg>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    private boolean isNodePartOfRoute(PathNode node, Route tripRoute) {
        String[] stations = tripRoute.getPath().split(",");
        for(int i = 0; i < stations.length - 1; i++) {
            if(stations[i].equals(node.getFrom()) && stations[i + 1].equals(node.getTo()) || !node.isOneWay() && stations[i].equals(node.getTo()) && stations[i+1].equals(node.getFrom())) {
                return true;
            }
        }
        return false;
    }
}

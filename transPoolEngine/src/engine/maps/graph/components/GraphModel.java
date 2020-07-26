package engine.maps.graph.components;

import java.util.List;

public class GraphModel {
    private List<StationNode> stationsNodes;
    private List<CoordinateNode> coordinateNodes;
    private List<PathNode> pathsNodes;

    public List<StationNode> getStationsNodes() {
        return stationsNodes;
    }

    public void setStationsNodes(List<StationNode> stationsNodes) {
        this.stationsNodes = stationsNodes;
    }

    public List<CoordinateNode> getCoordinateNodes() {
        return coordinateNodes;
    }

    public void setCoordinateNodes(List<CoordinateNode> coordinateNodes) {
        this.coordinateNodes = coordinateNodes;
    }

    public List<PathNode> getPathsNodes() {
        return pathsNodes;
    }

    public void setPathsNodes(List<PathNode> pathsNodes) {
        this.pathsNodes = pathsNodes;
    }
}

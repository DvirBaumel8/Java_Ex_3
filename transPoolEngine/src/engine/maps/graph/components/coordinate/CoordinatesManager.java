package engine.maps.graph.components.coordinate;

import engine.maps.graph.components.util.NodesManager;

import java.util.function.BiFunction;

public class CoordinatesManager extends NodesManager<CoordinateNode> {
    public CoordinatesManager(BiFunction<Integer, Integer, CoordinateNode> factory) {
        super(factory);
    }
}


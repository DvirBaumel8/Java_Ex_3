package Engine.maps.graph.components.station;

import Engine.maps.graph.components.util.NodesManager;

import java.util.function.BiFunction;

public class StationManager  extends NodesManager<StationNode> {
    public StationManager(BiFunction<Integer, Integer, StationNode> factory) {
        super(factory);
    }
}


package engine.maps;

import com.fxgraph.graph.Graph;
import engine.trips.TripRequest;
import engine.trips.TripSuggest;

import java.util.List;

public class MapRepresentation {
    private List<TripRequest> tripRequests;
    private List<TripSuggest> tripSuggests;
    private Graph graph;

    public MapRepresentation(List<TripRequest> tripRequests, List<TripSuggest> tripSuggests, Graph graph) {
        this.tripRequests = tripRequests;
        this.tripSuggests = tripSuggests;
        this.graph = graph;
    }
}

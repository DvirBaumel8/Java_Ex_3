package engine.dto;

import com.fxgraph.graph.Graph;

import java.util.List;

public class MapRepresentation {
    private List<TripRequestDto> tripRequests;
    private List<TripSuggestDto> tripSuggests;
    private Graph graph;

    public MapRepresentation(List<TripRequestDto> tripRequests, List<TripSuggestDto> tripSuggests, Graph graph) {
        this.tripRequests = tripRequests;
        this.tripSuggests = tripSuggests;
        this.graph = graph;
    }
}

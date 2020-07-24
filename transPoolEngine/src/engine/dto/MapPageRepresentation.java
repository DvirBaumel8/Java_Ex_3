package engine.dto;

import java.util.List;

public class MapPageRepresentation {
    private List<TripRequestDto> tripRequests;
    private List<TripSuggestDto> tripSuggests;
    //private Graph graph;

    public MapPageRepresentation(List<TripRequestDto> tripRequests, List<TripSuggestDto> tripSuggests, String x) {
        this.tripRequests = tripRequests;
        this.tripSuggests = tripSuggests;
        //this.graph = graph;
    }
}

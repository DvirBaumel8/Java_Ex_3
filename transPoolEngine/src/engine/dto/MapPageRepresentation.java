package engine.dto;

import java.util.List;

public class MapPageRepresentation {
    private List<TripRequestDto> tripRequestsDtos;
    private List<TripSuggestDto> tripSuggestDtos;
    //private Graph graph;

    public MapPageRepresentation(List<TripRequestDto> tripRequests, List<TripSuggestDto> tripSuggests, String x) {
        this.tripRequestsDtos = tripRequests;
        this.tripSuggestDtos = tripSuggests;
        //this.graph = graph;
    }
}

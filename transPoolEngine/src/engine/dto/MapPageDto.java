package engine.dto;

import java.util.List;

public class MapPageDto {
    private List<TripRequestDto> tripRequests;
    private List<TripSuggestDto> tripSuggests;
    private String htmlGraph;

    public MapPageDto(List<TripRequestDto> tripRequests, List<TripSuggestDto> tripSuggests, String htmlGraph) {
        this.tripRequests = tripRequests;
        this.tripSuggests = tripSuggests;
        this.htmlGraph = htmlGraph;
    }
}

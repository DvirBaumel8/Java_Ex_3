package engine.manager;

import engine.dto.mapPage.TripRequestDto;
import engine.dto.mapPage.TripSuggestDto;

import java.util.List;

public class MapPageDto {
    private List<TripRequestDto> tripRequestDtoList;
    private List<TripSuggestDto> tripSuggestDtoList;
    private String htmlGraph;

    public MapPageDto(List<TripRequestDto> tripRequestDtoList, List<TripSuggestDto> tripSuggestDtoList, String htmlGraph) {
        this.tripRequestDtoList = tripRequestDtoList;
        this.tripSuggestDtoList = tripSuggestDtoList;
        this.htmlGraph = htmlGraph;
    }

    public List<TripRequestDto> getTripRequestDtoList() {
        return tripRequestDtoList;
    }

    public void setTripRequestDtoList(List<TripRequestDto> tripRequestDtoList) {
        this.tripRequestDtoList = tripRequestDtoList;
    }

    public List<TripSuggestDto> getTripSuggestDtoList() {
        return tripSuggestDtoList;
    }

    public void setTripSuggestDtoList(List<TripSuggestDto> tripSuggestDtoList) {
        this.tripSuggestDtoList = tripSuggestDtoList;
    }

    public String getHtmlGraph() {
        return htmlGraph;
    }

    public void setHtmlGraph(String htmlGraph) {
        this.htmlGraph = htmlGraph;
    }
}

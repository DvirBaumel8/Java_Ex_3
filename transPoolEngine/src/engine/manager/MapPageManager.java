package engine.manager;

import engine.dto.mapPage.HtmlGraphDto;
import engine.dto.mapPage.TripRequestDto;
import engine.dto.mapPage.TripSuggestDto;

import java.util.List;

public class MapPageManager {
    private List<TripRequestDto> tripRequestDtoList;
    private List<TripSuggestDto> tripSuggestDtoList;
    private HtmlGraphDto htmlGraph;

    public MapPageManager(List<TripRequestDto> tripRequestDtoList, List<TripSuggestDto> tripSuggestDtoList
            , HtmlGraphDto htmlGraph) {
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

    public HtmlGraphDto getHtmlGraph() {
        return htmlGraph;
    }

    public void setHtmlGraph(HtmlGraphDto htmlGraph) {
        this.htmlGraph = htmlGraph;
    }
}

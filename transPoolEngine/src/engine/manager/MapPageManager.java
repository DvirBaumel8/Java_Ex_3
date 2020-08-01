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
}

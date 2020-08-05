package engine.dto.mapPage;

import java.util.List;

public class TripSuggestResponseDto {
    private List<TripSuggestDto> tripSuggestDtoList;
    private String error;

    public TripSuggestResponseDto(List<TripSuggestDto> tripSuggestDtoList, String error) {
        this.tripSuggestDtoList = tripSuggestDtoList;
        this.error = error;
    }
}

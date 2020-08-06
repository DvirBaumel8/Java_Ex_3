package engine.dto.mapPage;

import java.util.List;

public class TripSuggestResponseDto {
    private List<TripSuggestDto> tripSuggestDtoList;
    private String errors;

    public TripSuggestResponseDto(List<TripSuggestDto> tripSuggestDtoList, String error) {
        this.tripSuggestDtoList = tripSuggestDtoList;
        this.errors = error;
    }
}

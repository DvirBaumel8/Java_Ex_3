package engine.dto.mapPage;

import java.util.List;

public class TripRequestResponseDto {
    private List<TripRequestDto> tripRequestDtoList;
    private String error;

    public TripRequestResponseDto(List<TripRequestDto> tripRequestDtoList, String error) {
        this.tripRequestDtoList = tripRequestDtoList;
        this.error = error;
    }
}

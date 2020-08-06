package engine.dto.mapPage;

import java.util.List;

public class TripRequestResponseDto {
    private List<TripRequestDto> tripRequestDtoList;
    private String errors;

    public TripRequestResponseDto(List<TripRequestDto> tripRequestDtoList, String error) {
        this.tripRequestDtoList = tripRequestDtoList;
        this.errors = error;
    }
}

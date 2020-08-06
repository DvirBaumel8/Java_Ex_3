package engine.dto.mapPage;

import java.util.List;

public class PotentialTripsResponseDto {
    private List<PotentialRoadTripDto> potentialRoadTripDto;
    private String errors;

    public PotentialTripsResponseDto(List<PotentialRoadTripDto> potentialRoadTripDto, String error) {
        this.potentialRoadTripDto = potentialRoadTripDto;
        this.errors = error;
    }
}

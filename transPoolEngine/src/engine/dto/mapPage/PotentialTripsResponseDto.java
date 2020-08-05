package engine.dto.mapPage;

import java.util.List;

public class PotentialTripsResponseDto {
    private List<PotentialRoadTripDto> potentialRoadTripDto;
    private String error;

    public PotentialTripsResponseDto(List<PotentialRoadTripDto> potentialRoadTripDto, String error) {
        this.potentialRoadTripDto = potentialRoadTripDto;
        this.error = error;
    }
}

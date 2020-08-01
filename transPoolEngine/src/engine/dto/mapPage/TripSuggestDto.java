package engine.dto.mapPage;

import java.util.List;

public class TripSuggestDto {
    private int suggestId;
    private List<String> passengersNames;
    private int tripDay;
    private String sourceStation;
    private String destinationStation;
    private double ratingAVG;
    private int numOfRatings;
    private List<String> literallyRatings;

    public TripSuggestDto(int suggestId, List<String> passengersNames, int tripDay, String sourceStation, String destinationStation, double ratingAVG, int numOfRatings, List<String> literallyRatings) {
        this.suggestId = suggestId;
        this.passengersNames = passengersNames;
        this.tripDay = tripDay;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.ratingAVG = ratingAVG;
        this.numOfRatings = numOfRatings;
        this.literallyRatings = literallyRatings;
    }
}

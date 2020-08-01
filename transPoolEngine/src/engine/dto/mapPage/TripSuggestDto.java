package engine.dto.mapPage;

import java.util.List;

public class TripSuggestDto {
    private int suggestID;
    private List<String> passengersNames;
    private int tripDay;
    private String sourceStation;
    private String destinationStation;
    private double ratingAvg;
    private int ratingQuantity;
    private List<String> ratingNotes;

    public TripSuggestDto(int suggestId, List<String> passengersNames, int tripDay, String sourceStation, String destinationStation, double ratingAVG, int numOfRatings, List<String> literallyRatings) {
        this.suggestID = suggestId;
        this.passengersNames = passengersNames;
        this.tripDay = tripDay;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.ratingAvg = ratingAVG;
        this.ratingQuantity = numOfRatings;
        this.ratingNotes = literallyRatings;
    }
}

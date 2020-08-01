package engine.dto.mapPage;

import java.util.List;

public class TripSuggestDto {
    private int suggestId;
    private String passengersNames;
    private int tripDay;
    private String sourceStation;
    private String destinationStation;
    private double ratingAVG;
    private int numOfRatings;
    private String literallyRatings;

    public TripSuggestDto(int suggestId, List<String> passengersNames, int tripDay, String sourceStation, String destinationStation, double ratingAVG, int numOfRatings, List<String> literallyRatings) {
        this.suggestId = suggestId;
        this.passengersNames = createStringFromPassengersList(passengersNames);
        this.tripDay = tripDay;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.ratingAVG = ratingAVG;
        this.numOfRatings = numOfRatings;
        this.literallyRatings = createStringFromLiterallyRatings(literallyRatings);
    }

    private String createStringFromLiterallyRatings(List<String> literallyRatings) {
        StringBuilder str = new StringBuilder();
        str.append("Literally Ratings\n");
        for(String literallyRating : literallyRatings) {
            str.append(String.format("%s\n", literallyRating));
        }
        return str.toString();
    }

    private String createStringFromPassengersList(List<String> passengersNames) {
        StringBuilder str = new StringBuilder();
        str.append("Passengers: \n");
        for(String passenger : passengersNames) {
            str.append(passenger + ", ");
        }
        return str.toString();
    }
}

package engine.dto.mapPage;

public class TripRequestDto {
    private int requestId;
    private String tripOwnerName;
    private String sourceStation;
    private String destinationStation;
    private boolean isMatched;
    private String roadTrip;

    public TripRequestDto(int requestId, String tripOwnerName, String sourceStation, String destinationStation, boolean isMatched, String roadStory) {
        this.requestId = requestId;
        this.tripOwnerName = tripOwnerName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.isMatched = isMatched;
        this.roadTrip = roadStory;
    }
}

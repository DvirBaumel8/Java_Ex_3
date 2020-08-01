package engine.dto.mapPage;

public class TripRequestDto {
    private int requestId;
    private String tripOwnerName;
    private String sourceStation;
    private String destinationStation;
    private String isMatched;
    private String roadStory;

    public TripRequestDto(int requestId, String tripOwnerName, String sourceStation, String destinationStation, boolean isMatchedAlready, String roadStory) {
        this.requestId = requestId;
        this.tripOwnerName = tripOwnerName;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        if(isMatchedAlready) {
            this.isMatched = "Yes";
        }
        else {
            this.isMatched = "No";
        }
        this.roadStory = roadStory;
    }
}

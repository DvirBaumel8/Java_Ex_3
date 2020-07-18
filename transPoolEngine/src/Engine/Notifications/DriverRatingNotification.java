package Engine.Notifications;

public class DriverRatingNotification {
    private String ratedName;
    private int tripNum;
    private int rating;
    private String literalRating;

    public DriverRatingNotification(String ratedName, int tripNum, int rating, String literalRating) {
        this.ratedName = ratedName;
        this.tripNum = tripNum;
        this.rating = rating;
        this.literalRating = literalRating;
    }
}

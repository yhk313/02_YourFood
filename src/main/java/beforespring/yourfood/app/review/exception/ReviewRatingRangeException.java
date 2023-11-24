package beforespring.yourfood.app.review.exception;

public class ReviewRatingRangeException extends RuntimeException{

    public ReviewRatingRangeException() {
        super("Rating should be between 1 and 5.");
    }
}

package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

public class Rating {
    private double rating;
    private int countRatings;

    public Rating() {
        rating = 0.0;
        countRatings = 0;
    }

    public double getRating() {
        return rating;
    }

    public void addRating(double rating) {
        this.rating = ((this.rating * countRatings++) + rating) / countRatings;
    }
}

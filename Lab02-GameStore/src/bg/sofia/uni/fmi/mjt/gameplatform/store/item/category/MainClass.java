package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class MainClass implements StoreItem {
    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;
    private final Rating rating;

    public MainClass(String title, BigDecimal price, LocalDateTime releaseDate) {
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
        rating = new Rating();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public double getRating() {
        return rating.getRating();
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, BigDecimal.ROUND_UP);
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    //TODO
    @Override
    public void rate(double rating) {
        this.rating.addRating(rating);
    }
}

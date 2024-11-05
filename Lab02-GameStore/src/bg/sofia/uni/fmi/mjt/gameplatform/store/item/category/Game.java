package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import com.sun.tools.javac.Main;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends MainClass {
    private String genre;

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre) {
        super(title, price, releaseDate);
        setGenre(genre);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle extends MainClass {
    private Game[] games;

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games) {
        super(title, price, releaseDate);
        setGames(games);
    }

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }
}

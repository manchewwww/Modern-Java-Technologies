package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {

    public Glovo(char[][] mapLayout) {

    }

    void validClient(MapEntity client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
    }

    void validRestaurant(MapEntity restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
    }

    void validFoodItem(String foodItem) {
        if (foodItem == null) {
            throw new IllegalArgumentException("FoodItem cannot be null");
        }
        if (foodItem.isEmpty()) {
            throw new IllegalArgumentException("FoodItem cannot be empty");
        }
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {
        return null;
    }

}

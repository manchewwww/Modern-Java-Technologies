package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

public record Delivery(Location client, Location restaurant, Location deliveryGuy, String foodItem, double price,
                       int estimatedTime) {

    public Delivery {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if (deliveryGuy == null) {
            throw new IllegalArgumentException("DeliveryGuy cannot be null");
        }
        if (foodItem == null) {
            throw new IllegalArgumentException("FoodItem cannot be null");
        }
        if (foodItem.isEmpty()) {
            throw new IllegalArgumentException("FoodItem cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (estimatedTime < 0) {
            throw new IllegalArgumentException("EstimateTime cannot be negative");
        }
    }

}
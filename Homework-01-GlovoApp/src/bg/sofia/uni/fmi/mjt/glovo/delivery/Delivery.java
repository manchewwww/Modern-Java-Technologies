package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

public record Delivery(Location client, Location restaurant, Location deliveryGuy, String foodItem, double price,
                       int estimateTime) {

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
        if (estimateTime < 0) {
            throw new IllegalArgumentException("EstimateTime cannot be negative");
        }
    }

}

//public class Delivery {
//
//    private Location client;
//    private Location restaurant;
//    private Location deliveryGuy;
//    private String foodItem;
//    private double price;
//    private int estimateTime;
//
//    public Delivery(Location client, Location restaurant, Location deliveryGuy, String foodItem, double price,
//                    int estimatedTime) {
//        this.client = client;
//        this.restaurant = restaurant;
//        this.deliveryGuy = deliveryGuy;
//        this.foodItem = foodItem;
//        this.price = price;
//        this.estimateTime = estimatedTime;
//    }
//
//    public void setClient(Location client) {
//
//        this.client = client;
//    }
//
//    public void setRestaurant(Location restaurant) {
//
//        this.restaurant = restaurant;
//    }
//
//    public void setDeliveryGuy(Location deliveryGuy) {
//
//        this.deliveryGuy = deliveryGuy;
//    }
//
//    public void setFoodItem(String foodItem) {
//
//        this.foodItem = foodItem;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public void setEstimateTime(int estimateTime) {
//        this.estimateTime = estimateTime;
//    }
//
//    public Location getClient() {
//        return client;
//    }
//
//    public Location getRestaurant() {
//        return restaurant;
//    }
//
//    public Location getDeliveryGuy() {
//        return deliveryGuy;
//    }
//
//    public String getFoodItem() {
//        return foodItem;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public int getEstimateTime() {
//        return estimateTime;
//    }
//
//}

package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

public record DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType) {

    public DeliveryInfo {
        if (deliveryGuyLocation == null) {
            throw new IllegalArgumentException("deliveryGuyLocation cannot be null");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (estimatedTime < 0) {
            throw new IllegalArgumentException("EstimatedTime cannot be negative");
        }
        if (deliveryType == null) {
            throw new IllegalArgumentException("deliveryType cannot be null");
        }
    }
}

//public class DeliveryInfo {
//
//    private Location delieryGuyLocation;
//    private double price;
//    private int estimatedTime;
//    private DeliveryType deliveryType;
//
//    public DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType) {
//        this.delieryGuyLocation = deliveryGuyLocation;
//        this.price = price;
//        this.estimatedTime = estimatedTime;
//        this.deliveryType = deliveryType;
//    }
//
//    public DeliveryType getDeliveryType() {
//        return deliveryType;
//    }
//
//    public int getEstimatedTime() {
//        return estimatedTime;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public Location getDelieryGuyLocation() {
//        return delieryGuyLocation;
//    }
//
//}

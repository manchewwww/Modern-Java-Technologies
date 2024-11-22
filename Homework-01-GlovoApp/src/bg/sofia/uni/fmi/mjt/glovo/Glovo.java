package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenterApi;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMaximumPriceForDeliveryException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMaximumTimeForDeliveryException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {

    private static final int NO_COST_CONSTRAINS = -1;

    private final ControlCenterApi controlCenter;

    public Glovo(char[][] mapLayout) {
        controlCenter = new ControlCenter(mapLayout);
    }

//    public Glovo(ControlCenterApi controlCenter) {
//        this.controlCenter = controlCenter;
//    }

    private void validateClient(MapEntity client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        if (client.type() != MapEntityType.CLIENT) {
            throw new IllegalArgumentException("Client is not a client");
        }
    }

    private void validateRestaurant(MapEntity restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if (restaurant.type() != MapEntityType.RESTAURANT) {
            throw new IllegalArgumentException("Restaurant is not a restaurant");
        }
    }

    private void validateFoodItem(String foodItem) {
        if (foodItem == null) {
            throw new IllegalArgumentException("FoodItem cannot be null");
        }
        if (foodItem.isEmpty()) {
            throw new IllegalArgumentException("FoodItem cannot be empty");
        }
    }

    private void validateMaxPrice(double maxPrice) {
        if (maxPrice <= 0) {
            throw new InvalidMaximumPriceForDeliveryException("Price cannot be negative");
        }
    }

    private void validateMaxTime(int maxTime) {
        if (maxTime <= 0) {
            throw new InvalidMaximumTimeForDeliveryException("MaxTime cannot be negative");
        }
    }

    private void validateAll(MapEntity client, MapEntity restaurant, String foodItem) {
        validateClient(client);
        validateRestaurant(restaurant);
        validateFoodItem(foodItem);
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        validateAll(client, restaurant, foodItem);

        DeliveryInfo deliveryInfo =
            controlCenter.findOptimalDeliveryGuy(restaurant.location(), client.location(), NO_COST_CONSTRAINS,
                NO_COST_CONSTRAINS, ShippingMethod.CHEAPEST);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(client.location(), restaurant.location(), deliveryInfo.deliveryGuyLocation(), foodItem,
            deliveryInfo.price(), deliveryInfo.estimatedTime());
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        validateAll(client, restaurant, foodItem);

        DeliveryInfo deliveryInfo =
            controlCenter.findOptimalDeliveryGuy(restaurant.location(), client.location(), NO_COST_CONSTRAINS,
                NO_COST_CONSTRAINS, ShippingMethod.FASTEST);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(client.location(), restaurant.location(), deliveryInfo.deliveryGuyLocation(), foodItem,
            deliveryInfo.price(), deliveryInfo.estimatedTime());
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {
        validateAll(client, restaurant, foodItem);
        validateMaxPrice(maxPrice);

        DeliveryInfo deliveryInfo =
            controlCenter.findOptimalDeliveryGuy(restaurant.location(), client.location(), maxPrice, NO_COST_CONSTRAINS,
                ShippingMethod.FASTEST);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(client.location(), restaurant.location(), deliveryInfo.deliveryGuyLocation(), foodItem,
            deliveryInfo.price(), deliveryInfo.estimatedTime());
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {
        validateAll(client, restaurant, foodItem);
        validateMaxTime(maxTime);

        DeliveryInfo deliveryInfo =
            controlCenter.findOptimalDeliveryGuy(restaurant.location(), client.location(), NO_COST_CONSTRAINS, maxTime,
                ShippingMethod.CHEAPEST);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy");
        }

        return new Delivery(client.location(), restaurant.location(), deliveryInfo.deliveryGuyLocation(), foodItem,
            deliveryInfo.price(), deliveryInfo.estimatedTime());
    }

}

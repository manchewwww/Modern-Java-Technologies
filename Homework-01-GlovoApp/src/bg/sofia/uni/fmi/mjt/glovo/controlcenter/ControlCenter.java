package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoPriceCheapToExpensiveComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoTimeEstimateFastToSlowComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ControlCenter implements ControlCenterApi {

    private final MapEntity[][] mapEntities;
    private final List<Location> suppliers;

    public ControlCenter(char[][] mapLayout) {
        mapEntities = new MapEntity[mapLayout.length][mapLayout[0].length];
        suppliers = new ArrayList<>();
        parseMap(mapLayout);
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {
        validateRestaurantLocation(restaurantLocation);
        validateClientLocation(clientLocation);
        validateShippingMethod(shippingMethod);

        Set<DeliveryInfo> deliveryInfoSet;
        if (shippingMethod == ShippingMethod.CHEAPEST) {
            deliveryInfoSet = new TreeSet<>(new DeliveryInfoPriceCheapToExpensiveComparator());
        } else {
            deliveryInfoSet = new TreeSet<>(new DeliveryInfoTimeEstimateFastToSlowComparator());
        }

        addDeliveryInfoSet(deliveryInfoSet, restaurantLocation, clientLocation, maxPrice, maxTime);

        return deliveryInfoSet.isEmpty() ? null : deliveryInfoSet.iterator().next();

    }

    @Override
    public MapEntity[][] getLayout() {
        return mapEntities;
    }

    private void parseMap(char[][] mapLayout) {
        for (int i = 0; i < mapEntities.length; i++) {
            for (int j = 0; j < mapEntities[i].length; j++) {
                MapEntityType type = MapEntityType.of(mapLayout[i][j]);

                if (type == MapEntityType.DELIVERY_GUY_CAR || type == MapEntityType.DELIVERY_GUY_BIKE) {
                    suppliers.add(new Location(i, j));
                }

                mapEntities[i][j] = new MapEntity(new Location(i, j), type);
            }
        }
    }

    private void validateRestaurantLocation(Location restaurantLocation) {
        if (restaurantLocation.x() >= mapEntities.length || restaurantLocation.y() >= mapEntities[0].length
            || restaurantLocation.x() < 0 || restaurantLocation.y() < 0) {
            throw new InvalidOrderException("Restaurant location is out of map");
        }

        if (mapEntities[restaurantLocation.x()][restaurantLocation.y()].type() != MapEntityType.RESTAURANT) {
            throw new InvalidOrderException("In this location in map is other object not a restaurant");
        }
    }

    private void validateClientLocation(Location clientLocation) {
        if (clientLocation.x() >= mapEntities.length || clientLocation.y() >= mapEntities[0].length
            || clientLocation.x() < 0 || clientLocation.y() < 0) {
            throw new InvalidOrderException("Client location is out of map");
        }

        if (mapEntities[clientLocation.x()][clientLocation.y()].type() != MapEntityType.CLIENT) {
            throw new InvalidOrderException("In this location in map is other object not a client");
        }
    }

    private void validateShippingMethod(ShippingMethod shippingMethod) {
        if (shippingMethod == null) {
            throw new IllegalArgumentException("Shipping Method is null");
        }
    }

    private void addDeliveryInfoSet(Set<DeliveryInfo> deliveryInfoSet, Location restaurantLocation,
                                    Location clientLocation, double maxPrice, int maxTime) {
        BFS bfs = new BFS(mapEntities);
        for (Location supplier : suppliers) {
            int distanceToRestaurant = bfs.searchPathFromStartToEnd(supplier, restaurantLocation);
            if (distanceToRestaurant == -1) {
                continue;
            }

            int distanceToClient = bfs.searchPathFromStartToEnd(restaurantLocation, clientLocation);
            if (distanceToClient == -1) {
                continue;
            }

            int totalDistance = distanceToRestaurant + distanceToClient;
            DeliveryType deliveryType =
                mapEntities[supplier.x()][supplier.y()].type() == MapEntityType.DELIVERY_GUY_CAR ? DeliveryType.CAR :
                    DeliveryType.BIKE;
            double priceForDelivery = totalDistance * deliveryType.getPricePerKm();
            int timeForDelivery = totalDistance * deliveryType.getTimePerKm();

            if (maxTime != -1 && timeForDelivery > maxTime) {
                continue;
            }
            if (maxPrice != -1 && priceForDelivery > maxPrice) {
                continue;
            }

            deliveryInfoSet.add(new DeliveryInfo(supplier, priceForDelivery, timeForDelivery, deliveryType));
        }
    }

}

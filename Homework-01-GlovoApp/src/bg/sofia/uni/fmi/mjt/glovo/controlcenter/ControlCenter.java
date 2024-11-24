package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoPriceCheapToExpensiveComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfoTimeEstimateFastToSlowComparator;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    private void parseMap(char[][] mapLayout) {
        for (int i = 0; i < mapEntities.length; i++) {
            for (int j = 0; j < mapEntities[i].length; j++) {
                MapEntityType type = switch (mapLayout[i][j]) {
                    case '#' -> MapEntityType.WALL;
                    case '.' -> MapEntityType.ROAD;
                    case 'R' -> MapEntityType.RESTAURANT;
                    case 'C' -> MapEntityType.CLIENT;
                    case 'A' -> {
                        suppliers.add(new Location(i, j));
                        yield MapEntityType.DELIVERY_GUY_CAR;
                    }
                    case 'B' -> {
                        suppliers.add(new Location(i, j));
                        yield MapEntityType.DELIVERY_GUY_BIKE;
                    }
                    default -> throw new InvalidMapSymbolException(
                        "Symbol in map is invalid: " + mapLayout[i][j] + "in position: x: " + i + ", y: " + j);
                };
                mapEntities[i][j] = new MapEntity(new Location(i, j), type);
            }
        }
    }

    private boolean isWalkable(MapEntityType type) {
        return type == MapEntityType.RESTAURANT || type == MapEntityType.ROAD
            || type == MapEntityType.CLIENT || type == MapEntityType.DELIVERY_GUY_CAR
            || type == MapEntityType.DELIVERY_GUY_BIKE;
    }

    private int calculateDistance(Location start, Location end) {
        int rows = mapEntities.length;
        int cols = mapEntities[0].length;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        Queue<Location> queue = new LinkedList<Location>();
        boolean[][] visited = new boolean[rows][cols];

        int direction = 0;

        visited[start.x()][start.y()] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                Location location = queue.poll();

                if (location.equals(end)) {
                    return direction;
                }

                for (int j = 0; j < directions.length; j++) {
                    int newX = location.x() + directions[j][0];
                    int newY = location.y() + directions[j][1];

                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY]
                        && isWalkable(mapEntities[newX][newY].type())) {
                        visited[newX][newY] = true;
                        queue.add(new Location(newX, newY));
                    }
                }
            }

            direction++;
        }

        return -1;
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
        for (Location supplier : suppliers) {
            int distanceToRestaurant = calculateDistance(supplier, restaurantLocation);
            if (distanceToRestaurant == -1) {
                continue;
            }

            int distanceToClient = calculateDistance(restaurantLocation, clientLocation);
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

}

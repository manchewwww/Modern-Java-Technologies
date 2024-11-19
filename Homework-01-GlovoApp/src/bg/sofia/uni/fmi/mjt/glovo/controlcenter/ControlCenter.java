package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

public class ControlCenter implements ControlCenterApi {

    public ControlCenter(char[][] mapLayout) {

    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice,
                                               int maxTime, ShippingMethod shippingMethod) {
        return null;
    }

    @Override
    public MapEntity[][] getLayout() {
        return new MapEntity[][];
    }

}

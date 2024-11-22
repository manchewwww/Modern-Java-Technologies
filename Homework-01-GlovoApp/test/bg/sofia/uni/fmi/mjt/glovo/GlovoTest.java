package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenterApi;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMaximumPriceForDeliveryException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMaximumTimeForDeliveryException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlovoTest {

    private Glovo glovo;

    @BeforeEach
    void setUp() {
//        controlCenter = mock();
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 1), new Location(3, 1), -1, -1,
//            ShippingMethod.CHEAPEST)).thenThrow(new InvalidOrderException("On this position is not restaurant"));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(2, 1), -1, -1,
//            ShippingMethod.CHEAPEST)).thenThrow(new InvalidOrderException("On this position is not client"));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(2, 3), new Location(3, 1), -1, -1,
//            ShippingMethod.CHEAPEST)).thenReturn(null);
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, -1,
//            ShippingMethod.CHEAPEST)).thenReturn(new DeliveryInfo(new Location(1, 2), 15, 25, DeliveryType.BIKE));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), 20, -1,
//            ShippingMethod.FASTEST)).thenReturn(new DeliveryInfo(new Location(1, 2), 15, 25, DeliveryType.BIKE));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), 7, -1,
//            ShippingMethod.FASTEST)).thenReturn(null);
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, -1,
//            ShippingMethod.FASTEST)).thenReturn(new DeliveryInfo(new Location(3, 3), 30, 18, DeliveryType.CAR));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, 20,
//            ShippingMethod.CHEAPEST)).thenReturn(new DeliveryInfo(new Location(3, 3), 30, 18, DeliveryType.CAR));
//
//        when(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, 7,
//            ShippingMethod.CHEAPEST)).thenReturn(null);
//
//        glovo = new Glovo(controlCenter);

        char[][] layout = {
            {'#', '#', '#', '.', '#'},
            {'#', '.', 'B', 'R', '.'},
            {'.', '.', '#', '.', '#'},
            {'#', 'C', '.', 'A', '.'},
            {'#', '.', '#', '#', '#'}
        };

        glovo = new Glovo(layout);
    }



    @Test
    public void testGetCheapestDeliveryWithNullClient() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(null, new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC"),
            "When client is null program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDeliveryWithClientIsNotClient() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC"),
            "When client is not client program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDeliveryWithNullRestaurant() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(new MapEntity(new Location(3, 1),MapEntityType.CLIENT), null, "KFC"),
            "When restaurant is null program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDeliveryWithRestaurantIsNotRestaurant() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.CLIENT), "KFC"),
            "When restaurant is not restaurant program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDeliveryWithNullFoodName() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), null),
            "When food item is null program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDeliveryWithEmptyFoodName() {
        assertThrows(IllegalArgumentException.class,
            () -> glovo.getCheapestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), ""),
            "When food item is empty program expect IllegalArgumentException");
    }

    @Test
    public void testGetCheapestDelivery() throws NoAvailableDeliveryGuyException {
        assertEquals(new Delivery(new Location(3, 1), new Location(1, 3), new Location(1, 2), "KFC", 15, 25),
            glovo.getCheapestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC"),
            "Get cheapest delivery return incorrect answer for delivery");
    }

//    @Test
//    public void testGetCheapestDeliveryWithNoOneAvailableDeliveryGuy() {
//        assertThrows(NoAvailableDeliveryGuyException.class,
//            () -> glovo.getCheapestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
//                new MapEntity(new Location(2, 3), MapEntityType.RESTAURANT), "KFC"),
//            "Get cheapest delivery expect exception when no one delivery guy is available");
//    }

    @Test
    public void testGetFastestDelivery() throws NoAvailableDeliveryGuyException {
        assertEquals(new Delivery(new Location(3, 1), new Location(1, 3), new Location(3, 3), "KFC", 30, 18),
            glovo.getFastestDelivery(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC"),
            "Get fastest delivery return incorrect answer for delivery");
    }

//    @Test
//    public void testGetFastestDeliveryWithNoOneAvailableDeliveryGuy() {
//        assertThrows(NoAvailableDeliveryGuyException.class,
//            () -> glovo.getFastestDelivery(new MapEntity(new Location(3, 1), MapEntityType.Client),
//                new MapEntity(new Location(2, 3), MapEntityType.Restaurant), "KFC"),
//            "Get fastest delivery expect exception when no one delivery guy is available");
//    }

    @Test
    public void testGetFastestDeliveryUnderPriceWithInvalidMaxPrice() {
        assertThrows(InvalidMaximumPriceForDeliveryException.class,
            () -> glovo.getFastestDeliveryUnderPrice(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", -5),
            "Price in get fastest delivery under price must be bigger than 0");
    }

    @Test
    public void testGetFastestDeliveryUnderPrice() throws NoAvailableDeliveryGuyException {
        assertEquals(new Delivery(new Location(3, 1), new Location(1, 3), new Location(1, 2), "KFC", 15, 25),
            glovo.getFastestDeliveryUnderPrice(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", 20),
            "Get fastest delivery under price return incorrect answer for delivery");
    }

    @Test
    public void testGetFastestDeliveryUnderPriceWithNoOneAvailableDeliveryGuy() {
        assertThrows(NoAvailableDeliveryGuyException.class,
            () -> glovo.getFastestDeliveryUnderPrice(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", 7),
            "Get fastest delivery under price expect exception when no one delivery guy is available");
    }

    @Test
    public void testGetCheapestDeliveryUnderTimeWithInvalidMaxTime() {
        assertThrows(InvalidMaximumTimeForDeliveryException.class,
            () -> glovo.getCheapestDeliveryWithinTimeLimit(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", -5),
            "Time in get cheapest delivery under time must be bigger than 0");
    }

    @Test
    public void testGetCheapestDeliveryUnderTime() throws NoAvailableDeliveryGuyException {
        assertEquals(new Delivery(new Location(3, 1), new Location(1, 3), new Location(3, 3), "KFC", 30, 18),
            glovo.getCheapestDeliveryWithinTimeLimit(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", 20),
            "Get cheapest delivery under time return incorrect answer for delivery");
    }

    @Test
    public void testGetCheapestDeliveryUnderTimeWithNoOneAvailableDeliveryGuy() {
        assertThrows(NoAvailableDeliveryGuyException.class,
            () -> glovo.getCheapestDeliveryWithinTimeLimit(new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT), "KFC", 7),
            "Get cheapest delivery under time expect exception when no one delivery guy is available");
    }

}

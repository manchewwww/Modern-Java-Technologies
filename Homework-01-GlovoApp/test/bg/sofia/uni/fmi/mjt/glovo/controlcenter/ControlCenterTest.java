package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControlCenterTest {

    private ControlCenter controlCenter;

    @BeforeEach
    void setUp() {
        char[][] layout = {
            {'#', '#', '#', '.', '#'},
            {'#', '.', 'B', 'R', '.'},
            {'.', '.', '#', '.', '#'},
            {'#', 'C', '.', 'A', '.'},
            {'#', '.', '#', '#', '#'}
        };

        controlCenter = new ControlCenter(layout);
    }

    @Test
    void testCreateControlCenterWithInvalidSymbol() {
        char[][] layout = {
            {'#', '#', '1', '.', '#'},
            {'#', '.', 'B', 'R', '.'},
            {'.', '.', '#', '.', '#'},
            {'#', 'C', '.', 'A', '.'},
            {'#', '.', '#', '#', '#'}
        };

        assertThrows(InvalidMapSymbolException.class, () -> controlCenter = new ControlCenter(layout), "Invalid symbol in map must throw exception");
    }

    @Test
    void testGetLayout() {
        MapEntity[][] mapEntities = {
            {
//                {'#', '#', '#', '.', '#'},
                new MapEntity(new Location(0, 0), MapEntityType.WALL),
                new MapEntity(new Location(0, 1), MapEntityType.WALL),
                new MapEntity(new Location(0, 2), MapEntityType.WALL),
                new MapEntity(new Location(0, 3), MapEntityType.ROAD),
                new MapEntity(new Location(0, 4), MapEntityType.WALL)
            },
            {
//                {'#', '.', 'B', 'R', '.'},
                new MapEntity(new Location(1, 0), MapEntityType.WALL),
                new MapEntity(new Location(1, 1), MapEntityType.ROAD),
                new MapEntity(new Location(1, 2), MapEntityType.DELIVERY_GUY_BIKE),
                new MapEntity(new Location(1, 3), MapEntityType.RESTAURANT),
                new MapEntity(new Location(1, 4), MapEntityType.ROAD)
            },
            {
//                {'.', '.', '#', '.', '#'},
                new MapEntity(new Location(2, 0), MapEntityType.ROAD),
                new MapEntity(new Location(2, 1), MapEntityType.ROAD),
                new MapEntity(new Location(2, 2), MapEntityType.WALL),
                new MapEntity(new Location(2, 3), MapEntityType.ROAD),
                new MapEntity(new Location(2, 4), MapEntityType.WALL)
            },
            {
//                {'#', 'C', '.', 'A', '.'},
                new MapEntity(new Location(3, 0), MapEntityType.WALL),
                new MapEntity(new Location(3, 1), MapEntityType.CLIENT),
                new MapEntity(new Location(3, 2), MapEntityType.ROAD),
                new MapEntity(new Location(3, 3), MapEntityType.DELIVERY_GUY_CAR),
                new MapEntity(new Location(3, 4), MapEntityType.ROAD)
            },
            {
//                {'#', '.', '#', '#', '#'}
                new MapEntity(new Location(4, 0), MapEntityType.WALL),
                new MapEntity(new Location(4, 1), MapEntityType.ROAD),
                new MapEntity(new Location(4, 2), MapEntityType.WALL),
                new MapEntity(new Location(4, 3), MapEntityType.WALL),
                new MapEntity(new Location(4, 4), MapEntityType.WALL)
            },
        };

        assertTrue(Arrays.deepEquals(mapEntities, controlCenter.getLayout()),
            "Two Map entity arrays must be equals but in creating Control center make incorrect Map entities");
    }

    @Test
    void testFindOptimalDeliveryGuyWithOutOfMapRestaurantLocation() {
        assertThrows(InvalidOrderException.class,
            () -> controlCenter.findOptimalDeliveryGuy(new Location(5, 5), new Location(3, 1), -1, -1,
                ShippingMethod.CHEAPEST),
            "When restaurant location is out of map program expect InvalidOrderException");
    }

    @Test
    void testFindOptimalDeliveryGuyWithOutOfMapClientLocation() {
        assertThrows(InvalidOrderException.class,
            () -> controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 7), -1, -1,
                ShippingMethod.CHEAPEST), "When client location is out of map program expect InvalidOrderException");
    }

    @Test
    void testFindOptimalDeliveryGuyWithInvalidShippingMethod() {
        assertThrows(IllegalArgumentException.class,
            () -> controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, -1,
                null), "When shipping method is null program expect IllegalArgumentException");
    }

    @Test
    void testFindOptimalDeliveryGuyCheapest() {
        assertEquals(new DeliveryInfo(new Location(1, 2), 15, 25, DeliveryType.BIKE),
            controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, -1,
                ShippingMethod.CHEAPEST));
    }

    @Test
    void testFindOptimalDeliveryGuyCheapestUnderTime() {
        assertEquals(new DeliveryInfo(new Location(3, 3), 30, 18, DeliveryType.CAR),
            controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, 20,
                ShippingMethod.CHEAPEST));
    }

    @Test
    void testFindOptimalDeliveryGuyCheapestUnderTimeWithNoOneUnderThisTime() {
        assertNull(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, 15,
            ShippingMethod.CHEAPEST));
    }

    @Test
    void testFindOptimalDeliveryGuyFastest() {
        assertEquals(new DeliveryInfo(new Location(3, 3), 30, 18, DeliveryType.CAR),
            controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), -1, -1,
                ShippingMethod.FASTEST));
    }

    @Test
    void testFindOptimalDeliveryGuyFastestUnderPrice() {
        assertEquals(new DeliveryInfo(new Location(1, 2), 15, 25, DeliveryType.BIKE),
            controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), 19, -1,
                ShippingMethod.FASTEST));
    }

    @Test
    void testFindOptimalDeliveryGuyFastestUnderPriceWithNoOneUnderThisTime() {
        assertNull(controlCenter.findOptimalDeliveryGuy(new Location(1, 3), new Location(3, 1), 1, -1,
            ShippingMethod.FASTEST));
    }

}

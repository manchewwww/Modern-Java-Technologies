package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NationMedalComparatorTest {

    private MJTOlympics mjtOlympics = mock();

    @Test
    void testComparingSmallerWithBigger() {
        when(mjtOlympics.getTotalMedals("Bulgarian")).thenReturn(100);
        when(mjtOlympics.getTotalMedals("Turkish")).thenReturn(1);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(mjtOlympics);
        assertEquals(1, nationMedalComparator.compare("Turkish", "Bulgarian"),
            "Comparing smaller with bigger return incorrect result");
    }

    @Test
    void testComparingBiggerWithSmaller() {
        when(mjtOlympics.getTotalMedals("Bulgarian")).thenReturn(2);
        when(mjtOlympics.getTotalMedals("Turkish")).thenReturn(1);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(mjtOlympics);
        assertEquals(-1, nationMedalComparator.compare("Bulgarian", "Turkish"),
            "Comparing bigger with smaller return incorrect result");
    }

    @Test
    void testComparingWithSameMedals() {
        when(mjtOlympics.getTotalMedals("Turkish")).thenReturn(2);
        when(mjtOlympics.getTotalMedals("Bulgarian")).thenReturn(2);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(mjtOlympics);
        assertFalse(0 < nationMedalComparator.compare("Bulgarian", "Turkish"),
            "Comparing two diff nationality with same medals need to return this with bigger name but return other");
    }

    @Test
    void testComparingWithSameMedalsAndSameName() {
        when(mjtOlympics.getTotalMedals("Bulgarian")).thenReturn(2);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(mjtOlympics);
        assertEquals(0, nationMedalComparator.compare("Bulgarian", "Bulgarian"),
            "Comparing two nationality with same medals and names expect zero but it is other answer");
    }

}

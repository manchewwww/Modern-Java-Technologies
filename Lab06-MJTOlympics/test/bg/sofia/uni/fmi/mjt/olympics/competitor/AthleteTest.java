package bg.sofia.uni.fmi.mjt.olympics.competitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AthleteTest {

    @Test
    void testCreateAthlete() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        assertEquals(competitor.getIdentifier(), "Competitor 1", "Athlete identifier is set incorrect");
        assertEquals(competitor.getName(), "Georgi", "Athlete name is set incorrect");
        assertEquals(competitor.getNationality(), "Bulgarian", "Athlete nationality is set incorrect");
    }

    @Test
    void testMedalCreating() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        assertEquals(0, competitor.getMedals().size(), "Medal is created incorrectly");
    }

    @Test
    void testMedalAdding() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        competitor.addMedal(Medal.GOLD);
        assertEquals(1, competitor.getMedals().size(),
            "Medal size need to be 1 after adding one medal but it is not!");
    }

    @Test
    void testMedalAddingSameMedals() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        competitor.addMedal(Medal.GOLD);
        competitor.addMedal(Medal.GOLD);
        assertEquals(2, competitor.getMedals().size(),
            "Medal size need to be 2 after adding two same medals but it is not!");
    }

    @Test
    void testAddingNullMedal() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        competitor.addMedal(Medal.GOLD);
        assertThrows(IllegalArgumentException.class, () -> competitor.addMedal(null), "When medal is null expect Invalid argument exception");
    }

    @Test
    void testAddingImaginaryMedal() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        assertThrows(IllegalArgumentException.class, () -> competitor.addMedal(Medal.valueOf("UNKNOWN")),
            "Adding of imaginary medal need to fail with IllegalArgumentException");
    }

    @Test
    void testEqualsToSameAthlete() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        assertEquals(competitor, competitor, "Athlete equals failed but this is same athlete");
    }

    @Test
    void testEqualsToDifferentAthleteWithSameCharacteristics() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        Competitor competitor1 = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        assertEquals(competitor, competitor1, "Athlete equals failed but athletes are with same characteristics");
    }


    //first mistake in code because this return this different athlete are same
    @Test
    void testEqualsToDifferentAthletes() {
        Competitor competitor = new Athlete("Competitor 1", "Georgi", "Bulgarian");
        Competitor competitor1 = new Athlete("Competitor 2", "Georgi", "Bulgarian");
        assertNotEquals(competitor, competitor1, "Athlete are with different characteristics but return they are same");
    }

}

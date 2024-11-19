package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompetitionTest {

    @Test
    void testCreationWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Competition(null, "R", new HashSet<>()),
            "When competition name is null this expect to throw InvalidArgumentException");
    }

    @Test
    void testCreationWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("", "R", new HashSet<>()),
            "When competition name is empty this expect to throw InvalidArgumentException");
    }

    @Test
    void testCreationWithNullDiscipline() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("Name", null, new HashSet<>()),
            "When competition discipline is null this expect to throw InvalidArgumentException");
    }

    @Test
    void testCreationWithEmptyDiscipline() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("Name", "", new HashSet<>()),
            "When competition discipline is empty this expect to throw InvalidArgumentException");
    }

    @Test
    void testCreationWithNullCompetitors() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("Name", "Dis", null),
            "When competition competitors are null this expect to throw InvalidArgumentException");
    }

    @Test
    void testCreationWithEmptyCompetitors() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("Name", "Dis", new HashSet<>()),
            "When competition competitors are empty this expect to throw InvalidArgumentException");
    }

    @Test
    void testCompetitionCreationWithOneCompetitor() {
        Set<Competitor> competitorSet = new HashSet<>();
        competitorSet.add(new Athlete("Competitor", "Georgi", "Bulgarian"));

        Competition competition = new Competition("Olympic", "Running", competitorSet);

        assertEquals(1, competition.competitors().size(), "Competitors of competition is set incorrect");
    }

    @Test
    void testEqualsCompetition() {
        Set<Competitor> competitorSet = new HashSet<>();
        competitorSet.add(new Athlete("Competitor", "Georgi", "Bulgarian"));

        Competition competition1 = new Competition("Olympic", "Running", competitorSet);
        Competition competition2 = new Competition("Olympic", "Running", competitorSet);

        assertEquals(competition1, competition2,
            "Competition equals return false but expected true because competitions are equals");
    }

    @Test
    void testNotEqualsCompetition() {
        Set<Competitor> competitorSet = new HashSet<>();
        competitorSet.add(new Athlete("Competitor", "Georgi", "Bulgarian"));

        Competition competition1 = new Competition("Olympic", "Running", competitorSet);
        Competition competition2 = new Competition("Olympic 2", "Swimming", competitorSet);

        assertNotEquals(competition1, competition2,
            "Competition equals return true but expected false because competitions are different");
    }

}

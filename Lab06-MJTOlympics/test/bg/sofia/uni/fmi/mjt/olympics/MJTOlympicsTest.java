package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.AthleteComparator;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MJTOlympicsTest {

    private MJTOlympics mjtOlympics;


    @BeforeEach
    void setUp() {
        CompetitionResultFetcher competitionResultFetcher = mock();
        Competitor competitor1 = new Athlete("1", "Georgi", "Bulgarian");
        Competitor competitor2 = new Athlete("2", "Rado", "Turkish");
        Competitor competitor3 = new Athlete("3", "Pesho", "Italian");
        Competition competition = mock();

        TreeSet<Competitor> ranking = new TreeSet<>(new AthleteComparator());
        ranking.add(competitor1);
        ranking.add(competitor2);
        ranking.add(competitor3);

        when(competition.competitors()).thenReturn(ranking);
        when(competitionResultFetcher.getResult(any())).thenReturn(ranking);

        mjtOlympics = new MJTOlympics(ranking, competitionResultFetcher);
        mjtOlympics.updateMedalStatistics(competition);
    }

    @Test
    void testUpdateMedalStatistic() {
        assertEquals(1, mjtOlympics.getTotalMedals("Bulgarian"),
            "Nation update medal statistics does not working correct because expect one medal for Bulgaria but return other answer");
        assertEquals(1, mjtOlympics.getTotalMedals("Turkish"),
            "Nation update medal statistics does not working correct because expect one medal for Turkish but return other answer");
        assertEquals(1, mjtOlympics.getTotalMedals("Italian"),
            "Nation update medal statistics does not working correct because expect one medal for Italian but return other answer");
    }

    @Test
    void testGetNationsRankList() {
        assertEquals(3, mjtOlympics.getNationsRankList().size(), "Nations rank list is different from expected");
    }

}

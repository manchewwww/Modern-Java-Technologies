package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Comparator;

public class AthleteComparator implements Comparator<Competitor> {

    public int compare(Competitor a, Competitor b) {
        int compareInt = Integer.compare(a.getMedals().size(), b.getMedals().size());
        if (compareInt != 0) {
            return compareInt;
        }

        return a.getIdentifier().compareTo(b.getIdentifier());
    }

}

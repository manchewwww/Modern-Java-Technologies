package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.util.Comparator;

public class EventComparator implements Comparator<Event<?>> {

    @Override
    public int compare(Event o1, Event o2) {
        int compareNumber = Integer.compare(o1.getPriority(), o2.getPriority());

        if (compareNumber == 0) {
            return o1.getTimestamp().compareTo(o2.getTimestamp());
        }
        return compareNumber;
    }

}

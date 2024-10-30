package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import java.time.Duration;
import java.time.LocalDateTime;

public class RentalPeriod {
    private long seconds;
    private long days;
    private long hours;
    private long weeks;

    public RentalPeriod(LocalDateTime start, LocalDateTime end) {
        Duration period = Duration.between(start, end);
        this.seconds = period.getSeconds();
        this.weeks = seconds / (3600 * 24 * 7);
        seconds %= (3600 * 24 * 7);
        this.days = seconds / (3600 * 24);
        seconds %= (3600 * 24);
        this.hours = seconds / 3600;
        seconds %= 3600;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getWeeks() {
        return weeks;
    }
}

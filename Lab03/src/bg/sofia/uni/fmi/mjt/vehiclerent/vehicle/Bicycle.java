package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public final class Bicycle extends Vehicle {

    PriceManage priceManage;

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model);
        priceManage = new PriceManage(pricePerDay, pricePerHour);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if (endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("End date is before start date.");
        }

        Duration duration = Duration.between(startOfRent, endOfRent);
        long seconds = duration.getSeconds();
        int days = (int) seconds / (3600 * 24);
        seconds %= (3600 * 24);
        long hours = seconds / 3600;
        seconds %= 3600;

        if(days >=7) {
            throw new InvalidRentingPeriodException("Bicycle max rental period is 6 days, 23 hours and 59 minutes. ");
        }
        double price = hours * priceManage.pricePerHour() + days * priceManage.pricePerDay();
        return seconds != 0 ? price + priceManage.pricePerHour() : price;
    }
}

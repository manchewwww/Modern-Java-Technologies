package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import com.sun.net.httpserver.Request;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public final class Bicycle extends Vehicle {
    private final RentalRate rentalRate;
    private RentalPeriod rentalPeriod;

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model);
        rentalRate = new RentalRate(pricePerDay, pricePerHour);
    }

    private double calculatePrice(final RentalPeriod rentalPeriod) {
        double price =
                rentalPeriod.getHours() * rentalRate.getPricePerHour() +
                        rentalPeriod.getDays() * rentalRate.getPricePerDay();

        return rentalPeriod.getSeconds() != 0 ? price + rentalRate.getPricePerHour() : price;
    }

    @Override
    public void validPeriodOfTime(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        this.rentalPeriod = new RentalPeriod(startOfRent,endOfRent);
        if (endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("End rent period is before start rent period!");
        }
        if (rentalPeriod.getWeeks() != 0) {
            throw new InvalidRentingPeriodException("Bicycle max rental period is 6 days, 23 hours and 59 minutes. ");
        }
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        validPeriodOfTime(startOfRent, endOfRent);

        return calculatePrice(rentalPeriod);
    }
}

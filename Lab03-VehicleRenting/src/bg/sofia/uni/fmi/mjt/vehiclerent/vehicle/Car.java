package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.AgeGroup;
import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Car extends Vehicle {
    private final VehicleRentalDetails vehicleRentalDetails;
    private RentalPeriod rentalPeriod;

    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        vehicleRentalDetails = new VehicleRentalDetails(fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
    }

    private double calculatePrice(final RentalPeriod rentalPeriod) {
        double price =
                vehicleRentalDetails.getFuelType().getFeePerDay() * rentalPeriod.getDays() +
                        VehicleRentalDetails.PRICE_FOR_SEAT * vehicleRentalDetails.getNumberOfSeats() +
                        vehicleRentalDetails.getPricePerWeek() * rentalPeriod.getWeeks() +
                        vehicleRentalDetails.getPricePerDay() * rentalPeriod.getDays() +
                        vehicleRentalDetails.getPricePerHour() * rentalPeriod.getHours() +
                        super.getDriver().ageGroup().getFee();

        return rentalPeriod.getSeconds() != 0 ?
                price + vehicleRentalDetails.getPricePerHour() + vehicleRentalDetails.getFuelType().getFeePerDay() :
                price;
    }

    @Override
    public void validPeriodOfTime(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        this.rentalPeriod = new RentalPeriod(startOfRent,endOfRent);
        if (endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("End rent period is before start rent period!");
        }
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        validPeriodOfTime(startOfRent, endOfRent);

        return calculatePrice(rentalPeriod);
    }

}

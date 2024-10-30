package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Caravan extends Vehicle {
    private final static int PRICE_FOR_BED = 10;
    private final int numberOfBeds;
    private final VehicleRentalDetails vehicleRentalDetails;
    private RentalPeriod rentalPeriod;

    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.numberOfBeds = numberOfBeds;
        vehicleRentalDetails = new VehicleRentalDetails(fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
    }

    private double calculatePrice(final RentalPeriod rentalPeriod) {
        double price =
                vehicleRentalDetails.getFuelType().getFeePerDay() * rentalPeriod.getDays() +
                        VehicleRentalDetails.PRICE_FOR_SEAT * vehicleRentalDetails.getNumberOfSeats() +
                        PRICE_FOR_BED * numberOfBeds +
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
        if (rentalPeriod.getDays() < 1 && rentalPeriod.getWeeks() == 0) {
            throw new InvalidRentingPeriodException("Caravan minimum rental period is 24 hours. ");
        }
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        validPeriodOfTime(startOfRent, endOfRent);

        return calculatePrice(rentalPeriod);
    }
}

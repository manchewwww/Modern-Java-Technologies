package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Caravan extends Vehicle {
    private final static int PRICE_FOR_BED = 10;
    private final static int PRICE_FOR_SEAT = 5;

    private FuelType fuelType;
    private int numberOfSeats;
    private int numberOfBeds;
    private PriceManage priceManage;
    private double pricePerWeek;


    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        setFuelType(fuelType);
        setNumberOfSeats(numberOfSeats);
        setNumberOfBeds(numberOfBeds);
        setPricePerWeek(pricePerWeek);
        setPriceManage(new PriceManage(pricePerDay, pricePerHour));
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setPricePerWeek(double pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }

    public void setPriceManage(PriceManage priceManage) {
        this.priceManage = priceManage;
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
        long weeks = days / 7;
        seconds %= 3600;


        if (days < 1) {
            throw new InvalidRentingPeriodException("Caravan minimum rental period is 24 hours. ");
        }
        double price = (fuelType.getFeePerDay()) * days + numberOfSeats * PRICE_FOR_SEAT + numberOfBeds * PRICE_FOR_BED +
                weeks * pricePerWeek + days * priceManage.pricePerDay() + hours * priceManage.pricePerHour()
                + super.getDriver().ageGroup().getFee();
        return seconds != 0 ? price + priceManage.pricePerHour() + fuelType.getFeePerDay() : price;
    }
}

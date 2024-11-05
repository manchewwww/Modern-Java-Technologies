package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

public class VehicleRentalDetails extends RentalRate {
    public final static int PRICE_FOR_SEAT = 5;
    private final FuelType fuelType;
    private final int numberOfSeats;
    private final double pricePerWeek;

    public VehicleRentalDetails(FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(pricePerDay, pricePerHour);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public double getPricePerWeek() {
        return pricePerWeek;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}

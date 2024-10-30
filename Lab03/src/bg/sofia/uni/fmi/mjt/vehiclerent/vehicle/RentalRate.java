package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

public class RentalRate {
    private final double pricePerHour;
    private final double pricePerDay;

    public RentalRate(double pricePerDay, double pricePerHour) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
}

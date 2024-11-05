package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

public enum FuelType {
    DIESEL(3),
    PETROL(3),
    HYBRID(1),
    ELECTRICITY(0),
    HYDROGEN(0);

    private final int feePerDay;

    FuelType(int feePerDay) {
        this.feePerDay = feePerDay;
    }

    public int getFeePerDay() {
        return feePerDay;
    }


}

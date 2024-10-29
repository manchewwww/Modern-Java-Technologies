package bg.sofia.uni.fmi.mjt.vehiclerent.exception;

public class VehicleAlreadyRentedException extends RuntimeException {
    public VehicleAlreadyRentedException() {
        super("The vehicle is already rented");
    }
}

package bg.sofia.uni.fmi.mjt.vehiclerent.exception;

public class VehicleAlreadyRentedException extends RuntimeException {
    public VehicleAlreadyRentedException() {
        super("Vehicle is already rented");
    }
}

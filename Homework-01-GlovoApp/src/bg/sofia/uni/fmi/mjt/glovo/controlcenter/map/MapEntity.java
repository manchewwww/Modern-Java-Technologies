package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

public record MapEntity(Location location, MapEntityType type) {

    public MapEntity {
        if (location == null) {
            throw new IllegalArgumentException("Location must be different from null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type must be different from null");
        }
    }

}

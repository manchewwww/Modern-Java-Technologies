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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MapEntity other = (MapEntity) obj;
        return location.equals(other.location) && type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return location.hashCode() + type.hashCode();
    }

}

package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

public record Location(int x, int y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x) * Integer.hashCode(y);
    }

}

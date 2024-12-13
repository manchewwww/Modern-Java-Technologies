package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidMapSymbolException;

public enum MapEntityType {
    ROAD('.'),
    WALL('#'),
    RESTAURANT('R'),
    CLIENT('C'),
    DELIVERY_GUY_CAR('A'),
    DELIVERY_GUY_BIKE('B');

    private final char symbol;

    MapEntityType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static MapEntityType of(char symbol) {
        for (MapEntityType type : MapEntityType.values()) {
            if (type.getSymbol() == symbol) {
                return type;
            }
        }

        throw new InvalidMapSymbolException("Symbol in map is invalid: " + symbol);
    }
}


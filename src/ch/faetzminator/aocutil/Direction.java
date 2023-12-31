package ch.faetzminator.aocutil;

public enum Direction implements CharEnum {

    NORTH('N'), EAST('E'), SOUTH('S'), WEST('W');

    private final char c;

    Direction(final char c) {
        this.c = c;
    }

    public Direction getOpposite() {
        switch (this) {
        case NORTH:
            return SOUTH;
        case EAST:
            return WEST;
        case SOUTH:
            return NORTH;
        case WEST:
            return EAST;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public char getCharacter() {
        return c;
    }

    public static Direction byChar(final char c) {
        return CharEnum.byChar(Direction.class, c);
    }
}

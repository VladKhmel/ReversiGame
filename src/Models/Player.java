package Models;

abstract public class Player {
    Field field;
    Chip color;

    public abstract Coordinate makeMove();

}

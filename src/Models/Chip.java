package Models;

public enum Chip {
    Black,
    White,
    Empty;

    public Chip reverseColor() {
        return this == Black ? White : Black;
    }

}

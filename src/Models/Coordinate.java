package Models;

import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;

    Coordinate(String str) {
        str = str.toLowerCase();
        y = str.charAt(0) - '0' - 49;
        x = str.charAt(1) - '0' - 1;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return x == coordinate.x && y == coordinate.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

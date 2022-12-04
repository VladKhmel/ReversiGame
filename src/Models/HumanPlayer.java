package Models;

import IO.ConsoleInput;

public class HumanPlayer extends Player {
    public HumanPlayer(Field field, Chip color) {
        this.field = field;
        this.color= color;
    }
    public Coordinate makeMove() {
        String maidenMove;
        while (true) {
            try {
                maidenMove = ConsoleInput.moveInput();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return new Coordinate(maidenMove);
    }
}

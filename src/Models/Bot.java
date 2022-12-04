package Models;

import java.util.ArrayList;

public class Bot extends Player {

    public Bot(Field field, Chip color) {
        this.color = color;
        this.field = field;
    }

    public Coordinate makeMove() {
        ArrayList<Coordinate> availableMoves = field.availableMoves.get(color);
        double maxTurnValue = 0;
        double turnValue = 0;
        Coordinate bestTurn = new Coordinate(0, 0);
        for (Coordinate chip : availableMoves) {
            turnValue = field.getPositionValue(chip);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (field.isChipOnField(chip.x + i, chip.y + j) && field.board[chip.x + i][chip.y + j] == Chip.White) {
                        int possibleDirectionX = chip.x + i;
                        int possibleDirectionY = chip.y + j;
                        while (field.isChipOnField(possibleDirectionX, possibleDirectionY) && field.board[possibleDirectionX][possibleDirectionY] == Chip.White) {
                            possibleDirectionX += i;
                            possibleDirectionY += j;
                        }
                        if (field.isChipOnField(possibleDirectionX, possibleDirectionY) && field.board[possibleDirectionX][possibleDirectionY] == Chip.Black) {
                            while (chip.x != possibleDirectionX || chip.y != possibleDirectionY) {
                                possibleDirectionX -= i;
                                possibleDirectionY -= j;
                                turnValue += field.getRecoloredChipValue(possibleDirectionX, possibleDirectionY);
                            }
                        }

                    }
                }
            }
            if (maxTurnValue < turnValue) {
                maxTurnValue = turnValue;
                bestTurn = new Coordinate(chip.x, chip.y);
            }
        }
        return bestTurn;
    }
}

package Models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Field {

    public Chip[][] board = new Chip[8][8];
    public Map<Chip, ArrayList<Coordinate>> availableMoves= new HashMap<>();

    public Field() {
        for (Chip[] chips : board) {
            Arrays.fill(chips, Chip.Empty);
        }
        board[3][4] = Chip.Black;
        board[4][3] = Chip.Black;
        board[3][3] = Chip.White;
        board[4][4] = Chip.White;
        updateAvailableMoves();
    }


    public void addChipOnBoard(Coordinate coordinate, Chip chip) {
        board[coordinate.x][coordinate.y] = chip;
        if (availableMoves.get(chip).contains(coordinate)) {
            recolorBoard(coordinate.x, coordinate.y);
        } else {
            throw new IllegalArgumentException("Этот ход невозможен по правилам игры");
        }
        updateAvailableMoves();
    }

    private void updateAvailableMoves() {
        ArrayList<Coordinate> availableWhiteMoves = new ArrayList<>();
        ArrayList<Coordinate> availableBlackMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Chip.Empty && isColorNearChip(i, j, Chip.Black) && isChipRecoloringEnemy(i, j, Chip.White)) {
                    availableWhiteMoves.add(new Coordinate(i, j));
                }
                if (board[i][j] == Chip.Empty && isColorNearChip(i, j, Chip.White) && isChipRecoloringEnemy(i, j, Chip.Black)) {
                    availableBlackMoves.add(new Coordinate(i, j));
                }
            }
        }
        availableMoves.put(Chip.White, availableWhiteMoves);
        availableMoves.put(Chip.Black, availableBlackMoves);
    }


    private boolean isChipRecoloringEnemy(int x, int y, Chip color) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isChipOnField(x + i, y + j) && board[x + i][y + j] == color.reverseColor()) {
                    int possibleDirectionX = x + i;
                    int possibleDirectionY = y + j;
                    while (isChipOnField(possibleDirectionX, possibleDirectionY) && board[possibleDirectionX][possibleDirectionY] != color) {
                        possibleDirectionX += i;
                        possibleDirectionY += j;
                        if (isChipOnField(possibleDirectionX, possibleDirectionY) && board[possibleDirectionX][possibleDirectionY] == Chip.Empty) {
                            break;
                        }
                    }
                    if (isChipOnField(possibleDirectionX, possibleDirectionY) && board[possibleDirectionX][possibleDirectionY] == color) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    private boolean isColorNearChip(int x, int y, Chip color) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isChipOnField(x + i, y + j) && board[x + i][y + j] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isChipOnField(int x, int y) {
        return x >= 0 && x < board.length && y >= 0 && y < board.length;
    }

    void recolorBoard(int x, int y) {
        Chip currentColor = board[x][y];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isChipOnField(x + i, y + j) && board[x + i][y + j] == currentColor.reverseColor()) {
                    int possibleDirectionX = x + i;
                    int possibleDirectionY = y + j;
                    while (isChipOnField(possibleDirectionX, possibleDirectionY) && board[possibleDirectionX][possibleDirectionY] == currentColor.reverseColor()) {
                        possibleDirectionX += i;
                        possibleDirectionY += j;

                    }
                    if (isChipOnField(possibleDirectionX, possibleDirectionY) && board[possibleDirectionX][possibleDirectionY] == currentColor) {
                        while (x != possibleDirectionX || y != possibleDirectionY) {
                            possibleDirectionX -= i;
                            possibleDirectionY -= j;
                            board[possibleDirectionX][possibleDirectionY] = currentColor;

                        }
                    }
                }
            }
        }
    }

    public int getRecoloredChipValue(int x, int y) {
        if (x == 0 || y == 0 || x == board.length - 1 || y == board.length - 1) {
            return 2;
        }
        return 1;
    }

    public double getPositionValue(Coordinate chip) {
        if ((chip.x == 0 || chip.x == board.length) && (chip.y == 0 || chip.y == board.length)) {
            return 0.8;
        }
        if (chip.x == 0 || chip.y == 0 || chip.x == board.length - 1 || chip.y == board.length - 1) {
            return 0.4;
        }
        return 0;
    }

    public int getScore(Chip color) {
        int score = 0;
        for (Chip[] chipsLine : board) {
            for (Chip chip : chipsLine) {
                if (chip == color) {
                    score++;
                }
            }
        }
        return score;
    }
}

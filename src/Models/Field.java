package Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Field {

    public Chip[][] board = new Chip[8][8];
    public Map<Chip, ArrayList<Coordinate>> availableMoves = new HashMap<>();

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

    /**
     * Добавляет фишку на поле, если по правилам игры это разрешено, обновляет возможные ходы для игроков по итогу добавления
     *
     * @param coordinate координаты поля, в которое надо добавить фишку
     * @param chip       цвет добавляемой фишки
     */
    public void addChipOnBoard(Coordinate coordinate, Chip chip) {
        board[coordinate.x][coordinate.y] = chip;
        if (availableMoves.get(chip).contains(coordinate)) {
            recolorBoard(coordinate.x, coordinate.y);
        } else {
            throw new IllegalArgumentException("Этот ход невозможен по правилам игры");
        }
        updateAvailableMoves();
    }

    /**
     * обновление списка возможных ходов для обоих игроков
     */
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

    /**
     * проверка на то, перекрашивает ли ход вражеские фишки. По правилам игры ход ОБЯЗАН перекрасить хоть одну
     *
     * @param x     координата хода
     * @param y     координата хода
     * @param color цвет фишки, для которой идет проверка
     * @return true - если перекрашивает, false если нет
     */
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

    /**
     * проверяет, есть ли около передаваемой позиции фишки определенного цвета
     *
     * @param x     - координата позиции
     * @param y     - координата позиции
     * @param color цвет, который проверяем
     * @return
     */
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

    /**
     * перекрашивает поле после выставления фишечки
     *
     * @param x - координата фишки, от которой необходимо перекрасить поле
     * @param y - координата фишки, от которой необходимо перекрасить поле
     */
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

    /**
     * Считает ценность перекрашивания данной фишки для алгоритма выбора хода у бота
     *
     * @param x - координаты фишки
     * @param y - координаты фишки
     * @return ценность фишки
     */
    public int getRecoloredChipValue(int x, int y) {
        if (x == 0 || y == 0 || x == board.length - 1 || y == board.length - 1) {
            return 2;
        }
        return 1;
    }

    /**
     * Считает ценность постановки данной фишки для алгоритма выбора хода у бота
     *
     * @param chip координаты фишки
     * @return ценность фишки
     */
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

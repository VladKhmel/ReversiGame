package IO;

import Models.Chip;
import Models.Coordinate;
import Models.Field;

public class GameView {
    Field field;

    public GameView(Field field) {
        this.field = field;
    }

    /**
     * Метод для отрисовки игровой доски
     *
     * @param color - цвет, для которого будут отображаться подсказки в виде подсветки возможных ходов
     */
    public void boardViewFromPlayerColor(Chip color) {
        System.out.println("    A B C D  E F G H");
        for (int i = 0; i < field.board.length; i++) {
            String a = (i + 1) + "  ";
            for (int j = 0; j < field.board[i].length; j++) {

                if (color == Chip.White && field.availableMoves.get(Chip.White).contains(new Coordinate(i, j))) {
                    a += "\uD83D\uDD35";
                } else if (color == Chip.Black && field.availableMoves.get(Chip.Black).contains(new Coordinate(i, j))) {
                    a += "\uD83D\uDD35";
                } else {
                    if (field.board[i][j] == Chip.Empty) {
                        a += "⚪";
                    }
                    if (field.board[i][j] == Chip.White) {
                        a += "\uD83C\uDF15";
                    }
                    if (field.board[i][j] == Chip.Black) {
                        a += "\uD83C\uDF11";
                    }
                }
            }
            System.out.println(a);

        }
        System.out.print("Текущий игрок: ");
        System.out.println(color == Chip.White ? "\uD83C\uDF15" : "\uD83C\uDF11");
    }

    public void scorePrint(int firstPlayerScore, int secondPlayerScore) {
        System.out.println("Игра окончена!" + System.lineSeparator());
        System.out.println("Финальный счет:");
        System.out.println("\uD83C\uDF15Первый игрок: " + firstPlayerScore);
        System.out.println("\uD83C\uDF11Второй игрок: " + secondPlayerScore);
        if (firstPlayerScore > secondPlayerScore) {
            System.out.println("\uD83C\uDF15Победитель - первый игрок!\uD83C\uDF15");
        } else if (firstPlayerScore < secondPlayerScore) {
            System.out.println("\uD83C\uDF11Победитель - второй игрок!\uD83C\uDF11");
        } else {
            System.out.println("\uD83C\uDF17Ничья!\uD83C\uDF13");
        }

    }


}

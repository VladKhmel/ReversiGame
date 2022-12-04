import IO.GameView;
import Models.Bot;
import Models.Chip;
import Models.Field;
import Models.Player;

public class Game {
    Field field;
    GameView view;
    Player firstPlayer;

    Player secondPlayer;
    Player currentPlayer;
    Chip currentColor;

    Game(Field field, Player first, Player second) {
        this.field = field;
        view = new GameView(field);
        firstPlayer = first;
        secondPlayer = second;
        currentPlayer = firstPlayer;
        currentColor = Chip.White;
    }

    /**
     * запускает основной ход игры, где игроки поочередно вводят свои ходы
     *
     * @return возвращает лучший счет, полученный в этой игре
     */
    public int GameProcess() {
        do {
            view.boardViewFromPlayerColor(currentColor);
            while (true) {
                try {
                    field.addChipOnBoard(currentPlayer.makeMove(), currentColor);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (!field.availableMoves.get(currentColor.reverseColor()).isEmpty()) {
                changeTurn();
            }
        } while (!isFinished());

        view.boardViewFromPlayerColor(currentColor);
        view.scorePrint(field.getScore(Chip.White), field.getScore(Chip.Black));
        return getBestScore();

    }

    /**
     * @return возвращает счет игрока, если противник - бот, или наибольший счеит, елси оба игрока - люди
     */
    private int getBestScore() {
        if (secondPlayer instanceof Bot) {
            return field.getScore(Chip.White);
        } else {
            return Math.max(field.getScore(Chip.White), field.getScore(Chip.Black));
        }
    }


    private void changeTurn() {
        currentPlayer = currentPlayer != firstPlayer ? firstPlayer : secondPlayer;
        currentColor = currentColor != Chip.White ? Chip.White : Chip.Black;
    }

    private boolean isFinished() {
        return field.availableMoves.get(Chip.White).isEmpty() && field.availableMoves.get(Chip.Black).isEmpty();
    }


}

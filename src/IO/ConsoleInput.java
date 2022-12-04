package IO;

import java.util.Scanner;


public class ConsoleInput {
    static Scanner in = new Scanner(System.in);

    /**
     * Запрашивает от пользователя ввод команды, если ввод корректный - возвращает ее, если нет - выкидывает исключение
     *
     * @return выбранная пользователем команда
     */
    public static Commands commandInput() {
        String command = in.nextLine().toLowerCase();
        if (command.equals("/bot")) {
            return Commands.PVE;
        }
        if (command.equals("/pvp")) {
            return Commands.PVP;
        }
        if (command.equals("/score")) {
            return Commands.Score;
        }
        if (command.equals("/quit")) {
            return Commands.End;
        }
        throw new IllegalArgumentException("Такой комманды не существует! Перепроверь еще раз список команд и введи еще раз.");
    }

    /**
     * Запрашивает у пользователя ввод хода, проверяет его корректность, ход считается корректны,
     * если состоит из цифры от 1 до 8 и латинской буквы от A до H. Буква и цифра могут быть в любом порядке, регистр не важен
     *
     * @return корректную строку, содержащую координаты выбранного поля
     */
    public static String moveInput() {
        String input = in.nextLine().trim().toLowerCase();
        if (input.length() != 2) {
            throw new IllegalArgumentException("Поля с такой координатой не существует!");
        }
        if (input.charAt(0) >= 97 && input.charAt(0) <= 122 && input.charAt(1) > 48 && input.charAt(1) <= 56) {
            return input;
        }
        if (input.charAt(1) >= 97 && input.charAt(1) <= 122 && input.charAt(0) > 48 && input.charAt(0) <= 56) {
            return "" + input.charAt(1) + input.charAt(0);
        }
        throw new IllegalArgumentException("Поля с такой координатой не существует!");
    }
}

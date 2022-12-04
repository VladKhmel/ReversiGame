package IO;
public class InterfaceView {
    public static void printStartingInfo() {
        System.out.println("\uD83C\uDF11\uD83C\uDF12\uD83C\uDF13РЕВЕРСИ\uD83C\uDF13\uD83C\uDF14\uD83C\uDF15");
        System.out.println("Краткие правила для тех кто не знал об этой прекрасной интеллектуальной игре:");
        System.out.println("Задача игрока перекрасить как можно большую часть поля в свой цвет, перекрашивая вражеские " +
                "фишки в свой цвет, окружая их своими. Чтобы сделать ход введите координаты поля, на которое хотите " +
                "поставить фишку, например 'e3' 'a5'. Правила игры разрешают только ходы, по итогам которых хоть одна" +
                " вражеская фишка перекраситься в ваш цвет. Чтобы упростить игру, все возможные ходы подсвечены синим цветом. Пора начинать!");

    }
    public static void printInfoAboutCommands() {
        System.out.print(System.lineSeparator());
        System.out.println("Чтобы начать, введите команду:");
        System.out.println("/bot - начать партию с ботом");
        System.out.println("/pvp - начать партию игрок против игрока - вводите ходы по очереди");
        System.out.println("/score - показать лучший результат игрока за эту сессию");
        System.out.println("/quit - завершить сессию");
    }
}

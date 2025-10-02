package academy.game;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class InteractiveMode {

    private final File externalDictionaryPath;
    private final Difficulty difficulty;
    private final Category category;

    public InteractiveMode(File externalDictionaryPath, Difficulty difficulty, Category category) {
        this.externalDictionaryPath = externalDictionaryPath;
        this.difficulty = Objects.requireNonNullElse(difficulty, Difficulty.getRandomDifficulty());
        this.category = Objects.requireNonNullElse(category, Category.getRandomCategory());
    }

    public void run() {
        try {
            Dictionary.loadYaml(externalDictionaryPath); // checks for null inside loadYaml
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
            return;
        }
        final String answer = Dictionary.getRandomWord(category, difficulty);
        final GameSession game = new GameSession(answer, difficulty);
        final HangmanRenderer hangmanRenderer = new HangmanRenderer(difficulty);

        System.out.println("Добро пожаловать в игру \"Виселица\"!");

        final Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Введите букву (или '?' для подсказки): ");
            String choice = in.nextLine().trim();
            GuessResult result = game.guess(choice);
            clearScreen();
            printHeader(result, hangmanRenderer);

            switch (result.status()) {
                case INVALID_INPUT -> System.out.println("Пожалуйста, введите только одну букву.");
                case HINT -> System.out.println("Подсказка: " + Dictionary.getHint(answer));
                case REPETITION -> System.out.printf("Вы уже называли букву: %s. Попробуйте снова.\n", choice);
                case WON -> {
                    System.out.println("Вы победили: Загадываемое слово было " + answer);
                    return;
                }
                case LOST -> {
                    System.out.println("Вы проиграли: Загадываемое слово было " + answer);
                    return;
                }
                case HIT -> {
                    System.out.printf("Отлично! Буква: %s существует.\n", choice);
                    System.out.printf(
                            "Текущее слово: %-30s %s\n", result.masked(), hangmanRenderer.render(result.mistakes()));
                }
                case MISS -> {
                    System.out.printf(
                            "Буквы %s в слове нет. Осталось попыток: %d",
                            choice, result.maxMistakes() - result.mistakes());
                    System.out.printf(
                            "Текущее слово: %-30s %s\n", result.masked(), hangmanRenderer.render(result.mistakes()));
                }
            }
        }
    }

    private void printHeader(GuessResult result, HangmanRenderer hangmanRenderer) {
        System.out.printf(
                "Категория: %s  |  Сложность: %s  |  Попыток осталось: %d  |  Кол-во ошибок: %d%n",
                category, difficulty, result.maxMistakes() - result.mistakes(), result.mistakes());
        System.out.println(hangmanRenderer.render(result.mistakes()));
        System.out.println("Слово: " + result.masked());
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

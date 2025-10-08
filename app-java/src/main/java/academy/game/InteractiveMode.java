package academy.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class InteractiveMode {

    private final Path externalDictionaryPath;
    private final Difficulty difficulty;
    private final Category category;

    public InteractiveMode(final Path externalDictionaryPath, final Difficulty difficulty, final Category category) {
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
        final WordEntry answer = Dictionary.getRandomWordEntry(category, difficulty);
        final GameSession game = new GameSession(answer.word(), difficulty);
        final HangmanRenderer hangmanRenderer = new HangmanRenderer(difficulty);

        // Visualization
        printHeader(game.initialState(), hangmanRenderer);
        System.out.println("Добро пожаловать в игру \"Виселица\"!");

        final Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Введите букву (или '?' для подсказки): ");
            String choice = in.nextLine().strip();
            GuessResult result = game.guess(choice);
            clearScreen();
            printHeader(result, hangmanRenderer);

            switch (result.status()) {
                case INVALID_INPUT -> System.out.println("Пожалуйста, введите только одну букву.");
                case HINT -> System.out.println("Подсказка: " + answer.hint());
                case REPETITION -> System.out.printf("Вы уже называли букву: %s. Попробуйте снова.\n", choice);
                case WON -> {
                    System.out.println("Вы победили: Загадываемое слово - " + answer.word());
                    return;
                }
                case LOST -> {
                    System.out.println("Вы проиграли: Загадываемое слово - " + answer.word());
                    return;
                }
                case HIT -> System.out.printf("Отлично! Буква: %s существует.\n", choice);
                case MISS ->
                    System.out.printf(
                            "Буквы %s в слове нет. Осталось попыток: %d\n",
                            choice, result.maxMistakes() - result.mistakes());
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
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
}

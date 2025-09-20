package academy.game;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class InteractiveMode {

    private final File externalDictionaryPath;
    private final Difficulty difficulty;
    private final Category category;

    public InteractiveMode(File externalDictionaryPath, Difficulty difficulty, Category category){
        this.externalDictionaryPath = externalDictionaryPath;
        this.difficulty = Objects.requireNonNullElse(difficulty, Difficulty.getRandomDifficulty());
        this.category = Objects.requireNonNullElse(category, Category.getRandomCategory());
    }

    public void run(){
        try {
            Dictionary.loadYaml(externalDictionaryPath); // checks for null inside loadYaml
        } catch (IOException e){
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
            return;
        }
        final String answer = Dictionary.getRandomWord(category, difficulty);
        final GameSession game = new GameSession(answer, difficulty.getMaxMistakes());
        final HangmanRenderer hangmanRenderer = new HangmanRenderer(difficulty);

        while (game.)
    }
}

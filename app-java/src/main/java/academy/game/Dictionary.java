package academy.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<WordEntry>>> words = new HashMap<>();
    private static Supplier<Double> rng = Math::random; // для тестинга

    private Dictionary() {
        throw new UnsupportedOperationException();
    }

    // для детерминированности в тестах
    static void setRandom(Supplier<Double> supplier) { // package private
        Objects.requireNonNull(supplier);
        rng = supplier;
    }

    static void resetRandom() {
        rng = Math::random;
    }

    public static void loadYaml(Path yamlfile) throws IOException {
        if (yamlfile == null) {
            yamlfile = Path.of("src/main/resources/dictionary.yaml");
        }
        words.putAll(DictionaryLoader.load(yamlfile));
    }

    public static WordEntry getRandomWordEntry(final Category category, final Difficulty difficulty) {
        List<WordEntry> wordList = words.get(category).get(difficulty);
        return wordList.get((int) (rng.get() * wordList.size()));
    }
}

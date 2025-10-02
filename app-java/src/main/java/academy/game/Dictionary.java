package academy.game;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
    private static final Map<String, String> hints = new HashMap<>();

    private Dictionary() {
        throw new UnsupportedOperationException();
    }

    public static void loadYaml(Path yamlfile) throws IOException {
        if (yamlfile == null) {
            yamlfile = Path.of("src/main/resources/dictionary.yaml");
        }
        DictionaryLoader.ProcessedDictionary data = DictionaryLoader.load(yamlfile);
        words.putAll(data.words());
        hints.putAll(data.hints());
    }

    public static String getRandomWord(final Category category, final Difficulty difficulty) {
        List<String> wordList = words.get(category).get(difficulty);
        return wordList.get((int) (Math.random() * wordList.size()));
    }

    public static String getHint(final String word) {
        return hints.get(word);
    }
}
// TODO: Add logic for hints mechanism in Application.java

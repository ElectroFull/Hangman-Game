package academy.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
    private static final Map<String, String> hints = new HashMap<>();

    public static void loadYaml(File yamlfile) throws IOException {
        if (yamlfile == null) {
            yamlfile = new File("resources/dictionary.yaml");
        }
        DictionaryLoader.ProcessedDictionary data = DictionaryLoader.load(yamlfile);
        words.putAll(data.words());
        hints.putAll(data.hints());
    }

    public static String getRandomWord(Category category, Difficulty difficulty) {
        List<String> wordList = words.get(category).get(difficulty);
        return wordList.get((int) (Math.random() * wordList.size()));
    }

    public static String getHint(String word) {
        return hints.get(word);
    }
}
// TODO: Add logic for hints mechanism in Application.java

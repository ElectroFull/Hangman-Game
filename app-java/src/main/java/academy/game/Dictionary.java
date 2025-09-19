package academy.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
    private static final Map<String, String> hints = new HashMap<>();

    public static void loadYaml(File yamlfile) throws IOException {
        DictionaryLoader.ProcessedDictionary data = DictionaryLoader.load(yamlfile);
        words.putAll(data.words());
        hints.putAll(data.hints());
    }

    public static String getRandomWord(Category category, Difficulty difficulty){
        Category chosenCategory = (category != null) ? category : getRandomCategory();
        Difficulty chosenDifficulty = (difficulty != null) ? difficulty : getRandomDifficulty();
        List<String> wordList = words.get(chosenCategory).get(chosenDifficulty);
        return wordList.get((int)(Math.random() * wordList.size()));
    }

    public static String getHint(String word){
        return hints.get(word);
    }

    public static Category getRandomCategory(){
        return Category.values()[(int)(Math.random() * Category.values().length)];
    }

    public static Difficulty getRandomDifficulty(){
        return Difficulty.values()[(int)(Math.random() * Difficulty.values().length)];
    }
}
// TODO: Add logic for hints mechanism in Application.java

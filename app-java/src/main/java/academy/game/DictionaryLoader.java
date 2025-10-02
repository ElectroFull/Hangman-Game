package academy.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryLoader {
    private record WordEntry(String word, String hint) {}

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private DictionaryLoader() {
        throw new UnsupportedOperationException();
    }

    public record ProcessedDictionary(Map<Category, Map<Difficulty, List<String>>> words, Map<String, String> hints) {}

    public static ProcessedDictionary load(final Path file) throws IOException {
        TypeReference<Map<Category, Map<Difficulty, List<WordEntry>>>> typeRef = new TypeReference<>() {};
        Map<Category, Map<Difficulty, List<WordEntry>>> rawData = mapper.readValue(file.toFile(), typeRef);
        Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
        Map<String, String> hints = new HashMap<>();

        for (var category : rawData.entrySet()) {
            Map<Difficulty, List<String>> wordsCollector = new HashMap<>();
            for (var difficulty : category.getValue().entrySet()) {
                List<String> wordsOnly = new ArrayList<>();
                for (var entry : difficulty.getValue()) {
                    wordsOnly.add(entry.word().toLowerCase().strip()); // normalization
                    hints.put(entry.word().toLowerCase().strip(), entry.hint()); // hint for UI only
                }
                wordsCollector.put(difficulty.getKey(), wordsOnly);
            }
            words.put(category.getKey(), wordsCollector);
        }

        return new ProcessedDictionary(words, hints);
    }
}

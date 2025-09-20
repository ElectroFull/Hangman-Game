package academy.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DictionaryLoader {
    private record WordEntry (String word, String hint){ }
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    public record ProcessedDictionary (Map<Category, Map<Difficulty, List<String>>> words,
                                        Map<String, String> hints){}

    public static ProcessedDictionary load(File file) throws IOException {
        TypeReference<Map<Category, Map<Difficulty, List<WordEntry>>>> typeRef = new TypeReference<Map<Category, Map<Difficulty, List<WordEntry>>>>() {};
        Map<Category, Map<Difficulty, List<WordEntry>>> rawData = mapper.readValue(file, typeRef);

        Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
        Map<String, String> hints = new HashMap<>();

        for (var category : rawData.entrySet()){
            Map<Difficulty, List<String>> wordsCollector = new HashMap<>();
            for (var difficulty : category.getValue().entrySet()){
                List <String> wordsOnly = new ArrayList<>();
                for (var entry: difficulty.getValue()){
                    wordsOnly.add(entry.word().toLowerCase().trim()); // normalization
                    hints.put(entry.word().toLowerCase().trim(), entry.hint()); // hint for UI only
                }
                wordsCollector.put(difficulty.getKey(), wordsOnly);
            }
            words.put(category.getKey(), wordsCollector);
        }

        return new ProcessedDictionary(words, hints);
    }
}

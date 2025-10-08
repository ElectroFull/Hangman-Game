package academy.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DictionaryLoader {
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private DictionaryLoader() {
        throw new UnsupportedOperationException();
    }

    public static Map<Category, Map<Difficulty, List<WordEntry>>> load(final Path file) throws IOException {
        TypeReference<Map<Category, Map<Difficulty, List<WordEntry>>>> typeRef = new TypeReference<>() {};
        return mapper.readValue(file.toFile(), typeRef);
    }
}

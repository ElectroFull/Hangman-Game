package academy;

import academy.game.Category;
import academy.game.Dictionary;
import academy.game.Difficulty;
import academy.game.GameSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import static academy.AcceptanceTestExample.TEST_CASES_DUMMY;
import static academy.AcceptanceTestExample.UNKNOWN_TEST_WORD;
import static java.util.Objects.nonNull;

@Command(name = "Hangman Game", version = "Hangman 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final ObjectReader YAML_READER =
        new ObjectMapper(new YAMLFactory()).findAndRegisterModules().reader();
    private static final Predicate<String[]> IS_TESTING_MODE = words -> nonNull(words) && words.length == 2;

    @Option(
        names = {"-s", "--font-size"},
        description = "Font size")
    int fontSize;

    @Parameters(
        paramLabel = "<word>",
        description = "Words pair for testing mode")
    private String[] words;

    @Option(
        names = {"-c", "--config"},
        description = "Path to YAML config file")
    private File configPath;

    @Option(
        names = {"-d", "--dictionary"},
        description = "Path to YAML dictionary file")
    private File dictionaryPath;

    @Option(
        names = {"-l", "--level"},
        description = "Set the difficulty of the game: ${COMPLETION-CANDIDATES}")
    private Difficulty difficulty;

    @Option(
        names = {"-t", "--topic"},
        description = "Set the word's category: ${COMPLETION-CANDIDATES}")
    private Category category;


    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).setCaseInsensitiveEnumValuesAllowed(true).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        AppConfig config = loadConfig();
        LOGGER.atInfo().addKeyValue("config", config).log("Config content");

        if (IS_TESTING_MODE.test(config.words())) {
            LOGGER.atInfo().log("Non-interactive testing mode enabled");

        } else {
            LOGGER.atInfo().log("Interactive mode enabled");

        }
    }

    private AppConfig loadConfig() {
        // fill with cli options
        if (configPath == null) return new AppConfig(fontSize, words);

        // use config file if provided
        try {
            return YAML_READER.readValue(configPath, AppConfig.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

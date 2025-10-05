package academy;

import static java.util.Objects.nonNull;

import academy.game.Category;
import academy.game.Difficulty;
import academy.game.InteractiveMode;
import academy.game.NonInteractiveMode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "Hangman Game", version = "Hangman 1.0", mixinStandardHelpOptions = true)
public class Application implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final Predicate<String[]> IS_TESTING_MODE = words -> nonNull(words) && words.length == 2;

    @Parameters(paramLabel = "<word>", description = "Words pair for testing mode")
    private String[] words;

    @Option(
            names = {"-d", "--dictionary"},
            description = "Path to YAML dictionary file")
    private Path dictionaryPath;

    @Option(
            names = {"-l", "--level"},
            description = "Set the difficulty of the game: ${COMPLETION-CANDIDATES}")
    private Difficulty difficulty;

    @Option(
            names = {"-t", "--topic"},
            description = "Set the word's category: ${COMPLETION-CANDIDATES}")
    private Category category;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application())
                .setCaseInsensitiveEnumValuesAllowed(true) //
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (IS_TESTING_MODE.test(words)) {
            LOGGER.atInfo().log("Non-interactive testing mode enabled");
            NonInteractiveMode theHangmanTestApp = new NonInteractiveMode(words[0], words[1]);
            theHangmanTestApp.run();
        } else {
            LOGGER.atInfo().log("Interactive mode enabled");
            InteractiveMode theHangmanGame = new InteractiveMode(dictionaryPath, difficulty, category);
            theHangmanGame.run();
        }
    }
}

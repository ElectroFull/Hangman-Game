package academy;

import static org.instancio.Select.allStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

import academy.game.NonInteractiveMode;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class NonInteractiveModeTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream baos;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testNonInteractiveModeValidation() {
        assertThrows(NullPointerException.class, () -> new NonInteractiveMode(null, "test"));
        assertThrows(IllegalArgumentException.class, () -> new NonInteractiveMode("hello", "hi"));
    }

    @ParameterizedTest
    @MethodSource("getWords")
    void testRunnerPositive(String word) {
        NonInteractiveMode testGame = new NonInteractiveMode(word, word);
        testGame.run();
        String capturedOutput = baos.toString().strip();
        assertEquals(word + ";" + "POS", capturedOutput);
    }

    @ParameterizedTest
    @MethodSource("getWords")
    void testRunnerNegative(String word) {
        int position = 0;

        String wrongWord = word.replace(word.charAt(position), '5');
        NonInteractiveMode testGame = new NonInteractiveMode(word, wrongWord);
        String masked = maskedWordHelper(word, position);

        testGame.run();

        String capturedOutput = baos.toString().strip();
        assertNotNull(capturedOutput);
        assertEquals(masked + ";" + "NEG", capturedOutput);
    }

    static Stream<String> getWords() {
        return Instancio.of(String.class)
                .generate(allStrings(), gen -> gen.string()
                        .minLength(3)
                        .maxLength(10)
                        .allowEmpty(false)
                        .lowerCase())
                .withSeed(1234)
                .stream()
                .limit(10);
    }

    // Открывает все буквы кроме заданной
    static String maskedWordHelper(String word, int position) {
        return word.toLowerCase()
                .chars()
                .mapToObj(ch -> (char) ch)
                .map(ch -> ch != word.charAt(position) ? ch : '*')
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}

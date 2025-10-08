package academy.game;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.instancio.Select.allStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class GameSessionTest {

    @Test
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () -> new GameSession(null, Difficulty.HARD));
        assertThrows(IllegalArgumentException.class, () -> new GameSession("h", Difficulty.HARD));
        assertThrows(IllegalArgumentException.class, () -> new GameSession("  ", Difficulty.HARD));
        assertThrows(IllegalArgumentException.class, () -> new GameSession("abcd9", Difficulty.EASY));
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testHitCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);
        int position = 0;
        String masked = maskedWordHelper(word, position);

        GuessResult result = game.guess(String.valueOf(word.charAt(position)));

        assertEquals(GameStatus.HIT, result.status());
        assertEquals(masked, result.masked());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testMissCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);

        GuessResult result = game.guess(String.valueOf('字'));

        assertEquals(GameStatus.MISS, result.status());
        assertEquals("*".repeat(word.length()), result.masked());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testInvalidInputCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);

        GuessResult result = game.guess("abc");

        assertEquals(GameStatus.INVALID_INPUT, result.status());
        assertEquals("*".repeat(word.length()), result.masked());

        result = game.guess(" ");
        assertEquals(GameStatus.INVALID_INPUT, result.status());

        result = game.guess("");
        assertEquals(GameStatus.INVALID_INPUT, result.status());

        result = game.guess("+");
        assertEquals(GameStatus.INVALID_INPUT, result.status());

        result = game.guess("5");
        assertEquals(GameStatus.INVALID_INPUT, result.status());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testRepetitionCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);
        int position = 0;
        String masked = maskedWordHelper(word, position);

        game.guess(String.valueOf(word.charAt(position)));
        GuessResult result = game.guess(String.valueOf(word.charAt(position)));

        assertEquals(GameStatus.REPETITION, result.status());
        assertEquals(masked, result.masked());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testHintCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);

        GuessResult result = game.guess("?");

        assertEquals(GameStatus.HINT, result.status());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testWinCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);
        GuessResult result = null;

        for (char x : word.toCharArray()) {
            result = game.guess(String.valueOf(x));
        }

        assertNotNull(result);
        assertEquals(GameStatus.WON, result.status());
        assertEquals(0, result.mistakes());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testLoseCondition(String word) {
        GameSession game = new GameSession(word, Difficulty.HARD);
        game.guess("字");
        game.guess("水");
        GuessResult result = game.guess("日");
        assertEquals(GameStatus.LOST, result.status());
        assertEquals(Difficulty.HARD.getMaxMistakes(), result.mistakes());
    }

    @ParameterizedTest
    @EnumSource(Difficulty.class)
    void testDifficulties(Difficulty difficulty) {
        GameSession game = new GameSession("hellohi", difficulty);

        int maxMistakes = difficulty.getMaxMistakes();

        char[] chineseChars = {'人', '水', '火', '山', '日', '月', '木', '天', '中', '爱', '学', '国', '家', '你', '好'};

        for (int i = 0; i < maxMistakes - 1; i++) {
            GuessResult result = game.guess(String.valueOf(chineseChars[i]));
            assertThat(result.status()).isNotEqualTo(GameStatus.LOST);
        }

        GuessResult finalResult = game.guess(String.valueOf(chineseChars[maxMistakes]));

        assertThat(finalResult.mistakes()).isEqualTo(difficulty.getMaxMistakes());
        assertEquals(GameStatus.LOST, finalResult.status());
    }

    @Test
    void testCaseInsensitiveGuess() {
        GameSession game = new GameSession("Hellohi", Difficulty.HARD);

        GuessResult result = game.guess("H");

        assertEquals(GameStatus.HIT, result.status());

        result = game.guess("h");

        assertEquals(GameStatus.REPETITION, result.status());
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

    // Открывает все буквы соответствующие текущей в position
    static String maskedWordHelper(String word, int position) {
        return word.toLowerCase()
                .chars()
                .mapToObj(ch -> (char) ch)
                .map(ch -> ch == word.charAt(position) ? ch : '*')
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}

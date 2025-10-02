package academy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import academy.game.Difficulty;
import academy.game.GameSession;
import academy.game.GameStatus;
import academy.game.GuessResult;
import org.assertj.core.internal.Diff;
import org.instancio.Instancio;
import static org.instancio.Select.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GameSessionTest {

    @Test
    void testValidation(){
        assertThrows(IllegalArgumentException.class, () -> new GameSession(null, Difficulty.HARD));
        assertThrows(IllegalArgumentException.class, () -> new GameSession("h", Difficulty.HARD));
        assertThrows(IllegalArgumentException.class, () -> new GameSession("  ", Difficulty.HARD));
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testHitCondition(String word){
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

        GuessResult result = game.guess(String.valueOf('0'));

        assertEquals(GameStatus.MISS, result.status());
        assertEquals("*".repeat(word.length()), result.masked());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testInvalidInputCondition(String word){
        GameSession game = new GameSession(word, Difficulty.HARD);

        GuessResult result = game.guess("abc");

        assertEquals(GameStatus.INVALID_INPUT, result.status());
        assertEquals("*".repeat(word.length()), result.masked());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testRepetitionCondition(String word){
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
    void testHintCondition(String word){
        GameSession game = new GameSession(word, Difficulty.HARD);

        GuessResult result = game.guess("?");

        assertEquals(GameStatus.HINT, result.status());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testWinCondition(String word){
        GameSession game = new GameSession(word, Difficulty.HARD);
        GuessResult result = null;
        for (char x : word.toCharArray()){
            result = game.guess(String.valueOf(x));
        }
        assertNotNull(result);
        assertEquals(GameStatus.WON, result.status());
    }

    @ParameterizedTest()
    @MethodSource("getWords")
    void testLoseCondition(String word){
        GameSession game = new GameSession(word, Difficulty.HARD);
        game.guess("0");
        game.guess("1");
        GuessResult result = game.guess("2");
        assertEquals(GameStatus.LOST, result.status());
    }

    static Stream<String> getWords(){
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

    static String maskedWordHelper(String word, int position){
        return word.toLowerCase().chars()
            .mapToObj(ch -> (char) ch)
            .map(ch -> ch == word.charAt(position) ? ch : '*')
            .map(String::valueOf)
            .collect(Collectors.joining());
    }
}

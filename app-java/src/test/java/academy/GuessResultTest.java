package academy;

import academy.game.GameStatus;
import academy.game.GuessResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessResultTest {
    @Test
    void testGuessResultCreation() {
        GuessResult result = new GuessResult("h*llo", 2, 5, GameStatus.HIT);
        assertEquals("h*llo", result.masked());
        assertEquals(2, result.mistakes());
        assertEquals(5, result.maxMistakes());
        assertEquals(GameStatus.HIT, result.status());
    }
}

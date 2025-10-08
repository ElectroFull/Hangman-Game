package academy.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class HangmanRendererTest {

    @ParameterizedTest
    @EnumSource(Difficulty.class)
    void TestHangmanProgress(Difficulty d) {
        HangmanRenderer r = new HangmanRenderer(d);
        int max = d.getMaxMistakes();

        String prev = r.render(0);
        // Проверяем начальное состояние (0 ошибок)
        assertTrue(prev.isBlank());

        for (int i = 1; i <= max; i++) {
            String cur = r.render(i);
            // Проверяем прогрессию рисунка
            assertNotEquals(prev, cur);
            prev = cur;
        }
    }

    @Test
    void testHangmanRendererDifficultyDifferences() {
        HangmanRenderer easyRenderer = new HangmanRenderer(Difficulty.EASY);
        HangmanRenderer mediumRenderer = new HangmanRenderer(Difficulty.MEDIUM);
        HangmanRenderer hardRenderer = new HangmanRenderer(Difficulty.HARD);
        // Проверяем последнее состояние при разных количествах ошибок
        assertEquals(
                easyRenderer.render(Difficulty.EASY.getMaxMistakes()),
                hardRenderer.render(Difficulty.HARD.getMaxMistakes()));
        assertEquals(
                easyRenderer.render(Difficulty.EASY.getMaxMistakes()),
                mediumRenderer.render(Difficulty.MEDIUM.getMaxMistakes()));
        assertEquals(
                mediumRenderer.render(Difficulty.MEDIUM.getMaxMistakes()),
                hardRenderer.render(Difficulty.HARD.getMaxMistakes()));
    }

    @ParameterizedTest
    @EnumSource(Difficulty.class)
    void IllegalArgumentsTest(Difficulty d) {
        HangmanRenderer r = new HangmanRenderer(d);

        assertThrows(IllegalArgumentException.class, () -> r.render(-1));
        assertThrows(IllegalArgumentException.class, () -> r.render(d.getMaxMistakes() + 1));
    }
}

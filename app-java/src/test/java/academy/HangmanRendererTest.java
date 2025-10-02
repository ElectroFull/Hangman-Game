package academy;

import academy.game.Difficulty;
import academy.game.HangmanRenderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HangmanRendererTest {
    @Test
    void testHangmanRendering() {
        HangmanRenderer renderer = new HangmanRenderer(Difficulty.EASY);
        assertEquals("", renderer.render(0));
        assertNotEquals("", renderer.render(1));
    }
}

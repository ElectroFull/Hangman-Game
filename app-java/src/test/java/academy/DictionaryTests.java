package academy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

import academy.game.Category;
import academy.game.Dictionary;
import academy.game.Difficulty;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DictionaryTests {
    @BeforeAll
    static void testDictionaryLoading() throws IOException {
        Dictionary.loadYaml(Path.of("src/test/resources/test-dictionary.yaml"));
    }

    @Test
    void testGetRandomWord() {
        String word = Dictionary.getRandomWord(Category.ANIMALS, Difficulty.EASY);
        assertNotNull(word);
        assertEquals("кот", word);

        word = Dictionary.getRandomWord(Category.FRUITS, Difficulty.MEDIUM);
        assertNotNull(word);
        assertTrue(word.equals("яблоко") || word.equals("банан"));

        word = Dictionary.getRandomWord(Category.TECHNOLOGY, Difficulty.HARD);
        assertNotNull(word);
        assertTrue(word.equals("компьютер") || word.equals("телефон"));
    }

    @Test
    void testGetHintForDifferentWords() {
        String catHint = Dictionary.getHint("кот");
        assertNotNull(catHint);
        assertEquals("Домашнее животное, любит молоко", catHint);

        String appleHint = Dictionary.getHint("яблоко");
        assertNotNull(appleHint);
        assertEquals("Красный или зеленый фрукт, растет на дереве", appleHint);

        String bananaHint = Dictionary.getHint("банан");
        assertNotNull(bananaHint);
        assertEquals("Длинный желтый фрукт", bananaHint);

        String computerHint = Dictionary.getHint("компьютер");
        assertNotNull(computerHint);
        assertEquals("Электронное устройство для обработки данных", computerHint);
    }

    @Test
    void testAllCategoriesAndDifficultiesNotNull() {
        assertNotNull(Dictionary.getRandomWord(Category.ANIMALS, Difficulty.EASY));
        assertNotNull(Dictionary.getRandomWord(Category.FRUITS, Difficulty.MEDIUM));
        assertNotNull(Dictionary.getRandomWord(Category.TECHNOLOGY, Difficulty.HARD));
    }
}

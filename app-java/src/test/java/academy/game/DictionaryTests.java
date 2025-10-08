package academy.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DictionaryTests {
    @BeforeAll
    static void testDictionaryLoading() throws IOException {
        Dictionary.loadYaml(Path.of("src/test/resources/test-dictionary.yaml"));
    }

    @AfterEach
    void resetRng() {
        Dictionary.resetRandom(); // чтобы тесты не смешивались
    }

    @Test
    void animals_easy_is_cat() {
        Dictionary.setRandom(() -> 0.0); // индекс 0
        WordEntry data = Dictionary.getRandomWordEntry(Category.ANIMALS, Difficulty.EASY);
        assertNotNull(data);
        assertEquals("кот", data.word());
        assertEquals("Домашнее животное, любит молоко", data.hint());
    }

    @Test
    void fruits_medium_index0_is_apple() {
        Dictionary.setRandom(() -> 0.0); // индекс 0
        WordEntry data = Dictionary.getRandomWordEntry(Category.FRUITS, Difficulty.MEDIUM);
        assertNotNull(data);
        assertEquals("яблоко", data.word());
        assertEquals("Красный или зеленый фрукт, растет на дереве", data.hint());
    }

    @Test
    void fruits_medium_index1_is_banana() {
        Dictionary.setRandom(() -> 0.99); // индекс 1
        WordEntry data = Dictionary.getRandomWordEntry(Category.FRUITS, Difficulty.MEDIUM);
        assertNotNull(data);
        assertEquals("банан", data.word());
        assertEquals("Длинный желтый фрукт", data.hint());
    }

    @Test
    void technology_hard_index0_is_computer() {
        Dictionary.setRandom(() -> 0.0); // индекс 0
        WordEntry data = Dictionary.getRandomWordEntry(Category.TECHNOLOGY, Difficulty.HARD);
        assertNotNull(data);
        assertEquals("компьютер", data.word());
        assertEquals("Электронное устройство для обработки данных", data.hint());
    }

    @Test
    void technology_hard_index1_is_phone() {
        Dictionary.setRandom(() -> 0.99); // индекс 1
        WordEntry data = Dictionary.getRandomWordEntry(Category.TECHNOLOGY, Difficulty.HARD);
        assertNotNull(data);
        assertEquals("телефон", data.word());
        assertEquals("Устройство для связи на расстоянии", data.hint());
    }

    @Test
    void testAllCategoriesAndDifficultiesNotNull() {
        assertNotNull(Dictionary.getRandomWordEntry(Category.ANIMALS, Difficulty.EASY));
        assertNotNull(Dictionary.getRandomWordEntry(Category.FRUITS, Difficulty.MEDIUM));
        assertNotNull(Dictionary.getRandomWordEntry(Category.TECHNOLOGY, Difficulty.HARD));
    }
}

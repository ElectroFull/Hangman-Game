package academy;

import academy.game.Category;
import academy.game.Difficulty;
import academy.game.InteractiveMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

class InteractiveModeTest {
    private PrintStream originalStdOut;
    private PrintStream originalErrorOut;
    private ByteArrayOutputStream baosStdOut;
    private ByteArrayOutputStream baosErrOut;

    @BeforeEach
    void setUp(){ // Для перехвата стандартного вывода
        originalStdOut = System.out;
        originalErrorOut = System.err;
        baosStdOut = new ByteArrayOutputStream();
        baosErrOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baosStdOut));
        System.setErr(new PrintStream(baosErrOut));
    }

    @AfterEach
    void tearDown(){
        System.setOut(originalStdOut);
        System.setErr(originalErrorOut);
    }

    @Test
    void testInvalidDictionary(){
        InteractiveMode game = new InteractiveMode(
            Path.of("abcde.yaml"),
            Difficulty.MEDIUM,
            Category.FRUITS);

        game.run();

        String exception = baosErrOut.toString().strip();

        assertTrue(exception.startsWith("Не удалось загрузить словарь: "));
    }

    @Test
    void testConstructorWithDefaultValues() {
        InteractiveMode game = new InteractiveMode(null, null, null);
        assertNotNull(game);
    }

    @Test
    void testAllConditionsExceptLost(){
        InputStream originalIn = System.in;
        try {
            String guess = "?\nabc\na\na\na\nк\nо\nт";

            ByteArrayInputStream baosStdIn = new ByteArrayInputStream(guess.getBytes());
            System.setIn(baosStdIn);

            InteractiveMode game = new InteractiveMode(
                Path.of("src/test/resources/test-dictionary.yaml"),
                Difficulty.EASY,
                Category.ANIMALS
            );

            game.run();

            String output = baosStdOut.toString();
            assertTrue(output.contains("Категория: ANIMALS  |  Сложность: EASY  |  Попыток осталось: 8  |  Кол-во ошибок: 0"));
            assertTrue(output.contains("Буквы a в слове нет. Осталось попыток: 7\n"));
            assertTrue(output.contains("Вы уже называли букву: a. Попробуйте снова.\n"));
            assertTrue(output.contains("Подсказка: Домашнее животное, любит молоко"));
            assertTrue(output.contains("Вы победили: Загадываемое слово - кот"));
            assertTrue(output.contains("Отлично! Буква: к существует.\n"));
            assertTrue(output.contains("Пожалуйста, введите только одну букву."));
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    void testLoseCondition(){
        InputStream originalIn = System.in;
        try {
            String guess = "0\n1\n2\n3\n4\n5\n6\n7";

            ByteArrayInputStream baosStdIn = new ByteArrayInputStream(guess.getBytes());
            System.setIn(baosStdIn);

            InteractiveMode game = new InteractiveMode(
                Path.of("src/test/resources/test-dictionary.yaml"),
                Difficulty.EASY,
                Category.ANIMALS
            );

            game.run();

            String output = baosStdOut.toString();
            assertTrue(output.contains("Вы проиграли: Загадываемое слово - кот"));
        } finally {
            System.setIn(originalIn);
        }
    }

}

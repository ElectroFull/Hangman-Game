package academy.game;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NonInteractiveMode {
    private final String answer;
    private final char[] guessCharArray;

    public NonInteractiveMode(final String answer, final String guess) {
        Objects.requireNonNull(answer, "Угадываемое слово не должно быть пустым");
        Objects.requireNonNull(guess, "Результат угадывания не должен быть пустым");
        if (answer.length() != guess.length()) {
            throw new IllegalArgumentException("Слова должны быть одинаковой длины!");
        }
        this.answer = answer;
        this.guessCharArray = guess.toCharArray();
    }

    public void run() {
        final Set<Character> guessSet = new HashSet<>();
        for (char x : guessCharArray) {
            guessSet.add(x);
        }
        StringBuilder masked = new StringBuilder();
        char[] answerCharArray = answer.toCharArray();
        for (char x : answerCharArray) {
            if (guessSet.contains(x)) {
                masked.append(x);
            } else {
                masked.append('*');
            }
        }
        String maskedWord = masked.toString();
        if (maskedWord.equals(answer)) {
            System.out.printf("%s;POS\n", maskedWord);
        } else {
            System.out.printf("%s;NEG\n", maskedWord);
        }
    }
}

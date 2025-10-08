package academy.game;

import java.util.Objects;

public record WordEntry(String word, String hint) {
    public WordEntry {
        Objects.requireNonNull(word, "word");
        Objects.requireNonNull(hint, "hint");
        if (hint.isBlank()) throw new IllegalArgumentException();
        word = word.toLowerCase().strip();
        if (word.isBlank()) throw new IllegalArgumentException();
    }
}

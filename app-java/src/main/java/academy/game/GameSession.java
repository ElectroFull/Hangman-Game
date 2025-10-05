package academy.game;

import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final String answer;
    private final int maxMistakes;
    private final Set<Character> guessed = new HashSet<>();
    private int mistakes = 0;

    public GameSession(String answer, final Difficulty maxMistakes) {
        if (answer == null || (answer = norm(answer)).length() < 2)
            throw new IllegalArgumentException("Загадываемое слово не должно быть короче 2-x символов!");
        this.answer = answer;
        this.maxMistakes = maxMistakes.getMaxMistakes();
    }

    public String masked() {
        StringBuilder maskedStr = new StringBuilder();
        for (char c : answer.toCharArray()) {
            maskedStr.append(guessed.contains(c) ? c : '*');
        }
        return maskedStr.toString();
    }

    public GuessResult guess(String input) {
        if (input == null || (input = norm(input)).length() != 1) {
            return new GuessResult(masked(), mistakes, maxMistakes, getStatus(false, false, false, true, false));
        }
        if (input.equals("?")) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.HINT);
        }

        boolean isNewGuess = guessed.add(input.charAt(0));
        int exists = answer.indexOf(input.charAt(0));
        if (exists < 0) {
            mistakes += (isNewGuess ? 1 : 0);
        }
        return new GuessResult(
                masked(),
                mistakes,
                maxMistakes,
                getStatus(won(), lost(), hit(exists, isNewGuess), false, repetition(isNewGuess)));
    }

    private GameStatus getStatus(boolean won, boolean lost, boolean hit, boolean invalidInput, boolean repetition) {
        if (invalidInput) return GameStatus.INVALID_INPUT;
        if (won) return GameStatus.WON;
        if (lost) return GameStatus.LOST;
        if (hit) return GameStatus.HIT;
        if (repetition) return GameStatus.REPETITION;
        return GameStatus.MISS;
    }

    private boolean won() {
        return answer.equals(masked());
    }

    private boolean lost() {
        return mistakes >= maxMistakes;
    }

    private boolean hit(int existence, boolean isNewGuess) {
        return existence >= 0 && isNewGuess;
    }

    private boolean repetition(boolean isNewGuess) {
        return !isNewGuess;
    }

    private static String norm(String s) {
        return s.toLowerCase().strip();
    }
}

package academy.game;

import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final String answer;
    private final char[] answerCharArray;
    private final int maxMistakes;
    private final Set<Character> guessed = new HashSet<>();
    private int mistakes = 0;

    public GameSession(String answer, final Difficulty maxMistakes) {
        if (answer == null || (answer = norm(answer)).length() < 2)
            throw new IllegalArgumentException("Загадываемое слово не должно быть короче 2-x символов!");
        if (!answer.codePoints().allMatch(Character::isAlphabetic)) {
            throw new IllegalArgumentException("Загадываемое слово должно состоять из букв!");
        }
        this.answer = answer;
        this.answerCharArray = answer.toCharArray();
        this.maxMistakes = maxMistakes.getMaxMistakes();
    }

    public GuessResult initialState() {
        return new GuessResult(masked(), 0, maxMistakes, null);
    }

    public String masked() {
        StringBuilder maskedStr = new StringBuilder();
        for (char c : answerCharArray) {
            maskedStr.append(guessed.contains(c) ? c : '*');
        }
        return maskedStr.toString();
    }

    public GuessResult guess(String input) {
        if (input == null || (input = norm(input)).length() != 1) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.INVALID_INPUT);
        }
        if (input.equals("?")) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.HINT);
        }
        if (!Character.isLetter((int) input.charAt(0))) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.INVALID_INPUT);
        }
        // чтобы нельзя было мутировать mistakes в случае окончания игры.
        if (won()) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.WON);
        }
        if (lost()) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.LOST);
        }
        boolean isNewGuess = guessed.add(input.charAt(0));

        if (repetition(isNewGuess)) {
            return new GuessResult(masked(), mistakes, maxMistakes, GameStatus.REPETITION);
        }

        int exists = answer.indexOf(input.charAt(0));

        if (hit(exists)) {
            return new GuessResult(masked(), mistakes, maxMistakes, won() ? GameStatus.WON : GameStatus.HIT);
        }
        // miss
        ++mistakes;
        return new GuessResult(masked(), mistakes, maxMistakes, lost() ? GameStatus.LOST : GameStatus.MISS);
    }

    private boolean won() {
        return answer.equals(masked());
    }

    private boolean lost() {
        return mistakes >= maxMistakes;
    }

    private boolean hit(int existence) {
        return existence >= 0;
    }

    private boolean repetition(boolean isNewGuess) {
        return !isNewGuess;
    }

    private static String norm(String s) {
        return s.toLowerCase().strip();
    }
}

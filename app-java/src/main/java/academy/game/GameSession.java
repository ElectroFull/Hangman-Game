package academy.game;

import java.util.HashMap;
import java.util.HashSet;

public class GameSession {
    private final String answer;
    private final int maxMistakes;
    private final HashSet<Character> guessed = new HashSet<>();
    private int mistakes = 0;


    public GameSession(String answer, int maxMistakes) {
        if (answer == null || (answer = norm(answer)).length() < 2)
            throw new IllegalArgumentException("Загадываемое слово не должно быть короче 2 символов!");
        this.answer = answer;
        this.maxMistakes = maxMistakes;
    }

    public String masked(){
        StringBuilder maskedStr = new StringBuilder();
        for (int x : answer.chars().toArray()){
            char c = (char) x;
            if (guessed.contains(c)){
                maskedStr.append(c);
            }else{
                maskedStr.append('*');
            }
        }
        return maskedStr.toString();
    }

    public GuessResult guess(String input){
        if (input == null || (input = norm(input)).length() != 1){
            return new GuessResult(masked(), mistakes, maxMistakes, false, false, false, true);
        }
        boolean isNewGuess = guessed.add(input.charAt(0));
        int exists = answer.indexOf(input.charAt(0));
        if (exists < 0) {
            mistakes += (isNewGuess ? 1 : 0);
        }
        return new GuessResult(masked(), mistakes, maxMistakes, won(), lost(), hit(exists, isNewGuess), false)
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    private boolean won(){
        return answer.equals(masked());
    }

    private boolean lost(){
        return mistakes >= maxMistakes;
    }

    private boolean hit(int existence, boolean isNewGuess){
        return existence >= 0 && isNewGuess;
    }

    private static String norm(String s){
        return s.toLowerCase().trim();
    }
}

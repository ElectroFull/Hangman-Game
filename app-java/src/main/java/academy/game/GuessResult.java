package academy.game;

public record GuessResult(String masked,
                          int mistakes,
                          int maxMistakes,
                          boolean won,
                          boolean lost,
                          boolean hit, boolean InvalidInput) {
}

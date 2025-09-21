package academy.game;

public record GuessResult(String masked, int mistakes, int maxMistakes, GameStatus status) {}

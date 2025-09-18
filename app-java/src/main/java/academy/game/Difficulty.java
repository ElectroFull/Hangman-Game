package academy.game;

public enum Difficulty {
    EASY(9),
    MEDIUM(6),
    HARD(3);

    private final int maxMistakes;


    Difficulty(int maxMistakes) {
        this.maxMistakes = maxMistakes;
    }

    public int getMaxMistakes() {
        return maxMistakes;
    }
}

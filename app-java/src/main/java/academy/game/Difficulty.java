package academy.game;

public enum Difficulty {
    EASY(8),
    MEDIUM(6),
    HARD(3);

    private final int maxMistakes;

    Difficulty(int maxMistakes) {
        this.maxMistakes = maxMistakes;
    }

    static Difficulty getRandomDifficulty() {
        return Difficulty.values()[(int) (Math.random() * Difficulty.values().length)];
    }

    public int getMaxMistakes() {
        return maxMistakes;
    }
}

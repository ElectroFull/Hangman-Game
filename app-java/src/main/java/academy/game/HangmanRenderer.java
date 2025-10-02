package academy.game;

public class HangmanRenderer {
    private static final String[] hangman = {
        """






        =====
    """,
        """
         +--+
         |  |
            |
            |
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
            |
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
         |  |
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
        /|  |
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
        /|\\ |
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
        /|\\ |
        /   |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
        /|\\ |
        / \\ |
            |
        =====
    """
    };
    private final double rate;

    public HangmanRenderer(final Difficulty difficulty) {
        rate = (double) (hangman.length - 1) / difficulty.getMaxMistakes();
    }

    public String render(int mistakes) {
        if (mistakes == 0) return "";
        return hangman[(int) Math.round(mistakes * rate)];
    }
}

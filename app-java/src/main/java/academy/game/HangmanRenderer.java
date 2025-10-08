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
        rate = (double) hangman.length / difficulty.getMaxMistakes();
    }

    public String render(int mistakes) {
        if (mistakes < 0) throw new IllegalArgumentException();
        if (mistakes == 0) return "";
        int frame = (int) Math.round(mistakes * rate) - 1;
        if (frame >= hangman.length) throw new IllegalArgumentException();
        return hangman[frame];
    }
}

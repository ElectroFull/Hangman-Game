package academy.game;

public class HangmanRenderer {
    private static final String[] hangman = {
        """







        """,
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
        /|\\|
            |
            |
        =====
    """,
        """
         +--+
         |  |
         @  |
        /|\\|
        /   |
            |
        =====
    """,
         """
         +--+
         |  |
         @  |
        /|\\|
        / \\|
            |
        =====
    """
    };
    private double rate;

    HangmanRenderer(Difficulty difficulty) {
        rate = (double) (hangman.length - 1) / difficulty.getMaxMistakes();
    }
    public String render(int mistakes){
        return hangman[(int)Math.round(mistakes * rate)];
    }
}

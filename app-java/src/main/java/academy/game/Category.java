package academy.game;

public enum Category {
    ANIMALS,
    FRUITS,
    FAMILY,
    TECHNOLOGY;

    public static Category getRandomCategory() {
        return Category.values()[(int) (Math.random() * Category.values().length)];
    }
}

package academy.game;

public enum Category {
    ANIMALS,
    FRUITS,
    FAMILY,
    TECHNOLOGY;

    static Category getRandomCategory(){
        return Category.values()[(int)(Math.random() * Category.values().length)];
    }
}

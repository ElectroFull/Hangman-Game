package academy.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();

    static {
        words.put(Category.ANIMALS, Map.of(
            Difficulty.EASY, List.of("кот", "пёс", "лис", "рак"),
            Difficulty.MEDIUM, List.of("енот", "выдра", "заяц", "барсук"),
            Difficulty.HARD, List.of("хамелеон", "аллигатор", "носорог", "шипохвост")
        ));

        words.put(Category.FRUITS, Map.of(
            Difficulty.EASY, List.of("лимон", "груша", "дыня", "слива"),
            Difficulty.MEDIUM, List.of("банан", "апельсин", "малина", "черника"),
            Difficulty.HARD, List.of("мандарин", "клементин", "грейпфрут", "авокадо")
        ));

        words.put(Category.FAMILY, Map.of(
            Difficulty.EASY, List.of("мама", "папа", "дядя", "тётя", "сестра", "брат"),
            Difficulty.MEDIUM, List.of("бабушка", "дедушка", "племянник", "племянница"),
            Difficulty.HARD, List.of("праправнук", "крёстная", "крёстный", "прародитель")
        ));

        words.put(Category.TECHNOLOGY, Map.of(
            Difficulty.EASY, List.of("робот", "диск", "чип", "код", "сайт", "мышка"),
            Difficulty.MEDIUM, List.of("сервер", "гаджет", "монитор", "принтер"),
            Difficulty.HARD, List.of("процессор", "компилятор", "интерпретатор", "алгоритм")
        ));
    }

    public static String getRandomWord(Category category, Difficulty difficulty){
        Category chosenCategory = (category != null) ? category : getRandomCategory();
        Difficulty chosenDifficulty = (difficulty != null) ? difficulty : getRandomDifficulty();
        List<String> wordList = words.get(chosenCategory).get(chosenDifficulty);
        return wordList.get((int)(Math.random() * wordList.size()));
    }

    public static Category getRandomCategory(){
        return Category.values()[(int)(Math.random() * Category.values().length)];
    }

    public static Difficulty getRandomDifficulty(){
        return Difficulty.values()[(int)(Math.random() * Difficulty.values().length)];
    }
}

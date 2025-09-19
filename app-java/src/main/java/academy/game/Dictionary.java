package academy.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static final Map<Category, Map<Difficulty, List<String>>> words = new HashMap<>();
    private static final Map<String, String> hints = new HashMap<>();

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

        // ANIMALS
        hints.put("кот", "Домашнее животное, любит молоко");
        hints.put("пёс", "Верный друг человека");
        hints.put("лис", "Рыжий, хитрый зверь");
        hints.put("рак", "Живёт в реке, ходит задом наперёд");
        hints.put("енот", "Полосатый хвост, любит мыть еду");
        hints.put("выдра", "Водное животное, отличный пловец");
        hints.put("заяц", "Длинноухий, быстрый, любит морковь");
        hints.put("барсук", "Ночной зверь, чёрно-белая морда");
        hints.put("хамелеон", "Меняет цвет кожи");
        hints.put("аллигатор", "Крупный хищник с мощной челюстью");
        hints.put("носорог", "Тяжёлый зверь с рогом на носу");
        hints.put("шипохвост", "Ящерица с колючим хвостом");

        // FRUITS
        hints.put("лимон", "Кислый жёлтый фрукт");
        hints.put("груша", "Сочная, в форме лампочки");
        hints.put("дыня", "Большая сладкая бахчевая культура");
        hints.put("слива", "Фиолетовый фрукт с косточкой");
        hints.put("банан", "Длинный жёлтый фрукт");
        hints.put("апельсин", "Цитрус с оранжевой кожурой");
        hints.put("малина", "Красная сладкая ягода");
        hints.put("черника", "Синяя лесная ягода");
        hints.put("мандарин", "Маленький цитрус, легко чистить");
        hints.put("клементин", "Гибрид мандарина и апельсина");
        hints.put("грейпфрут", "Крупный цитрус, слегка горький");
        hints.put("авокадо", "Зелёный плод с крупной косточкой");

        // FAMILY
        hints.put("мама", "Родитель");
        hints.put("папа", "Родитель");
        hints.put("дядя", "Брат мамы или папы");
        hints.put("тётя", "Сестра мамы или папы");
        hints.put("сестра", "Дочь твоих родителей");
        hints.put("брат", "Сын твоих родителей");
        hints.put("бабушка", "Мама твоих родителей");
        hints.put("дедушка", "Папа твоих родителей");
        hints.put("племянник", "Сын твоего брата или сестры");
        hints.put("племянница", "Дочь твоего брата или сестры");
        hints.put("праправнук", "Сын сына правнука");
        hints.put("крёстная", "Женщина, крестившая ребёнка");
        hints.put("крёстный", "Мужчина, крестивший ребёнка");
        hints.put("прародитель", "Самый древний предок");

        // TECHNOLOGY
        hints.put("робот", "Механизм, выполняющий команды");
        hints.put("диск", "Круглый носитель данных");
        hints.put("чип", "Микросхема для электроники");
        hints.put("код", "Набор инструкций для компьютера");
        hints.put("сайт", "Страница в интернете");
        hints.put("мышка", "Устройство для управления курсором");
        hints.put("сервер", "Компьютер для хранения и обработки данных");
        hints.put("гаджет", "Маленькое электронное устройство");
        hints.put("монитор", "Экран для вывода изображения");
        hints.put("принтер", "Устройство для печати");
        hints.put("процессор", "Мозг компьютера");
        hints.put("компилятор", "Преобразует код в исполняемый файл");
        hints.put("интерпретатор", "Выполняет код построчно");
        hints.put("алгоритм", "Последовательность шагов для решения задачи");
    }

    public static String getRandomWord(Category category, Difficulty difficulty){
        Category chosenCategory = (category != null) ? category : getRandomCategory();
        Difficulty chosenDifficulty = (difficulty != null) ? difficulty : getRandomDifficulty();
        List<String> wordList = words.get(chosenCategory).get(chosenDifficulty);
        return wordList.get((int)(Math.random() * wordList.size()));
    }

    public static String getHint(String word){
        return hints.get(word);
    }

    public static Category getRandomCategory(){
        return Category.values()[(int)(Math.random() * Category.values().length)];
    }

    public static Difficulty getRandomDifficulty(){
        return Difficulty.values()[(int)(Math.random() * Difficulty.values().length)];
    }
}
// TODO: Add logic for hints mechanism in Application.java

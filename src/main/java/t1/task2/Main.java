package t1.task2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

        // Найдите в списке целых чисел 3-е наибольшее число
        Integer thirdMax = integers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();

        // Найдите в списке целых чисел 3-е наибольшее «уникальное» число
        Integer thirdMaxUniq = integers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();

        List<Employee> employees = Arrays.asList(
                new Employee("Вася", 20, "Инженер"),
                new Employee("Петя", 25, "Менеджер"),
                new Employee("Коля", 30, "Инженер"),
                new Employee("Максим", 35, "Аналитик"),
                new Employee("Маша", 34, "Инженер"),
                new Employee("Катя", 21, "Инженер")
        );

        // Получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
        List<String> oldestEmployeesNames = employees.stream()
                .filter(it -> "Инженер".equals(it.getTitle()))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .toList();

        // Посчитайте средний возраст сотрудников с должностью «Инженер»
        Double averageEngineerAge = employees.stream()
                .filter(it -> "Инженер".equals(it.getTitle()))
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0.0);


        // Найдите в списке слов самое длинное
        List<String> words = Arrays.asList("word", "cord", "word_word", "word_word_1", "word_word_word");

        String longestWord = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();


        // Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        // Постройте хеш-мапу, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
        String text = "строка с набором слов в нижнем регистре разделенных пробелом с набором слов";

        Map<String, Long> wordCounterMap = Arrays.stream(text.split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
        // если слова имеют одинаковую длину, то должен быть сохранен алфавитный порядок
        words.stream()
                .sorted(Comparator.comparingInt(String::length)
                        .thenComparing(String::compareTo))
                .forEach(System.out::println);

        // Имеется массив строк, в каждой из которых лежит набор из 5 слов, разделенных пробелом,
        // найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них

        String longestWordFromStrings = Arrays.stream(new String[]{"one two three four five1", "one two three four five12"})
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}

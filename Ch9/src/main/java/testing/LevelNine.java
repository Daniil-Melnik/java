/*
9. Проверить связные хеш-множества, попробовать поменять в них порядок ( доступа, изменения)
+ создать связное хеш-множество с порядком доступа, наполнить его значениями, вывести на экран
  + прочитать значение из середины множества, посмотреть состояние множества

+ создать связное хеш-отображение с порядком ввода, наполнить его значениями, вывести на экран
  + прочитать значение из отображения и посмотреть его состояние

+ создать малую коллекцию из 5 элементов и попробовать его изменить

+ создать коллекцию и перевести её в массив, этот массив перевести назад в коллекцию (просто toArray и toArray с нулевым списком)

+ создать стек, набить его и вывести на экран удалением головы

+ создать битовое множество, провести операции над ним

*/

package testing;

import java.util.*;

public class LevelNine {

    private static final int SIZE = 5;

    public static void main(String [] args){
        LinkedHashSet<String> linkedSet = new LinkedHashSet<>(); // связное хеш-множество
        LinkedHashMap<String, String> linkedMap1 = new LinkedHashMap<>(10, 0.85F, true); // порядок доступа
        LinkedHashMap<String, String> linkedMap2 = new LinkedHashMap<>(10, 0.85F, false); // порядок ввода

        for (int i = 0; i < SIZE; i++){ // наполнение контейнеров
            linkedSet.add("valSet_" + i);
            linkedMap1.put("keyMap_" + i, "valMap_" + i);
            linkedMap2.put("keyMap_" + i, "valMap_" + i);
        }

        // связные множества - хранят элементы в порядке ввода в контейнер
        // связные отображения - хранят элементы в порядке ввода/доступа в контейнере

        // порядок ввода - новый вводимый элемент добавляется в конец контейнера
        // порядок доступа - последний по доступу элемент находится в конце контейнера

        System.out.println("НАЧАЛЬНОЕ СОСТОЯНИЕ КОНТЕЙНЕРОВ");
        System.out.println(linkedSet);
        System.out.println("порядок доступа: " + linkedMap1);
        System.out.println("порядок ввода: " + linkedMap2);

        System.out.println("ПРОВЕРКА ПОРЯДКА ДОСТУПА");
        System.out.println(linkedMap1.get("keyMap_3"));
        System.out.println(linkedMap1); // перенос ключа keyMap_3 в конец списка

        System.out.println("ПРОВЕРКА ПОРЯДКА ВВОДА");
        linkedMap2.put("keyMap_-1", "valMap_-1");
        System.out.println(linkedMap2); // ввод в конец последнего введения

        // проверка малых коллекций
        List<String> list = List.of("1", "2", "3"); // создание мелких коллекций
        Set<String> set = Set.of("1", "2", "3");
        Map<String, String> map = Map.ofEntries(
                Map.entry("1", "e1"),
                Map.entry("2", "e2"));

        Iterator<String> lIterator = list.iterator(); // поучение итераторов для list и set
        Iterator<String> sIterator = set.iterator();

        try {
            lIterator.next();
            lIterator.remove();
        } catch (UnsupportedOperationException e){
            System.out.println("Малые коллекциии неизменяемы! - список " + e.getMessage());
        }

        try {
            sIterator.next();
            sIterator.remove();
        } catch (UnsupportedOperationException e){
            System.out.println("Малые коллекциии неизменяемы! - множество " + e.getMessage());
        }

        // коллекция - массив - коллекция
        ArrayList<String> sArrList = new ArrayList<>(List.of("1", "2", "3"));
        Object [] sArrO = sArrList.toArray(); // в простом toArray можно создавать массив Object, приведение типов не поможет
        String [] sArrS = sArrList.toArray(new String[0]); // решение: добавление в toArray нулевого массива целевого типа

        System.out.println("ПРОВЕРКА СТЕКА");
        // стек (LIFO)

        Stack<String> stack = new Stack<>();

        for (int i = 0; i < SIZE; i++) System.out.print(stack.push("val_" + i) + " "); // последний введённый - в конце контейнера

        System.out.println("\npeek = " + stack.peek());

        while (!stack.isEmpty()) System.out.print(stack.pop() + " ");

        System.out.println("\nПРОВЕРКА БИТОВЫХ МНОЖЕСТВ");

        // Битовые множества (хранение, например, флагов)
        // в структуре хранятся индексы установленных в единицу битов

        BitSet bitset = new BitSet(10);
        System.out.println(bitset);
        for (int i = 0; i < 10; i+=2) bitset.set(i);
        System.out.println("Установленный массив = " + bitset); // выдаст массив четных чисел - индексов уствновленных битов
        bitset.clear(2); // сброс бита с индексм 2
        System.out.println("После сброса двойки = " + bitset); // из массива пропала двойка

        BitSet bitset2 = new BitSet(10); // создание второй битовой маски
        bitset2.set(2, 5); // установка в единицу всех в [2 по 5)
        System.out.println("bitset2 = " + bitset2);

        bitset.and(bitset2);

        System.out.println("После and = " + bitset); // выдаст только 4

        bitset.or(bitset2);

        System.out.println("После or = " + bitset); // выдаст только 2, 3, 4

        bitset.xor(bitset2);

        System.out.println("После xor = " + bitset); // выдаст пустоту



    }

}

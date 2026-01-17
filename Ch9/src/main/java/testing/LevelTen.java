/*
10. Попробовать посмотреть поддиапазон, проверить влияние изменений в поддиапазоне на список
+ создать тестовый список ArrayList на 25 элементов
- создать второй список, на основе первого. Взять 10 элементов
- посмотреть оба списка

- создать тестовое отображение на 25 элементов
- создать подотображение на 10 элементов на основе первого
- провести операцию над подотображением

- получить представление первого отображения
- получить подколлекцию от представления
- выполнить удаление из подколлекции
- посмотреть коллекцию и всё первое отображение
*/

package testing;

import java.util.*;

public class LevelTen {

    private static int SIZE = 25;
    private static int SUB_SIZE = 10;
    private static int SUB_START = 3;

    public static void main (String[] args){
        ArrayList<String> arrList= new ArrayList<>();
        List<String> arrSubList;

        TreeMap<String, String> map = new TreeMap<>();
        TreeMap<String, String> subMap;

        for (int i = 0; i < SIZE; i++){
            arrList.add("i" + i);
            map.put("key_" + i, "val_" + i);
        }

        arrSubList = arrList.subList(SUB_START, SUB_START + SUB_SIZE);

        // получение поддиапазона списка
        System.out.println("ПОДДИАПАЗОН СПИСКОВ");
        System.out.println(arrList);
        System.out.println(arrSubList);
        arrSubList.remove(0);
        System.out.println(arrList); // поддиапазон = проекция на весь список
        System.out.println(arrSubList); // элемент i8 удалён из поддиапазона и подсписка


        // получение поддипазона представления
        System.out.println("ПОДДИАПАЗОН ПРЕДСТАВЛЕНИЯ");
        SortedSet<String> keys = (SortedSet<String>) map.keySet();
        SortedSet<String> subKeys = keys.subSet("key_0", "key_6");
        System.out.println(subKeys);
        System.out.println(map);
        System.out.println(keys);
        System.out.println(subKeys);

        subKeys.remove("key_5"); // из представления удален ключ key_5, всё что с ним связано будет удалено:
                                    // ключ в представлении -> элемент с ключем в отображении

        System.out.println("удаление key_5");
        System.out.println(map);
        System.out.println(keys);
        System.out.println(subKeys);

    }
}

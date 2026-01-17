/*
8. Рассмотреть три типа представления: ключи, значения, пары ключ-значение
+ создать малое отображение из 10-ти элементов
+ распечатать представления через keyset(), values(), entrySet()
+ удалить из представления, соответсвенно ключ, значение, пару и посмотреть в срввнении отображение и представление
*/

// представление - коллекция на основе отображения

package testing;

import java.util.*;

public class LevelEight {
    private static final int SIZE = 10;
    public static void main(String[] args){
        HashMap<String, String> hashMapSmall = new HashMap<>();

        for (int i = 0; i < SIZE; i++){
            hashMapSmall.put("key_" + i, "val_" + i);
        }

        System.out.println("ФОРМИРОВАНИЕ ПРЕДСТАВЛЕНИЙ");

        Set<String> keys = hashMapSmall.keySet(); // именно Set, не реализации
                                                  // ключи всегда уникальны,Ю поэтому - множество

        System.out.println(hashMapSmall);
        System.out.println(keys);

        Collection<String> values = hashMapSmall.values(); // Collection<>, так как в значениях
                                                           // могут быть повторы

        System.out.println(values);

        Set<Map.Entry<String, String>> entries = hashMapSmall.entrySet(); // все уникальны из-за уникальности ключа

        System.out.println(entries);

        System.out.println("УДАЛЕНИЕ ИЗ ПРЕДСТАВЛЕНИЯ КЛЮЧЕЙ");

        keys.remove("key_0"); // удалить ключ key_0 из представления
        System.out.println(keys); // key_0 удалён из представления
        System.out.println(hashMapSmall); // объёкт с key_0 удалён из отображения

        System.out.println("УДАЛЕНИЕ ИЗ ПРЕДСТАВЛЕНИЯ ЗНАЧЕНИЙ");

        values.remove("val_1"); // удаление значения val_1
        System.out.println(values); // val_1 удалено из представления значений
        System.out.println(hashMapSmall); // пара с val_1 удалена из отображения

        System.out.println("УДАЛЕНИЕ ИЗ ПРЕДСТАВЛЕНИЯ ПАР");

        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        System.out.println(iterator.next()); // переход по парам через итератор
        iterator.remove(); // удаление (key_3,val_3) из множества отображения пар
        System.out.println(entries); // пара исчезнет из коллекции
        System.out.println(hashMapSmall); // пара исчезнет из отображения

    }
}

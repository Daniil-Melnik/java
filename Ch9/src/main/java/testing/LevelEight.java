/*
8. Рассмотреть три типа представления: ключи, значения, пары ключ-значение
+ создать малое отображение из 10-ти элементов
+ распечатать представления через keyset(), values(), entrySet()
+ удалить из представления, соответсвенно ключ, значение, пару и посмотреть в срввнении отображение и представление

- проверить равенство одинаковых значений по разным ключам
- создать отображение идентинчности, проверить равенство в нём
*/

// представление - коллекция на основе отображения

package testing;

import java.util.*;

public class LevelEight {
    private static final int SIZE = 10;
    public static void main(String[] args){
        HashMap<String, String> hashMapSmall = new HashMap<>();

        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();

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

        System.out.println("ПРОВЕРКА РАВЕНТСВА В ОБЫЧНОМ ОТОБРАЖЕНИИ");

        hashMapSmall.put("key_9a", "val_9");
        System.out.println(hashMapSmall.get("key_9").equals(hashMapSmall.get("key_9a"))); // проверка равенства значений по двум ключам true
        System.out.println(hashMapSmall.get("key_9").equals(hashMapSmall.get("key_8"))); // проверка значение по двум ключам (значения разные)

        System.out.println("ПРОВЕРКА РАВЕНТСВА В ОТОБРАЖЕНИИ ИДЕНТИЧНОСТИ");

        for (int i = 0; i < SIZE; i++){
            identityHashMap.put("iKey_" + i, "iVal_" + i);
        }
        identityHashMap.put("iKey_9a", "iVal_9");

        System.out.println(identityHashMap.get("key_9") == (identityHashMap.get("key_9a"))); // сравнение через == , в отличие от обычного отображение
    }
}

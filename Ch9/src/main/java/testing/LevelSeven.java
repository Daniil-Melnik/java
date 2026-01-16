/*
6. Сравнить работу TreeMap и HashMap
+ создать по два экземпляра (по одному с 10 элементами и по одному с 500*10^3) отображений
  + TreeMap - простой конструктор
  + HashMap - конструктор с объёмом и коэффициентом заполнения
+ заполнить отображения значениями HasableEntity (HashMap с у четом коэффициента заполнения),
  замерить время выполнения)
+ выполнить вывод каждого малого отображения в консоль через forEach()
+ замерить время нахождения элемента по ключу в обоих больших через get()
+ проверить на отсутсвующих значениях getOrDefault()
+ проверить существование ключа в отображении через containsKey()
+ проверить методы обновления записей по ключу на малых отображениях
  + smth.put(word, newEntity) - можно ли так?
  + smth.put(word, smth.get(word) + 1)
  + smth.put(word, smth.getOrDefault(word) + 1)
  + smth.putIfAbsent(word, 0) -- надёжный
    smth.put(word, smth.get(word)+1)
+ проверить надёжный метод на большых отображениях с замером времени
+ проверить метод merge()
+ проверить compute()
+ проверить метод computeIfPresent()
+ проверить метод computeIfAbsent()
+ проверить метод replaceAll()
*/

package testing;

import testing.ext.HashableEntity;
import testing.utils.PrintUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class LevelSeven {

    private static final int SIZE_BIG = 500_000;
    private static final int SIZE_SMALL = 10;

    private static long timeStamp0;
    private static long timeStamp1;
    private static long timeStamp2;
    private static long timeStamp3;
    private static long timeStamp4;
    private static long timeStamp5;
    private static long timeStamp6;
    private static long timeStamp7;

    public static void main(String[] args){
        HashMap<String, HashableEntity> hashMapBig = new HashMap<>(50, 0.75F); // создание большх отображений
        TreeMap<String, HashableEntity> treeMapBig = new TreeMap<>();

        HashMap<String, HashableEntity> hashMapSmall = new HashMap<>(); // создание малых отображений
        TreeMap<String, HashableEntity> treeMapSmall = new TreeMap<>();

        HashMap<String, String> map1 = new HashMap<>(); // создание отображений для тестирования методов
        HashMap<String, String> map2 = new HashMap<>(); // интерфейса Map
        HashMap<String, String> map3 = new HashMap<>();

        timeStamp0 = System.currentTimeMillis(); // отсечение старта работы

        for (int i = 0; i < SIZE_BIG; i++){ // заполнение большого Хэш-отображения
            hashMapBig.put(String.format("key_%d", i), new HashableEntity(i, String.format("str_%d", i)));
        }

        timeStamp1 = System.currentTimeMillis();

        for (int i = 0; i < SIZE_BIG; i++){ // заполнение большого древовидного отображения
            treeMapBig.put(String.format("key_%d", i), new HashableEntity(i, String.format("str_%d", i)));
        }

        timeStamp2 = System.currentTimeMillis();

        for (int i = 0; i < SIZE_SMALL; i++){ // заполнение обоих малых отображений
            hashMapSmall.put(String.format("key_%d", i), new HashableEntity(i, String.format("str_%d", i)));
            treeMapSmall.put(String.format("key_%d", i), new HashableEntity(i, String.format("str_%d", i)));
        }

        hashMapSmall.forEach((k ,v) -> System.out.println(k + " - " + v.toString())); // простая проверка метода forEach на Хэш-отображении

        System.out.println();

        treeMapSmall.forEach((k ,v) -> System.out.println(k + " - " + v.toString())); // проверка на древовидном отображении
                                                                                                          // древовидное отсортировано, хэщ - "случайно"

        timeStamp3 = System.currentTimeMillis(); // отсечение начала выбора по ключу в середине

        System.out.println(hashMapBig.get("key_250000")); // выбор по ключу в середине Хэш-отображения

        timeStamp4 = System.currentTimeMillis();

        System.out.println(treeMapBig.get("key_250000")); // выбор по ключу в середине древовидного отображения

        timeStamp5 = System.currentTimeMillis();

        System.out.println(hashMapSmall.get("key_20")); // вернёт null, так как по такому ключу ничего не хранится
        System.out.println(hashMapSmall.getOrDefault("key_20", new HashableEntity())); // вернёт определённое на такой
                                                                                            // случай значение 500

        // == проверка простого (1) обновления записи

        int keyN = 2;

        System.out.println("\nПРОСТОЕ ОБНОВЛЕНИЕ С ПОЛНОЙ ЗАМЕНОЙ");
        System.out.println(hashMapSmall.get("key_2").toString());
        hashMapSmall.put("key_2", new HashableEntity(89, "new_str"));
                          // замена старого значения по ключу на полностью новое
        System.out.println(hashMapSmall.get("key_2").toString());

        System.out.println("\nПРОСТОЕ ОБНОВЛЕНИЕ С ОБНОВЛЕНИЕМ ЭКЗЕМПЛЯРА");
        System.out.println(hashMapSmall.get("key_2").toString());
        hashMapSmall.put("key_2", hashMapSmall.get("key_2").setStr("str_2"));
                         // Опасно, так как get может вернуть null (само по себе возвращение не страшно)
                         // но обращение к null с методом выдаст ошибку
        System.out.println(hashMapSmall.get("key_2").toString());

        // == проверка обновления с контролем существования (2)

        System.out.println("\nОБНОВЛЕНИЕ С ПРОВЕРКОЙ");

        try {
            hashMapSmall.put("key_25", hashMapSmall.get("key_25").setStr("str_25")); // проверка с заведомым отсутсвием по ключу "25"
        } catch (NullPointerException e){
            System.out.println("Ошибка нулевого значения! По такому ключу ничего нет, чего нет - то обновить нельзя!"); // ОШИБКА!!!
        }

        // вариант выхода: взять в случае отсутвия значения - значение по умолчанию, тогда не будет null
        hashMapSmall.put("key_25", hashMapSmall.getOrDefault("key_25", new HashableEntity(25, "str")).setStr("str_25"));


        System.out.println(hashMapSmall.get("key_25").toString());

        // проверка обновления с предварительной установкой значения, если такого не существует (3)

        System.out.println("\nОБНОВЛЕНИЕ С ПРЕДВАРИТЕЛЬНОЙ УСТАНОВКОЙ");

        // также можно предварительно установить (если пусто - будет установлено, иначе - ничего)
        treeMapSmall.putIfAbsent("key_26", new HashableEntity(26, "str_0"));
        System.out.println(treeMapSmall.get("key_26"));
        treeMapSmall.put("key_26", treeMapSmall.get("key_26").setStr("str_26"));
        System.out.println(treeMapSmall.get("key_26"));

        timeStamp6 = System.currentTimeMillis();

        treeMapBig.putIfAbsent("key_2625222", new HashableEntity(26, "str_0"));
        treeMapBig.put("key_2625222", treeMapBig.get("key_2625222").setStr("str_2625222"));

        timeStamp7 = System.currentTimeMillis();

        // == тсетирование спец. методов на множествах
        // merge()

        map1.put("1", "val_1");
        map1.put("3", "val_3");
        map1.put("5", "val_5");

        map2.put("1", "val_2");
        map2.put("3", "val_4");
        map2.put("5", "val_6");
        map2.put("7", "val_7");

        map3.putAll(map1);

        System.out.println(map3);

        map2.forEach((key, value) ->
                map3.merge(key, value, (v3, v2) -> v3 + v2)
            );

        // то есть: к map3 пробуют прибавиться все элементы map2 проходя через условие выбора/обработки значений обоих кандидатов: v3 из map3 и v2 из map2

        System.out.println(map3);

        map3.merge("1", "NNN", (v1, v2) -> v1 + v2);

        System.out.println(map3);

        map3.merge("35", "MMM", (v1, v2) -> v1 + v2);

        System.out.println(map3);

        // compute()

        map3.compute("35", (k, v) -> v+k);
        System.out.println(map3.get("35"));
        // берёт объект по ключу "35", потом передаёт пару ключ-значение в функцию,
        // где она преобразуется и устанавливается в "35"

        map1.forEach((key, value) ->
                map1.compute(key, (k, v) -> v + "*"));

        System.out.println(map1);

        // computeIfPresent()

        map1.computeIfPresent("KKK", (k, v) -> "TEST"); // KKK нет, тогда ничего не делать
        System.out.println(map1); // {1=val_1*, 3=val_3*, 5=val_5*}

        map1.computeIfPresent("1", (k, v) -> "TEST"); // "1" есть - меняем значение по "1" на TEST
        System.out.println(map1); // {1=TEST, 3=val_3*, 5=val_5*}

        // computeIfAbsent()

        map1.computeIfAbsent("KKK", (k) -> "TEST"); // т. к. ключа ККК нет - в него добавляется "TEST"
        System.out.println(map1); // {1=TEST, 3=val_3*, 5=val_5*, KKK=TEST}

        map1.computeIfAbsent("1", (k) -> "GGG");
        System.out.println(map1); // {1=TEST, 3=val_3*, 5=val_5*, KKK=TEST}

        map2.replaceAll((key, value) -> "REPLACED"); // заменить все на что-то
        System.out.println(map2);

        /*try {
            PrintUtils.printResultsToFile(createOutTable(), "\\out7.txt");
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл! " + e.getMessage() + " " + e.getClass());
        }*/
    }

    private static String createOutTable(){

        long diffInitHash = timeStamp1-timeStamp0; // длительность заполнения хеш-отображения
        long diffInitTree = timeStamp2-timeStamp1; // длительность заполнения древовидного отображения
        long diffFindMiddleHash = timeStamp4 - timeStamp3; // длительность поиска середины Хэш
        long diffFindMeddleTree = timeStamp5 - timeStamp4; // длительность поиска середины Дерево
        long diffPutIntoBig = timeStamp7 - timeStamp6;

        return String.format(
                        "заполнить - Hash - %d\n" +
                        "заполнить - Tree - %d\n" +
                                "найти посередине Hash - %d\n" +
                "найти посередине Tree - %d\n" +
                "вставить новый в большой - %d\n",

                diffInitHash,
                diffInitTree,
                diffFindMiddleHash,
                diffFindMeddleTree,
                diffPutIntoBig
        );
    }
}

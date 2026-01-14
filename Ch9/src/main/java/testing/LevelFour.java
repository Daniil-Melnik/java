/*
3. Замерить и сравнить работу TreeSet и HashSet аналогично (1)
+ реализовать расширение класса Entity с определённым методом hashCode
+ создать хэш-ножество HashSet и древовидное множество TreeSet
+ заполнить каждое множество на SIZE элементов
+ попробовать вставить в множество повторяющийся элемент и посмотреть что получится
+ перебрать с замером времени каждое множество произваольным доступом и через итератор
+ проверить скорость поиска (проверки наличия) элемента на обоих множествах с помощью contains
- посмотреть время сортировки HeshSet
* */

package testing;

import testing.ext.HashableEntity;

import java.util.*;

public class LevelFour {
    private static final int SIZE = 100;
    private static final String [] WORDS = {"asdasd", "4tvgg", "aewrt", "re", "sdfg", "gfh", "dfjggfgfg", "sdfg", "r", "dsf", "asgh", "dsdf", "ASDasd", "ad"};

    public static void main(String [] args){

        HashSet<HashableEntity> hashSetEntity = new HashSet<HashableEntity>();
        TreeSet<HashableEntity> treeSetEntity = new TreeSet<HashableEntity>();

        for (int i = 0; i < SIZE; i++){
            hashSetEntity.add(new HashableEntity(i, WORDS[i % 14]));
        }

        for (int i = 0; i < SIZE; i++){
            treeSetEntity.add(new HashableEntity(i, WORDS[i % 14]));
        }

        HashSet<String> hashString = new HashSet<String>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        hashString.add("2");

        for (String str : hashString) {System.out.printf("%s ", str);}
        System.out.println();

        Iterator<HashableEntity> hashIterator = hashSetEntity.iterator();
        Iterator<HashableEntity> treeIterator = treeSetEntity.iterator();

        while (hashIterator.hasNext()){
            System.out.println(hashIterator.next().toString()); // печать в "случайном" порядке
        }

        System.out.println();

        while (treeIterator.hasNext()){
            System.out.println(treeIterator.next().toString()); // печать в порядке, определённом в compareTo() из Entity
        }

        System.out.println(hashSetEntity.contains(new HashableEntity(86, "aewrt"))); // будет возвращен true (так как такой экземпляр есть)
                                                                                           // это покажут hashCode() и equals()
        System.out.println(hashSetEntity.contains(new HashableEntity(87, "aewrt"))); // будет возвращен false, так как нет идентичного ни по
                                                                                           // hashCode() ни по equals()

        System.out.println();

        System.out.println(treeSetEntity.contains(new HashableEntity(86, "aewrt"))); // будет возвращен true, но ниже описание проблемы
        System.out.println(treeSetEntity.contains(new HashableEntity(88, "aewrt"))); // будет возвращен true (!)
        // так как в TreeSet ведётся поиск только через compareTo(), который унаследован от суперкласса, где
        // проверка только по полю num. Найдётся такой экземпляр, у которого num - 88 а str - не имеет значения

        System.out.println(new HashableEntity(86, "aewrt").toString());
        System.out.println(new HashableEntity(88, "aewrt").toString());
    }
}

/*
3. Замерить и сравнить работу TreeSet и HashSet аналогично (1)
+ реализовать расширение класса Entity с определённым методом hashCode
+ создать хэш-ножество HashSet и древовидное множество TreeSet
+ заполнить каждое множество на SIZE элементов
+ попробовать вставить в множество повторяющийся элемент и посмотреть что получится
+ перебрать с замером времени каждое множество произваольным доступом и через итератор
+ проверить скорость поиска (проверки наличия) элемента на обоих множествах с помощью contains
* */

package testing;

import testing.ext.HashableEntity;
import testing.utils.PrintUtils;

import java.io.IOException;
import java.util.*;

public class LevelFour {
    private static long timeStamp0;
    private static long timeStamp1;
    private static long timeStamp2;
    private static long timeStamp3;
    private static long timeStamp4;
    private static long timeStamp5;
    private static long timeStamp6;
    private static long timeStamp7;
    private static long timeStamp8;
    private static long timeStamp9;
    private static long timeStamp10;

    private static final int SIZE = 100_000;
    private static final String [] WORDS = {"asdasd", "4tvgg", "aewrt", "re", "sdfg", "gfh", "dfjggfgfg", "sdfg", "r", "dsf", "asgh", "dsdf", "ASDasd", "ad"};

    public static void main(String [] args){

        HashSet<HashableEntity> hashSetEntity = new HashSet<HashableEntity>(); // создание хеш-множества
        TreeSet<HashableEntity> treeSetEntity = new TreeSet<HashableEntity>(); // создание древовидного множества

        timeStamp0 = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++){
            hashSetEntity.add(new HashableEntity(i, WORDS[i % 14])); // заполнение экземплярами хеш-множества
        }

        timeStamp1 = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++){
            treeSetEntity.add(new HashableEntity(i, WORDS[i % 14])); // заполнение экземплярами древовидного множества
        }

        timeStamp2 = System.currentTimeMillis();

        HashSet<String> hashString = new HashSet<String>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")); // создание маленького множества
        hashString.add("2"); // характерно для множества - дубликата не появилось

        for (String str : hashString) {System.out.printf("%s ", str);} // печать множества через for-each
        System.out.println();

        Iterator<HashableEntity> hashIterator = hashSetEntity.iterator(); // моздание итераторов для множеств
        Iterator<HashableEntity> treeIterator = treeSetEntity.iterator();

        timeStamp3 = System.currentTimeMillis();

        while (hashIterator.hasNext()){
            System.out.println(hashIterator.next().toString()); // печать в "случайном" порядке
        }

        timeStamp4 = System.currentTimeMillis();

        System.out.println();

        timeStamp5 = System.currentTimeMillis();

        while (treeIterator.hasNext()){
            System.out.println(treeIterator.next().toString()); // печать в порядке, определённом в compareTo() из Entity
        }

        timeStamp6 = System.currentTimeMillis();

        System.out.println(hashSetEntity.contains(new HashableEntity(86, "aewrt"))); // будет возвращен true (так как такой экземпляр есть)
                                                                                           // это покажут hashCode() и equals()
        timeStamp7 = System.currentTimeMillis();

        System.out.println(hashSetEntity.contains(new HashableEntity(87, "aewrt"))); // будет возвращен false, так как нет идентичного ни по
                                                                                           // hashCode() ни по equals()
        timeStamp8 = System.currentTimeMillis();

        System.out.println(treeSetEntity.contains(new HashableEntity(86, "aewrt"))); // будет возвращен true, но ниже описание проблемы

        timeStamp9 = System.currentTimeMillis();

        System.out.println(treeSetEntity.contains(new HashableEntity(88, "aewrt"))); // будет возвращен true (!)
        // так как в TreeSet ведётся поиск только через compareTo(), который унаследован от суперкласса, где
        // проверка только по полю num. Найдётся такой экземпляр, у которого num - 88 а str - не имеет значения

        timeStamp10 = System.currentTimeMillis();

        System.out.println(new HashableEntity(86, "aewrt").toString());
        System.out.println(new HashableEntity(88, "aewrt").toString());

        try {
            PrintUtils.printResultsToFile(createOutTable(), "\\out4.txt");
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл! " + e.getMessage() + " " + e.getClass());
        }
    }

    private static String createOutTable(){

        long diffInitHash = timeStamp1-timeStamp0; // длительность заполнения хеш-множества
        long diffInitTree = timeStamp2-timeStamp1; // длительность заполнения древовидного множества
        long diffPrintHash = timeStamp4 - timeStamp3; // длительность печати хеш-множества
        long diffPrintTree = timeStamp6 - timeStamp5; // длительность печати древовидного множества
        long diffFindHashT = timeStamp7 - timeStamp6; // длительность проверки наличия в хеш-множестве true
        long diffFindHashF = timeStamp8 - timeStamp7; // длительность проверки наличия в хеш-множестве false
        long diffFindTreeT1 = timeStamp9 - timeStamp8; // длительность проверки наличия в древо-множестве true1
        long diffFindTreeT2 = timeStamp10 - timeStamp9; // длительность проверки наличия в древо-множестве true2

        return String.format(
                        "заполнить - Hash - %d\n" +
                        "заполнить - Tree - %d\n" +
                        "Печать - Hash - %d\n" +
                        "Печать - Tree - %d\n" +
                        "проверка наличия - Hash - true - %d\n" +
                        "проверка наличия - Hash - false - %d\n" +
                        "проверка наличия - Tree - true1 - %d\n" +
                                "проверка наличия - Tree - true2 - %d\n",
                diffInitHash,
                diffInitTree,
                diffPrintHash,
                diffPrintTree,
                diffFindHashT,
                diffFindHashF,
                diffFindTreeT1,
                diffFindTreeT2

        );
    }
}

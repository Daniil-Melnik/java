/*
2.1 проверить метод двоичного поиска на связном списке и списочном массиве
= УСЛОВИЕ список д.б. отсортированным по возрастанию. Гарантируется нахождение одного эл-та
= (фактически список является отсортированным множеством)
= проверяется скорость работы алгоритма на двух типах списка
- создать служебную сущность Entity - реализацию Comparable
- в главной программе создать два списка на 100 экземпляров Entity
- в главной программе создать метод, выполняющий двоичный поиск по коллекции через произвольный доступ
- замерить время работы (созданный и существующий) на связном списке и списочном массиве, вывести в файл
*/

package testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import testing.ext.Entity;
import testing.utils.PrintUtils;

public class LevelThree {

    private final static int SIZE = 500_000;
    
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

    public static void main(String[] args) {
        LinkedList<Entity> linkedEntity = new LinkedList<>();
        ArrayList<Entity> arrayEntity = new ArrayList<>();

        timeStamp0 = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++){
            linkedEntity.add(new Entity(i));
        }

        timeStamp1 = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++){
            arrayEntity.add(new Entity(i));
        }

        timeStamp2 = System.currentTimeMillis();

        System.out.println(binaryHSearch(linkedEntity, new Entity(58)));

        timeStamp3 = System.currentTimeMillis();

        System.out.println(binaryHSearch(arrayEntity, new Entity(58)));

        timeStamp4 = System.currentTimeMillis();

        System.out.println(Collections.binarySearch(linkedEntity, new Entity(58)));

        timeStamp5 = System.currentTimeMillis();

        System.out.println(Collections.binarySearch(arrayEntity, new Entity(58)));

        timeStamp6 = System.currentTimeMillis();

        try {
            PrintUtils.printResultsToFile(createOutTable(), "\\out2.txt");
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл! " + e.getMessage() + " " + e.getClass());
        }

    }

    private static String createOutTable(){ // формирование текста для печати в файл

        long diffInitLinked = timeStamp1-timeStamp0;
        long diffInitArray = timeStamp2-timeStamp1;
        long diffHSearchLinked = timeStamp3 - timeStamp2;
        long diffHSearchArray = timeStamp4 - timeStamp3;
        long diffCSearchLinked = timeStamp5 - timeStamp4;
        long diffCSearchArray = timeStamp6 - timeStamp5;

        return String.format(
                        "заполнить - Linked - %d\n" +
                        "заполнить - Array - %d\n" +
                        "самописный поиск - Linked - %d\n" +
                        "самописный поиск - Array - %d\n" +
                        "поиск - встроенный - Linked - %d\n" +
                        "поиск - встроенный - Array - %d\n",
                diffInitLinked,
                diffInitArray,
                diffHSearchLinked,
                diffHSearchArray,
                diffCSearchLinked,
                diffCSearchArray
        );
    }

    private static int binaryHSearch(List<? extends Entity> sortedList, Entity el){
        int size = sortedList.size();
        int index = -1;

        int right = size - 1;
        int left = 0;
        int center;
        Entity fEl = null;

        int cond = -100;
        while ((cond !=0) && ((right-left) != 1)){
            center = (right - left) / 2 + left;
            fEl = sortedList.get(center);
            cond = fEl.compareTo(el);

            if (cond > 0) right = center;
            else if (cond < 0) left = center;
            else if (cond == 0) index = center;
        }
        return index;
    }
}

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
    
    private static long timeStamp0; // временные границы отсчета
    private static long timeStamp1;
    private static long timeStamp2;
    private static long timeStamp3;
    private static long timeStamp4;
    private static long timeStamp5;
    private static long timeStamp6;

    public static void main(String[] args) {
        LinkedList<Entity> linkedEntity = new LinkedList<>(); // создание связного списка
        ArrayList<Entity> arrayEntity = new ArrayList<>(); // создание списочного массива

        timeStamp0 = System.currentTimeMillis(); // отсечение старта отсчёта программы

        for (int i = 0; i < SIZE; i++){ // заполнение связного массива
            linkedEntity.add(new Entity(i));
        }

        timeStamp1 = System.currentTimeMillis(); // отсечение заполнения связного списка

        for (int i = 0; i < SIZE; i++){ // заполнение списочного массива
            arrayEntity.add(new Entity(i));
        }

        timeStamp2 = System.currentTimeMillis(); // отсечение заполнения списочного массива

        System.out.println(binaryHSearch(linkedEntity, new Entity(58))); // запуск самописного поиска в связном списке

        timeStamp3 = System.currentTimeMillis(); // отсечение самописного двоичного поиска в связном списке

        System.out.println(binaryHSearch(arrayEntity, new Entity(58))); // запуск самописного поиска в списочном массиве

        timeStamp4 = System.currentTimeMillis(); // отсечение самописного поиска в списочном массиве

        System.out.println(Collections.binarySearch(linkedEntity, new Entity(58))); // запуск втроенного поиска в связном списке

        timeStamp5 = System.currentTimeMillis(); // отсечение встроенного поиска в связном списке

        System.out.println(Collections.binarySearch(arrayEntity, new Entity(58))); // запуск втроенного поиска в списочном массиве

        timeStamp6 = System.currentTimeMillis(); // отсечение встроенного поиска в списочном массиве

        try {
            PrintUtils.printResultsToFile(createOutTable(), "\\out3.txt");
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл! " + e.getMessage() + " " + e.getClass());
        }

    }

    private static String createOutTable(){ // формирование текста для печати в файл

        long diffInitLinked = timeStamp1-timeStamp0; // длительность заполнения связного списка
        long diffInitArray = timeStamp2-timeStamp1; // длительность заполнения списочного массива
        long diffHSearchLinked = timeStamp3 - timeStamp2; // длительность самописного поиска в связном списке
        long diffHSearchArray = timeStamp4 - timeStamp3; // длительность самописного поиска в списочном массиве
        long diffCSearchLinked = timeStamp5 - timeStamp4; // длительность встроенного поиска в связном списке
        long diffCSearchArray = timeStamp6 - timeStamp5; // длительность встроенного поиска в списочном массиве

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
        int size = sortedList.size(); // получение размера поданной коллекции
        int index = -1; // задание значения по умолчанию на случай отсутствия искомого элемента в коллекции

        int right = size - 1; // правая граница зоны поиска
        int left = 0; // левая граница зоны поиска
        int center; // центр зоны поиска, кандидат на индекс искомого элемента
        Entity fEl = null; // кандидат на найденный элемент в коллекции

        int cond = -100; // условие сравнения
        while ((cond !=0) && ((right-left) != 1)){ // пока не найден или границы не смокнулись
            center = (right - left) / 2 + left; // определение центра зоны поиска
            fEl = sortedList.get(center); // определение кандидата на искомый элемент
            cond = fEl.compareTo(el); // сравнение кандидата и искомый элемент

            if (cond > 0) right = center; // если кандидат больше искомого, то смещение в левую подзону
            else if (cond < 0) left = center; // если кандидат меньше искомого, то смещение в правую сторону
            else if (cond == 0) index = center; // если кандидат и искомы совпадают то присваивается ответ
        }
        return index;
    }
}

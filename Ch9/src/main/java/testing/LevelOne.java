package testing;

/*1. Замерить и сравнить работу LinkedList и ArrayList
- создать объекты спиков, набить каждый по 100_000 элементов
- выполнить произвольный доступ в середине
- выполнить перебор с печатью через произвольный доступ
- выполнить перебор через итератор с печатью*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import testing.utils.PrintUtils;


public class LevelOne {

    private static  final int SIZE = 100_000; // количество элементов в списках

    private static long timeStamp0;
    private static long timeStamp1;
    private static long timeStamp2;
    private static long timeStamp3;
    private static long timeStamp4;
    private static long timeStamp5;
    private static long timeStamp6;

    public static void main(String [] args){
        List<String> linkedList = new LinkedList<>(); // объявление связного списка
        List<String> arrayList = new ArrayList<>(); // объявление списочного массива

        for (int i = 0; i < SIZE; i++){ // набивка списков
            linkedList.add(String.format("String %d", i));
            arrayList.add(String.format("String %d", i));
        }

        Iterator<String> linkIterator = linkedList.iterator(); // получение итератора для связного списка
        Iterator<String> arrayIterator = arrayList.iterator(); // получение итератора для списочного массива

        timeStamp0 = System.currentTimeMillis(); // начало отсчета времени
        linkedList.get(SIZE / 2); // произвольный доступ к срединному элементу связного списка

        timeStamp1 = System.currentTimeMillis(); // отсечение замера середины связного списка
        arrayList.get(SIZE / 2); // произвольный доступ к срединному элементу списочного массива

        timeStamp2 = System.currentTimeMillis(); // отсечение замера середины списочного массива
        for (int i = 0; i < SIZE; i++){ // произвольная поэлементная печать связного списка
            System.out.println(linkedList.get(i));
        }

        timeStamp3 = System.currentTimeMillis(); // отсечение произвольной печати связного списка
        for (int i = 0; i < SIZE; i++){ // произвольная поэлементная печать списочного массива
            System.out.println(arrayList.get(i));
        }

        timeStamp4 = System.currentTimeMillis(); // отсечение произвольной печати связного списка
        while(linkIterator.hasNext()){ // поэлементная печать связного списка через итератор
            System.out.println(linkIterator.next());
        }

        timeStamp5 = System.currentTimeMillis(); // отсечение печати связного списка через итератор
        while(arrayIterator.hasNext()){ // поэлементная печать списочного массива через итератор
            System.out.println(arrayIterator.next());
        }

        timeStamp6 = System.currentTimeMillis(); // отсечение печати списочного массива через итератор

        try {
            PrintUtils.printResultsToFile(createOutTable(), "\\out1.txt"); // печать временнЫх результатов работы участков программы
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл!\n"  + e.getMessage());
        }
    }

    

    private static String createOutTable(){ // формирование текста для печати в файл

        long diffCentLinked = timeStamp1-timeStamp0;
        long diffCentArray = timeStamp2-timeStamp1;
        long diffRandLinked = timeStamp3 - timeStamp2;
        long diffRandArray = timeStamp4 - timeStamp3;
        long diffIterLinked = timeStamp5 - timeStamp4;
        long diffIterArray = timeStamp6 - timeStamp5;

        return String.format(
            "взять середину - Linked - %d\n" + 
            "взять середину - Array - %d\n" +
            "перебор - произвольный - Array - %d\n" + 
            "перебор - произвольный - Linked - %d\n" +
            "перебор - итератор - Array - %d\n" + 
            "перебор - итератор - Linked - %d\n", 
                diffCentLinked,
                diffCentArray,
                diffRandArray,
                diffRandLinked,
                diffIterArray,
                diffIterLinked
        );
    }
}
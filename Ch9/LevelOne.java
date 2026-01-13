/*1. Замерить и сравнить работу LinkedList и ArrayList
- создать объекты спиков, набить каждый по 100_000 элементов
- выполнить произвольный доступ в середине
- выполнить перебор с печатью через произвольный доступ
- выполнить перебор через итератор с печатью*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


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
            printResultsToFile(); // печать временнЫх результатов работы участков программы
        } catch (IOException e) {
            System.out.println("Ошибка печати в файл!\n"  + e.getMessage());
        }
    }

    private static  String getFileOutPath(){ // получение пути к файлу для печати выходных данных
        String result = "";

        Properties props = new Properties(); // объект свойств программы
        try {
            props.load(new FileInputStream("program.properties")); // загрузка свойств из файла
            result = props.getProperty("out.filePath"); // извлечение свойства с строкой пути из объекта свойств
        } catch (IOException e) {
            System.out.println("Ошибка получения пути к файлу вывода \n" + e.getMessage());
        }
        
        return result;
    }

    private static File getOutFile(){ // получение объекта файла по имени
        File outFile = new File(getFileOutPath() + "\\out1.txt");
        return outFile;
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

    private static void printResultsToFile() throws IOException{
        try (FileWriter writer = new FileWriter(getOutFile())) { // try с ресурсами для печати в файл
            writer.write(createOutTable());
        }
    }
}
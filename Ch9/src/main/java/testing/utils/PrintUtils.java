package testing.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PrintUtils {
    
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

    public static void printResultsToFile(String str) throws IOException{
        try (FileWriter writer = new FileWriter(getOutFile())) { // try с ресурсами для печати в файл
            writer.write(str);
        }
    }
}

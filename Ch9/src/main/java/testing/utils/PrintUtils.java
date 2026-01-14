package testing.utils;

import java.io.*;
import java.util.Properties;

public class PrintUtils {
    
    private static  String getFileOutPath(){ // получение пути к файлу для печати выходных данных
        String result = "";

        Properties props = new Properties(); // объект свойств программы
        try {
            InputStream input = PrintUtils.class.getClassLoader().getResourceAsStream("program.properties");
            // замена способа получения ресурса в реалиях maven (вместо создания FileInputStream на строке ниже)
            // props.load(new FileInputStream("C:\\Users\\user\\Desktop\\Java\\java\\Ch9\\src\\main\\resources\\program.properties")); // загрузка свойств из файла
            props.load(input); // загрузка свойств из файла
            result = props.getProperty("out.filePath"); // извлечение свойства с строкой пути из объекта свойств
        } catch (IOException e) {
            System.out.println("Ошибка получения пути к файлу вывода \n" + e.getMessage() + " " + e.getClass());
        }
        
        return result;
    }

    private static File getOutFile(String fileName){ // получение объекта файла по имени
        File outFile = new File(getFileOutPath() + fileName);
        return outFile;
    }

    public static void printResultsToFile(String str, String fileName) throws IOException{
        try (FileWriter writer = new FileWriter(getOutFile(fileName))) { // try с ресурсами для печати в файл
            writer.write(str);
        }
    }
}

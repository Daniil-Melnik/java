package ch_7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) {


        /*try { // обёртка кода кидающего ошибку
            TestClass.printMessage("");
        } catch (TestExeption e){ // приём кидаемой ошибки
            System.out.println(e);
        }*/
        /*TestChildClass tCh = new TestChildClass(); // если родитель не кидает, то и наследник не может
        tCh.printMessage1("qq");*/

        // TestClass.TestChainOfExeptions("55555"); // Проверки цепи
        // TestClass.TestChainOfExeptions("4444");
        // TestClass.TestChainOfExeptions("333");
        // TestClass.TestChainOfExeptions("1");
        // TestClass.TestChainOfExeptions("");

        try (var in = new Scanner(new FileInputStream("C:\\text\\1.txt"), StandardCharsets.UTF_8)){
            while (in.hasNext()) System.out.println(in.next());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
